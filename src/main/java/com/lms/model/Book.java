package com.lms.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Unique code must not be empty. ")
    @Size(min = 5,max = 70)
    private String UniqueCode;

    @NotNull(message = "Title can not be empty. ")
    @Size(min = 5,max = 70)
    private String title;

    @NotNull(message = "Subject can not be empty. ")
    @Size(min = 3,max = 50)
    private String subject;

    @NotNull(message = "Publication date can not be empty")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate publicationDate;

    @NotNull(message = "Language can not be empty. ")
    @Size(min = 3,max = 50)
    private String language;

    @NotNull(message = "Author name can not be empty. ")
    @Size(min = 3,max = 50)
    private String author;

   @OneToMany(mappedBy="book")
    private Set<BookItem> bookItems;


}
