package ru.sorokin.course.library.books;

import org.springframework.stereotype.Component;

@Component
public class BookDtoConverter {

    public BookDto convertToDto(Book book) {
        return new BookDto(
                book.id(),
                book.name(),
                book.authorId(),
                book.publicationYear(),
                book.pageNumber(),
                book.cost()
        );
    }

    public Book convertToModel(BookDto bookDto) {
        return new Book(
                bookDto.id(),
                bookDto.name(),
                bookDto.authorId(),
                bookDto.publicationYear(),
                bookDto.pageNumber(),
                bookDto.cost()
        );
    }
}
