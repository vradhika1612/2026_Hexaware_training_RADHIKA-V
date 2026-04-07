package com.springboot.bookapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookReqDto(
        @NotBlank(message = "Title cannot be blank")
        @NotNull
        @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
        String title,

        @NotBlank(message = "Author cannot be blank")
        @NotNull
        @Size(min = 1, max = 100, message = "Author name must be between 1 and 100 characters")
        String author,

        @NotBlank(message = "ISBN cannot be blank")
        @NotNull
        @Size(min = 2, max = 100, message = "ISBN must be between 10 and 100 characters")
        String isbn,


        @NotNull(message = "Publication year cannot be blank")
        Integer publicationYear
) {
}
