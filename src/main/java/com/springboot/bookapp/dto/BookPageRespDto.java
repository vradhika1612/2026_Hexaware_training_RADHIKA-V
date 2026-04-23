package com.springboot.bookapp.dto;

import java.util.List;

public record BookPageRespDto(
        List<BookRespDto> data,
        long totalRecords,
        int totalPages
) {
}
