package com.lib.mapper;

import com.lib.domain.Loan;
import com.lib.domain.Publisher;
import com.lib.dto.LoanDTO;
import com.lib.dto.PublisherDTO;

public interface PublisherMapper {

    PublisherDTO publisherToPublisherDTO(Publisher publisher);

    Publisher publisherDTOToPublisher(PublisherDTO publisherDTO);

}
