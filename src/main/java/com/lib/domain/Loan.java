package com.lib.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_loan")
public class Loan {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    @NotNull(message="Please provide loan date of the book")
    private LocalDateTime loanDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    @NotNull(message="Please provide expire date of the book")
    private LocalDateTime expireDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    @NotNull(message="Please provide return date of the book")
    @Column(nullable = false)
    private LocalDateTime returnDate;

    @Column(nullable = false)
    @Size(max = 300)
    private String notes;

    @OneToOne
    @JoinColumn(name = "book_id",referencedColumnName = "id")
    private Book book;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;



}
