package com.lib.mapper;

import com.lib.domain.Category;
import com.lib.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {


    List<CategoryDTO> map(List<Category> categoryList);


    Category categoryDTOToCategory(CategoryDTO categoryDTO);

    CategoryDTO categoryToCategoryDTO(Category category);





}
