package ru.sorokin.course.library.author;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import ru.sorokin.course.library.books.BookDto;

import java.util.Set;

public record AuthorDto(

        @Null
        Long id,

        @NotBlank
        String name,

        @Min(0)
        Integer birthYear,

        @Size(max = 0)
        Set<BookDto> books
) {
}
