package com.lms.repository;

import com.lms.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    @Query(value = "SELECT book FROM Book book WHERE book.title LIKE %?1%"
            + " OR book.author LIKE %?1%"
            + " OR concat(book.publicationDate,'') LIKE %?1%"
            + " OR book.subject LIKE %?1%")
    List<Book> searchBooks(String keyword);

}
