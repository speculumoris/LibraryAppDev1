package com.lib.domain;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

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

<<<<<<<<< Temporary merge branch 1
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime loanDate=LocalDateTime.now();
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    @NotNull(message="Please provide expire date of the book")
=========
    private LocalDateTime loanDate = LocalDateTime.now();

>>>>>>>>> Temporary merge branch 2
    private LocalDateTime expireDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    @NotNull(message="Please provide return date of the book")
    @Column(nullable = false)
    private LocalDateTime returnDate;

    @Column(nullable = false)
    @Size(max = 300)
    private String notes;

<<<<<<<<< Temporary merge branch 1
    @ManyToOne
    @JoinColumn(name = "book_id",referencedColumnName = "id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
=========
    @OneToMany
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private List<Book> book;

    @OneToMany
    @JoinColumn(name = "ueser_id", referencedColumnName = "id")
    private List<User> user;
>>>>>>>>> Temporary merge branch 2



}
