package ru.sorokin.course.library;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    private long idCounter;

    private final Map<Long, Book> books;

    public BookService() {
        this.books = new HashMap<>();
        this.idCounter = 0L;
    }

    public List<Book> getBooks(String authorName, Integer maxCost) {
        return books.values()
                .stream()
                .filter(book -> authorName == null || book.author().equals(authorName))
                .filter(book -> maxCost == null || book.cost() < maxCost)
                .toList();
    }

    public Book createBook(Book book) {
        idCounter++;
        Book newBook = new Book(idCounter,
                book.name(),
                book.author(),
                book.publicationYear(),
                book.pageNumber(),
                book.cost());
        books.put(newBook.id(), newBook);

        return newBook;
    }

    public Book findById(long id) {
        return Optional.ofNullable(books.get(id))
                .orElseThrow(() -> new NoSuchElementException("No found book by id= %s".formatted(id)));
    }

    public void deleteBook(long id) {
        Book remove = books.remove(id);
        if (remove == null) {
            throw new NoSuchElementException("No found book by id= %s".formatted(id));
        }
    }

    public Book updateBook(long id, Book book) {
        if (books.get(id) == null) {
            throw new NoSuchElementException("No found book by id= %s".formatted(id));
        }
        Book updatedBook = new Book(id,
                book.name(),
                book.author(),
                book.publicationYear(),
                book.pageNumber(),
                book.cost());
        books.put(updatedBook.id(), updatedBook);
        return updatedBook;
    }
}
