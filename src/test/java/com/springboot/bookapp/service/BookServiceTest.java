package com.springboot.bookapp.service;

import com.springboot.bookapp.dto.BookReqDto;
import com.springboot.bookapp.dto.BookRespDto;
import com.springboot.bookapp.exceptions.DuplicateIsbnException;
import com.springboot.bookapp.exceptions.ResourceNotFoundException;
import com.springboot.bookapp.model.Book;
import com.springboot.bookapp.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void getByIsbnTestWhenExists() {
        Assertions.assertNotNull(bookService);

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setIsbn("123");
        book.setPublicationYear(2008);

        Mockito.when(bookRepository.findByIsbn("123"))
                .thenReturn(Optional.of(book));

        BookRespDto dto = new BookRespDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublicationYear(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );

        BookRespDto dto1 = new BookRespDto(
                book.getId(),
                book.getTitle(),
                "Wrong Author",
                book.getIsbn(),
                book.getPublicationYear(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );

        Assertions.assertEquals(dto, bookService.getByIsbn("123"));
        Assertions.assertNotEquals(dto1, bookService.getByIsbn("123"));

        Mockito.verify(bookRepository, times(2)).findByIsbn("123");
    }

    @Test
    public void getByIsbnTestWhenNotFound() {
        when(bookRepository.findByIsbn("0000000000"))
                .thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getByIsbn("0000000000");
        });

        Assertions.assertEquals("No book found with ISBN: 0000000000", e.getMessage());
    }

    @Test
    public void getAllBooksTest() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Harry Potter");
        book1.setAuthor("JK Rowling");
        book1.setIsbn("234");
        book1.setPublicationYear(2008);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Abc");
        book2.setAuthor("Xyz");
        book2.setIsbn("134");
        book2.setPublicationYear(1999);

        List<Book> list = List.of(book1, book2);

        Page<Book> pageBook = new PageImpl<>(list);
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);
        // when(bookRepository.findAll(pageable)).thenReturn(pageBook);

        Page<Book> pageBook1 = new PageImpl<>(list.subList(0, 1));
        page = 0;
        size = 1;
        Pageable pageable1 = PageRequest.of(page, size);

        when(bookRepository.findAll(pageable1)).thenReturn(pageBook1);

        // Assertions.assertEquals(2, bookService.getAllBooks(0, 2).data().size());
        Assertions.assertEquals(1, bookService.getAllBooks(0, 1).data().size());
        // Assertions.assertNotEquals(3, bookService.getAllBooks(0, 1).data().size());
    }

    @Test
    public void addBookTestWhenIsbnNotDuplicate() {
        BookReqDto dto = new BookReqDto(
                "Harry Potter",
                "JK Rowling",
                "123",
                2008
        );

        when(bookRepository.existsByIsbn("123")).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        Assertions.assertDoesNotThrow(() -> bookService.addBook(dto));

        Mockito.verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void addBookTestWhenIsbnIsDuplicate() {
        BookReqDto dto = new BookReqDto(
                "Harry Potter",
                "JK Rowling",
                "123",
                2008
        );

        when(bookRepository.existsByIsbn("123")).thenReturn(true);

        Exception e = Assertions.assertThrows(DuplicateIsbnException.class, () -> {
            bookService.addBook(dto);
        });

        Assertions.assertEquals("A book with ISBN already exists.", e.getMessage());
    }

    @Test
    public void deleteByIsbnWhenExists() {
        Book book = new Book();
        book.setId(1L);
        book.setIsbn("123");

        when(bookRepository.findByIsbn("123"))
                .thenReturn(Optional.of(book));

        Assertions.assertDoesNotThrow(() -> bookService.deleteByIsbn("123"));

        Mockito.verify(bookRepository, times(1)).deleteById(book.getId());
    }

    @Test
    public void deleteByIsbnWhenNotFound() {
        when(bookRepository.findByIsbn("000"))
                .thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bookService.deleteByIsbn("000");
        });

        Assertions.assertEquals("No book found with ISBN: 000", e.getMessage());
    }
}