package com.lms.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3,max = 50)
    @NotNull(message = "Name can not be empty.")
    private String name;

    @NotNull(message = "Phone num must be of 10 digits")
    @Size(min = 10,max = 10)
    private String phone;

    @NotNull(message = "Email can not be empty.")
    @Email
    @Size(min = 5,max = 30)
    private String email;

    @NotNull(message = "Address can not be empty. ")
    @Size(min = 5,max = 100)
    private String address;

    @NotNull(message = "Date needs to be enter. ")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate memberTill;

    @NotNull
    private String accountStatus;

    @OneToMany(mappedBy="member")
    private Set<BookItem> bookItems;



}
