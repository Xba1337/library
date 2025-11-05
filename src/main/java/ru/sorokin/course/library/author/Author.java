package ru.sorokin.course.library.author;

import ru.sorokin.course.library.books.Book;

import java.util.Set;

public record Author(
        Long id,
        String name,
        Integer birthYear,
        Set<Book> books
) {
}
