package com.lib.service;


import com.lib.domain.ImageFile;
import com.lib.domain.Publisher;
import com.lib.dto.PublisherDTO;
import com.lib.exception.BadRequestException;
import com.lib.exception.ConflictException;
import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.mapper.PublisherMapper;
import com.lib.repository.PublisherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final ImageFileService imageFileService;
    private final PublisherMapper publisherMapper;

    public PublisherService(PublisherRepository publisherRepository, ImageFileService imageFileService, PublisherMapper publisherMapper) {
        this.publisherRepository = publisherRepository;
        this.imageFileService = imageFileService;
        this.publisherMapper = publisherMapper;
    }

    public Page<PublisherDTO> getPublisherPage(Pageable pageable) {


        Page<Publisher> publisherPage=publisherRepository.findAll(pageable);

        return getPublisherDtoPage(publisherPage);
    }

    private Page<PublisherDTO >getPublisherDtoPage (Page<Publisher>publisherPage){
        return publisherPage.map(publisher->
                publisherMapper.publisherToPublisherDTO(publisher));
    }


    public PublisherDTO getPublisherById(Long id) {

        Publisher publisher=publisherRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE,id)));

        return publisherMapper.publisherToPublisherDTO(publisher);

    }


    public void savePublisher(String imageId, PublisherDTO publisherDTO) {
        //!!! image Id , Repo da var mi ??
        ImageFile imageFile = imageFileService.findImageById(imageId);
        //!!! imadeId daha once baska bir yayin icin kullanildi mi ???
        Integer usedPublisherCount = publisherRepository.findPublisherCountByImageId(imageFile.getId());
        if(usedPublisherCount>0) {
            throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
        }
        //!!! mapperislemi
        Publisher publisher = publisherMapper.publisherDTOToPublisher(publisherDTO);
        //!!! image bilgisini Publisher a ekliyoruz
        Set<ImageFile> imFiles = new HashSet<>();
        imFiles.add(imageFile);
        //publisher.setImageFile(imFiles);
        publisherRepository.save(publisher);


    }

    public void removeById(Long id) {
        Publisher publisher = getPublisher(id);

        // !!! builtIn ???
        if(publisher.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }


       publisherRepository.delete(publisher);
    }
    private Publisher getPublisher(Long id){
        Publisher publisher = (Publisher) publisherRepository.findPublisherById(id).orElseThrow(()->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE, id)));
        return publisher;
    }

    public void updatePublisher(Long id, String imageId, PublisherDTO publisherDTO) {

            Publisher publisher = getPublisher(id);

            // !!! builtIn ???
            if(publisher.getBuiltIn()){
                throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
            }

            // !!! verilen image daha önce başka araç içni kullanılmış mı ???
            ImageFile imageFile =  imageFileService.findImageById(imageId);

            List<Publisher> publisherList = publisherRepository.findPublisherByImageId(imageFile.getId());
            for (Publisher p : publisherList) {
                // Long --> long
                if(publisher.getId().longValue()!= p.getId().longValue()){
                    throw  new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
                }
            }
           publisher.setName(publisherDTO.getName());


            publisherRepository.save(publisher);

    }
}
