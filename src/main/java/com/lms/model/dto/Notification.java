package com.lms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notification {

    private String sandTo;

    private LocalDate dueDate;

    private String bookName;

    private double totalFine;

}
