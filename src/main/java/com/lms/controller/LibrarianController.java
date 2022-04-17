package com.lms.controller;

import com.lms.model.Book;
import com.lms.model.BookItem;
import com.lms.model.Librarian;
import com.lms.model.Member;
import com.lms.model.dto.BookStatusResponse;
import com.lms.service.BookService;
import com.lms.service.LibrarianService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/v1/librarian")
public class LibrarianController {

    private final LibrarianService librarianService;
    private final BookService bookService;


    /**
     * add new member
     * @param member
     * @return ResponseEntity
     */

    @PostMapping("{id}/member")
    ResponseEntity<Member> addMember(@PathVariable("id") Long id, @RequestBody Member member){

        log.info("adding new member");
      return librarianService.addNewMember(id,member);
    }

    /**
     * add new librarian
     * @param librarian
     * @return ResponseEntity
     */

    @PostMapping("/")
    ResponseEntity<Librarian> registerNewLibrarian(@RequestBody @Valid Librarian librarian){

        log.info("registering new librarian");
        return librarianService.registerLibrarian(librarian);
    }

    /**
     * adding book to library
     * @param book
     * @return ResponseEntity
     */
    @PostMapping("{id}/book")
    ResponseEntity<Book> addNewBook(@PathVariable("id") Long id, @RequestBody Book book){

        log.info("adding new book to library");
        return librarianService.addBook(id,book);
    }

    /**
     * adding new item to book.
     *
     * @param book_id
     * @param bookItem
     * @return ResponseEntity<BookItem>
     */

    @PostMapping("{id}/book/{book_id}/bookitem")
     ResponseEntity<BookItem> addBookItem(@PathVariable("id") Long id,@PathVariable("book_id") Long book_id, @RequestBody BookItem bookItem) {

        return librarianService.addBookItem(id,book_id,bookItem);
     }


    /**
     * searching books
     * @param keyword
     * @return
     */
     @GetMapping("/book")
     ResponseEntity<List<Book>> searchBooks(@RequestParam("keyword") String keyword){
        log.info("search book by keyword ");
        return bookService.searchBooks(keyword);
     }


    /**
     * issue book to a particular user
     *
     * @param id
     * @param userId
     * @param bookItemId
     * @return ResponseEntity<BookStatusResponse>
     */

    @PostMapping("{id}/book/{bookItemId}/issue/member/{userID}")
    public ResponseEntity<BookStatusResponse> issueBookItem(@PathVariable("id") Long id, @PathVariable("userID") Long userId, @PathVariable("bookItemId") Long bookItemId) {

        log.info("issue book to user ");
    return librarianService.issueBookItem(id,userId,bookItemId);
    }

}
