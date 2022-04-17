package com.lms.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class BookItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Bar code can not be empty")
    private String barCode;


    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate issueDate;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate dueDate;

    @NotEmpty(message = "Price can not be empty")
    private double price;

    @NotEmpty(message = "Status can not be empty")
    private String status;

    private Integer rackNumber;

    @ManyToOne
    @JoinColumn(name="member_id", nullable=false)
    private Member member;

    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    @Transient
    private Long reservedBy;

}
