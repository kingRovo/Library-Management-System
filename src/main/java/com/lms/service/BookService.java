package com.lms.service;

import com.lms.exception.BookItemException;
import com.lms.model.Book;
import com.lms.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class BookService {

    private BookRepository bookRepository;

    /**
     * search books by keyword
     * @param keyword
     * @return ResponseEntity<List<Book>>
     */
    @Transactional
    public ResponseEntity<List<Book>> searchBooks(String keyword){
        try{

           List<Book> books =  bookRepository.searchBooks(keyword);
           log.info("return all available books");
            return new ResponseEntity<>(books,HttpStatus.OK);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            throw new BookItemException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * this method filter and sort books data.
     * @param start
     * @param pageSize
     * @param field
     * @return ResponseEntity<Page<Book>>
     */
    public ResponseEntity<Page<Book>> filterAndSort(Integer start, Integer pageSize, String field){


        Page<Book> books = bookRepository.findAll(PageRequest.of(start,pageSize).withSort(Sort.by(field)));
        log.info("filtering books data ");
        return new ResponseEntity<>(books,HttpStatus.OK);

    }

    /**
     * add books to database
     * @param book
     * @return ResponseEntity<Book>
     */
    @Transactional
    public ResponseEntity<Book> addBook(Book book){

        if (!ObjectUtils.isEmpty(book)) {

            return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);
        } else {
            log.error("bad request unable to add book to system");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
