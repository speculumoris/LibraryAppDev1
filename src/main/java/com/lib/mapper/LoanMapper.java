package com.lib.mapper;

import com.lib.domain.Book;
import com.lib.domain.Loan;
import com.lib.domain.User;
import com.lib.dto.LoanDTO;
import com.lib.dto.request.LoanRequest;
import com.lib.dto.request.LoanUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(source="book", target="bookId", qualifiedByName = "getBookId")
    @Mapping(source="user", target="userId", qualifiedByName = "getUserId")
    LoanDTO loanToLoanDTO(Loan loan);


    @Mappings({
            @Mapping(target="id", ignore = true),
            @Mapping(target="user", ignore = true),
            @Mapping(target="book", ignore = true),
            @Mapping(source="notes", target="notes")
    })
    Loan loanRequestToLoan(LoanRequest loanRequest);


    @Mappings({
            @Mapping(target="id", ignore = true),
            @Mapping(source="book", target="book"),
            @Mapping(source="user", target="user"),
            @Mapping(source="notes", target="notes"),
    })
    Loan loanUpdateRequestToLoan(LoanUpdateRequest loanRequest);

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
