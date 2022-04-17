package com.lms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookAvailableNotification {

    private String sandTo;
    private String bookStatus;
    private String bookName;
}
