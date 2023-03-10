package com.lib.mapper;

import com.lib.domain.ImageFile;
import com.lib.dto.ImageFileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageFileMapper {

    @Mapping(target = "book",ignore = true)
    List<ImageFileDTO> map(List<ImageFile> imageFileList);

    ImageFile imageFileDTOToImageFile(ImageFileDTO imageFileDTO);

    @Mapping(source = "book",target = "book",qualifiedByName = "getBookAsString")
    ImageFileDTO imageFileToImageFileDTO(ImageFile imageFile);




}
