package ru.sorokin.course.library.books;

public record Book(

        Long id,

        String name,

        Long authorId,

        Integer publicationYear,

        Integer pageNumber,

        Integer cost
) {
}
