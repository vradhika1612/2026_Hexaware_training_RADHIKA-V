package com.springboot.bookapp.mapper;

import com.springboot.bookapp.dto.BookReqDto;
import com.springboot.bookapp.dto.BookRespDto;
import com.springboot.bookapp.model.Book;

public class BookMapper {

    public static Book mapToEntity(BookReqDto bookReqDto) {
        Book book = new Book();
        book.setTitle(bookReqDto.title());
        book.setAuthor(bookReqDto.author());
        book.setIsbn(bookReqDto.isbn());
        book.setPublicationYear(bookReqDto.publicationYear());
        return book;
    }

    public static BookRespDto mapToDto(Book book) {
        return new BookRespDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublicationYear(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }
}