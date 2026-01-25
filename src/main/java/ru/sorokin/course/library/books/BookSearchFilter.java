package ru.sorokin.course.library.books;

import jakarta.validation.constraints.Min;

public record BookSearchFilter(

        Long authorId,

        Integer cost,

        @Min(0)
        Integer pageNumber,

        @Min(3)
        Integer pageSize
) {
}
