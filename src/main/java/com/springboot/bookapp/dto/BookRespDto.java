package com.springboot.bookapp.dto;
import java.time.Instant;

public record BookRespDto(
        long id,
        String title,
        String author,
        String isbn,
        int publicationYear,
        Instant createdAt,
        Instant updatedAt
) {
}
