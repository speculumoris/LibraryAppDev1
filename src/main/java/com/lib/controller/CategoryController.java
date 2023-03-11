package com.lib.controller;

import com.lib.dto.CategoryDTO;
import com.lib.dto.response.LibResponse;
import com.lib.dto.response.ResponseMessage;
import com.lib.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/admin/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> saveCategory(@PathVariable Long bookId, @Valid @RequestBody CategoryDTO categoryDTO){

        categoryService.saveCategory(bookId, categoryDTO);

        LibResponse response = new LibResponse(ResponseMessage.CATEGORY_SAVED_RESPONSE_MESSAGE,true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/visitors/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {

        List<CategoryDTO> allCategories = categoryService.getAllCategories();

        return ResponseEntity.ok(allCategories);

    }

    @GetMapping("/visitors/pages")
    public ResponseEntity<Page<CategoryDTO>> getAllCategoriesWithPage(@RequestParam("page") int page,
                                                                      @RequestParam("size") int size,
                                                                      @RequestParam("sort") String prop,
                                                                      @RequestParam(value = "direction", required = false, defaultValue = "DESC")Sort.Direction direction){

        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));

        Page<CategoryDTO> categoryDTO = categoryService.findAllWithPage(pageable);

        return ResponseEntity.ok(categoryDTO);

    }


    @GetMapping("/visitors/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {

        CategoryDTO categoryDTO = categoryService.findById(id);

        return ResponseEntity.ok(categoryDTO);

    }

    @PutMapping("/admin/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> updateCategory( @RequestParam("id") Long id,
                                                       @RequestParam("bookId") Long bookId,
                                                       @Valid @RequestBody CategoryDTO categoryDTO){

        categoryService.updateCategory(id, bookId, categoryDTO);

        LibResponse response = new LibResponse(ResponseMessage.CATEGORY_UPDATED_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/admin/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> deleteCategory(@PathVariable Long id){

        categoryService.removeById(id);

        LibResponse response = new LibResponse(ResponseMessage.CATEGORY_DELETED_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(response);

    }


}
