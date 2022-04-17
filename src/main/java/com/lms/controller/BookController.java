package com.lms.controller;

import com.lms.model.Book;
import com.lms.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@RequestMapping("api/v1/book")
@RestController
@Slf4j
public class BookController {

    private final BookService bookService;

    /**
     * search books by keyword
     *
     * @param keyword
     * @return ResponseEntity<List < Book>>
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam("keyword") String keyword) {
        log.info("search books by keyword {}", keyword);
        return bookService.searchBooks(keyword);
    }

    /**
     * this method filter and sort books data.
     *
     * @param start
     * @param pageSize
     * @param field
     * @return ResponseEntity<Page < Book>>
     */
    @GetMapping("/")
    public ResponseEntity<Page<Book>> filterAndSort(@RequestParam("start") Integer start, @RequestParam("pageSize") Integer pageSize, @RequestParam("field") String field) {

        log.info("filter and sort data ");
        return bookService.filterAndSort(start, pageSize, field);
    }

    @PostMapping("/")
    ResponseEntity<Book> addBook(@RequestBody Book book){
        log.info("adding book to library");
        return bookService.addBook(book);
    }

}