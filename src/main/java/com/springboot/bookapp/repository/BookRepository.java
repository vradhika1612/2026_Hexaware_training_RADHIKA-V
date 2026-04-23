package com.springboot.bookapp.repository;

import com.springboot.bookapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
            select b from Book b
            where b.isbn = ?1
            """)
    Optional<Book> findByIsbn(String isbn);

    @Query("""
            select count(b) > 0 from Book b
            where b.isbn = ?1
            """)
    boolean existsByIsbn(String isbn);
}