package ru.sorokin.course.library.books;

import org.springframework.stereotype.Component;

@Component
public class BookEntityConverter {

    public BookEntity convertToEntity(Book book) {
        return new BookEntity(
                book.id(),
                book.name(),
                book.authorId(),
                book.publicationYear(),
                book.pageNumber(),
                book.cost()
        );
    }

    public Book convertToModel(BookEntity bookEntity) {
        return new Book(
                bookEntity.getId(),
                bookEntity.getName(),
                bookEntity.getAuthorId(),
                bookEntity.getPublicationYear(),
                bookEntity.getPageNumber(),
                bookEntity.getCost()
        );
    }
}
