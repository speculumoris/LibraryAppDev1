package com.lib.service;

import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.repository.ImageFileRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageFileService {

    private final ImageFileRepository imageFileRepository;


    public ImageFileService(ImageFileRepository imageFileRepository) {
        this.imageFileRepository = imageFileRepository;
    }

    public ImageFileService findImageById(String imageId) {

        return imageFileRepository.findImageById(imageId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE,imageId)));


    }
}
