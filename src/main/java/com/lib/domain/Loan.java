package com.lib.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    private LocalDateTime loanDate = LocalDateTime.now();

    private LocalDateTime expireDate;
    private LocalDateTime returnDate;

    @Column(nullable = false)
    @Size(max = 300)
    private String notes;

    @OneToMany
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private List<Book> book;

    @OneToMany
    @JoinColumn(name = "ueser_id", referencedColumnName = "id")
    private List<User> user;



}
