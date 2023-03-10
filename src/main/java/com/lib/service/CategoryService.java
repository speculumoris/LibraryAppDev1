package com.lib.service;

import com.lib.domain.Book;
import com.lib.domain.Category;
import com.lib.dto.CategoryDTO;
import com.lib.exception.BadRequestException;
import com.lib.exception.ConflictException;
import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.mapper.CategoryMapper;
import com.lib.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final BookService bookService;

    private final CategoryMapper categoryMapper;

    private final LoanService loanService;


    public CategoryService(CategoryRepository categoryRepository, BookService bookService, CategoryMapper categoryMapper, LoanService loanService) {
        this.categoryRepository = categoryRepository;
        this.bookService = bookService;
        this.categoryMapper = categoryMapper;
        this.loanService = loanService;
    }


    public void saveCategory(Long bookId, CategoryDTO categoryDTO) {

        Book book = bookService.findBookById(bookId);

        Integer usedCategoryCount = categoryRepository.findCategoryCountByBookId(book.getId());
        if (usedCategoryCount > 0) {
            throw new ConflictException(ErrorMessage.BOOK_USED_MESSAGE);
        }

        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);

        Set<Book> bookSet = new HashSet<>();
        bookSet.add(book);
        category.setBook(bookSet);

        categoryRepository.save(category);


    }

    public List<CategoryDTO> getAllCategories() {

        List<Category> categoryList = categoryRepository.findAll();

        return categoryMapper.map(categoryList);

    }

    public Page<CategoryDTO> findAllWithPage(Pageable pageable) {

        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return categoryPage.map(category->categoryMapper.categoryToCategoryDTO(category));

    }

    public CategoryDTO findById(Long id) {

        Category category = getCategory(id);

        return categoryMapper.categoryToCategoryDTO(category);

    }

    private Category getCategory(Long id){

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION,id)));

        return category;

    }

    public void updateCategory(Long id, Long bookId, CategoryDTO categoryDTO) {

        Category category = getCategory(id);

        if (category.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        Book book = bookService.findBookById(bookId);

        List<Category> categoryList = categoryRepository.findCategoriesByBookId(bookId);
        for (Category c : categoryList) {
            if (category.getId().longValue() == c.getId().longValue()) {
                throw new ConflictException(ErrorMessage.BOOK_USED_MESSAGE);
            }
        }

        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setSequence(categoryDTO.getSequence());

        category.getBook().add(book);

        categoryRepository.save(category);

    }

    public void removeById(Long id) {

        Category category = getCategory(id);

        if (category.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        boolean exist = loanService.existByCategory(category); // ?
        if (exist){
            throw new BadRequestException(ErrorMessage.CATEGORY_USED_BY_LOAN_MESSAGE);
        }

        categoryRepository.delete(category);

    }
}
