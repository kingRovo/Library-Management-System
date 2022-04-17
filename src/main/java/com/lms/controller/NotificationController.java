package com.lms.controller;

import com.lms.model.dto.BookAvailableNotification;
import com.lms.model.dto.Notification;
import com.lms.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * check fine for a user and sand notification if user have any fine.
     *
     * @param userId
     * @return
     */
    @RequestMapping("/user/{userId}")
    public ResponseEntity<Notification> checkFineAndSandNotification(@PathVariable("userId") Long userId) {

        log.info("sand due notification to user {}", userId);
        return notificationService.checkFineAndSandNotification(userId);

    }

    /**
     * sanding book available notification to user
     *
     * @param userId
     * @param bookItemId
     * @return ResponseEntity
     */
    @RequestMapping("user/{userId}/book-item/{bookItemId}")
    public ResponseEntity<BookAvailableNotification> bookAvailableNotification(@PathVariable("userId") Long userId,@PathVariable("bookItemId") Long bookItemId) {

        log.info("sand notification to user if book is available ot not");
        return notificationService.bookAvailableNotification(userId, bookItemId);
    }


}

