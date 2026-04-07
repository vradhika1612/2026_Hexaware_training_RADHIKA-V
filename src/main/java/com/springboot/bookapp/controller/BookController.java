package com.springboot.bookapp.controller;


import com.springboot.bookapp.dto.BookPageRespDto;
import com.springboot.bookapp.dto.BookReqDto;
import com.springboot.bookapp.dto.BookRespDto;
import com.springboot.bookapp.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@Valid @RequestBody BookReqDto dto) {
        bookService.addBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/get-all")
    public BookPageRespDto getAllBooks(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        return bookService.getAllBooks(page, size);
    }

    /* GET /api/books/get/{isbn} — AUTHOR and STUDENT */
    @GetMapping("/get/{isbn}")
    public BookRespDto getByIsbn(@PathVariable String isbn) {
        return bookService.getByIsbn(isbn);
    }

    /* PUT /api/books/update/{isbn} — AUTHOR only */
    @PutMapping("/update/{isbn}")
    public ResponseEntity<?> updateBook(@PathVariable String isbn,
                                        @Valid @RequestBody BookReqDto bookReqDto) {
        bookService.updateBook(isbn, bookReqDto);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    /* DELETE /api/books/delete/{isbn} — AUTHOR only */
    @DeleteMapping("/delete/{isbn}")
    public ResponseEntity<?> deleteByIsbn(@PathVariable String isbn) {
        bookService.deleteByIsbn(isbn);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
