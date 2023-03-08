package com.lib.mapper;

import com.lib.domain.Loan;
import com.lib.dto.LoanDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {


    LoanDTO loanToLoanDTO(Loan loan);
}
