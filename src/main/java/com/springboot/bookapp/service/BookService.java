package com.springboot.bookapp.service;

import com.springboot.bookapp.dto.BookPageRespDto;
import com.springboot.bookapp.dto.BookReqDto;
import com.springboot.bookapp.dto.BookRespDto;
import com.springboot.bookapp.exceptions.DuplicateIsbnException;
import com.springboot.bookapp.exceptions.ResourceNotFoundException;
import com.springboot.bookapp.mapper.BookMapper;
import com.springboot.bookapp.model.Book;
import com.springboot.bookapp.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public void addBook(BookReqDto bookReqDto) {
        // Step 1: Check if ISBN already exists
        if (bookRepository.existsByIsbn(bookReqDto.isbn())) {
            throw new DuplicateIsbnException("A book with ISBN already exists.");
        }
        // Step 2: Map DTO to Entity
        Book book = BookMapper.mapToEntity(bookReqDto);
        // Step 3: Save in DB
        bookRepository.save(book);
    }

    public BookPageRespDto getAllBooks(int page, int size) {
        // Create Pageable object
        Pageable pageable = PageRequest.of(page, size);
        // Fetch paginated result from repository
        Page<Book> pageBook = bookRepository.findAll(pageable);
        long totalRecords = pageBook.getTotalElements();
        int totalPages = pageBook.getTotalPages();
        // Convert List<Book> to List<BookRespDto>
        List<BookRespDto> listDto = pageBook
                .toList()
                .stream()
                .map(BookMapper::mapToDto)
                .toList();
        return new BookPageRespDto(listDto, totalRecords, totalPages);
    }

    public BookRespDto getByIsbn(String isbn) {
        // Fetch book by ISBN — throw exception if not found
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("No book found with ISBN: " + isbn));
        return BookMapper.mapToDto(book);
    }

    public void updateBook(String isbn, BookReqDto bookReqDto) {
        // Step 1: Fetch existing book by ISBN
        Book existingBook = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("No book found with ISBN: " + isbn));
        // Step 2: If ISBN is changing, check new ISBN doesn't conflict
        if (!existingBook.getIsbn().equals(bookReqDto.isbn()) && bookRepository.existsByIsbn(bookReqDto.isbn())) {
            throw new DuplicateIsbnException("A book with ISBN " + bookReqDto.isbn() + " already exists.");
        }
        // Step 3: Update fields
        existingBook.setTitle(bookReqDto.title());
        existingBook.setAuthor(bookReqDto.author());
        existingBook.setIsbn(bookReqDto.isbn());
        existingBook.setPublicationYear(bookReqDto.publicationYear());
        // Step 4: Save updated book
        bookRepository.save(existingBook);
    }

    public void deleteByIsbn(String isbn) {
        // Step 1: Check if book exists — throw exception if not found
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("No book found with ISBN: " + isbn));
        // Step 2: Hard delete
        bookRepository.deleteById(book.getId());
    }
}