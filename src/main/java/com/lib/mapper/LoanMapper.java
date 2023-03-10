package com.lib.mapper;

import com.lib.domain.Book;
import com.lib.domain.Loan;
import com.lib.domain.User;
import com.lib.dto.LoanDTO;
import com.lib.dto.request.LoanRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(source="book", target="bookId", qualifiedByName = "getBookId")
    @Mapping(source="user", target="userId", qualifiedByName = "getUserId")
    LoanDTO loanToLoanDTO(Loan loan);


    Loan loanRequestToLoan(LoanRequest loanRequest);

    List<LoanDTO> loanListToLoanDTOList(List<Loan> loan);


    @Named("getBookId")
    public static Long getBookId(Book book){
        return book.getId();
    }

    @Named("getUserId")
    public static Long getUserId(User user) {
        return user.getId();
    }


}
