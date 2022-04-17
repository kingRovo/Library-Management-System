package com.lms.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class BookItemResponse {

    private String bookName;
    private LocalDate dueDate;
    private String Status;
}
