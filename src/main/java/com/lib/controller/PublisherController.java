package com.lib.controller;

import com.lib.dto.PublisherDTO;
import com.lib.dto.response.LibResponse;
import com.lib.dto.response.ResponseMessage;
import com.lib.service.PublisherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/publishers")
public class PublisherController {


    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }
    //get all publishers with Page

    @GetMapping("/pages")


    public ResponseEntity<Page<PublisherDTO>> gelAllUsersByPage (@RequestParam("page") int page,
                                                                 @RequestParam("size") int size,
                                                                 @RequestParam("sort") String prop,
                                                                 @RequestParam(value="direction",
                                                                    required = false,
                                                                    defaultValue = "ASC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<PublisherDTO> publisherDTOPage = publisherService.getPublisherPage(pageable);

        return ResponseEntity.ok(publisherDTOPage);

    }
    //!!GetPublisherById
    @GetMapping("/{id}")


    public ResponseEntity<PublisherDTO> getPublisherById(@PathVariable Long id){
        PublisherDTO publisherDTO=publisherService.getPublisherById(id);

        return ResponseEntity.ok(publisherDTO);
    }

    //Create Publishers
    @PostMapping("/admin/{publisherName}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<LibResponse> savePublisher(
            @PathVariable String imageId, @Valid @RequestBody PublisherDTO publisherDTO){
        publisherService.savePublisher(imageId,publisherDTO);

       LibResponse response = new LibResponse(
                ResponseMessage.PUBLISHER_CREATED_RESPONSE,true);
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }
    // !!! Delete Publisher

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> deletePublisher(@PathVariable Long id) {
        publisherService.removeById(id);

        LibResponse response =
                new LibResponse(ResponseMessage.PUBLISHER_DELETE_RESPONSE_MESSAGE,true);
        return  ResponseEntity.ok(response);
    }

    //!!! Update Publisher
    @PutMapping("/admin/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> updatePublisher(
            @RequestParam("id") Long id,
            @RequestParam("imageId") String imageId,
            @Valid @RequestBody PublisherDTO publisherDTO) {
        publisherService.updatePublisher(id,imageId,publisherDTO);
        LibResponse response = new LibResponse(
                ResponseMessage.PUBLISHER_UPDATE_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(response);
    }



}





