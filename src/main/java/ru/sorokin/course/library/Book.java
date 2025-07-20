package ru.sorokin.course.library;


public record Book(long id,
                   String name,
                   String author,
                   int publicationYear,
                   int pageNumber,
                   int cost) {
}
