package com.lms.repository;


import com.lms.model.BookItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookItemRepository extends JpaRepository<BookItem,Long> {
}
