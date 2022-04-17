package com.lms.controller;

import com.lms.model.dto.BookItemResponse;
import com.lms.model.dto.BookStatusResponse;
import com.lms.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("api/v1/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * this method reserve book if not available.
     *
     * @param userid
     * @param bookItemId
     * @return ResponseEntity<BookStatusResponse>
     */
    @RequestMapping("{userId}/book/{bookItemId}/reserve")
    ResponseEntity<BookStatusResponse> reserveBookItem(@PathVariable("userId") Long userid, @PathVariable("bookItemId") Long bookItemId) {

        log.info("reserve book to user {}", userid);
        return memberService.reserveBookItem(userid, bookItemId);
    }

    /**
     * this method renew book.
     *
     * @param userid
     * @param bookItemId
     * @return ResponseEntity<BookItemResponse>
     */
    @RequestMapping("{userId}/book/{bookItemId}/renew")
    ResponseEntity<BookItemResponse> renewBook(@PathVariable("userId") Long userid, @PathVariable("bookItemId") Long bookItemId) {

        log.info("renew book to user {}", userid);
        return memberService.renewBook(userid, bookItemId);
    }

    /**
     * this method  follow processing of return book
     *
     * @param userId
     * @param bookItemId
     * @return
     */

    @RequestMapping("{userId}/book/{bookItemId}/return")
    ResponseEntity<BookStatusResponse> returnBook(@PathVariable("userId") Long userId, @PathVariable("bookItemId") Long bookItemId) {

        log.info("return issue book {}", bookItemId);
        return memberService.returnBook(userId, bookItemId);

    }

    /**
     * issue book to a particular user
     *
     * @param userId
     * @param bookItemId
     * @return ResponseEntity<BookStatusResponse>
     */
    @RequestMapping("{userId}/book/{bookItemId}/issue")
    public ResponseEntity<BookStatusResponse> issueBookItem(@PathVariable("userId") Long userId, @PathVariable("bookItemId") Long bookItemId) {

        log.info("book issued to user {}",userId);
        return memberService.issueBookItem(userId, bookItemId);
    }

}