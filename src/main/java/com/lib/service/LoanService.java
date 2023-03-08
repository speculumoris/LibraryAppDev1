package com.lib.service;

import com.lib.domain.Book;
import com.lib.domain.Loan;
import com.lib.dto.LoanDTO;
import com.lib.dto.request.LoanRequest;
import com.lib.dto.response.LibResponse;
import com.lib.mapper.LoanMapper;
import com.lib.repository.LoanRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    private final LoanMapper loanMapper;

    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    public Page<LoanDTO> getAllLoanWithPage(Pageable pageable) {
        Page<Loan> getAllLoan=loanRepository.findAll(pageable);

        return getAllLoan.map(map->loanMapper.loanToLoanDTO(map));
    }


}
