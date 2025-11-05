package ru.sorokin.course.library.books;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sorokin.course.library.author.AuthorService;

import java.util.*;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookEntityConverter entityConverter;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, BookEntityConverter entityConverter, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.entityConverter = entityConverter;
        this.authorService = authorService;
    }

    public List<Book> getBooks(BookSearchFilter bookSearchFilter) {
        Integer pageSize = bookSearchFilter.pageSize() != null ? bookSearchFilter.pageSize() : 3;
        Integer pageNumber = bookSearchFilter.pageNumber() != null ? bookSearchFilter.pageNumber() : 0;

        Pageable pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);

        List<Book> books = bookRepository.searchBooks(bookSearchFilter.authorId(), bookSearchFilter.cost(), pageable)
                .stream()
                .map(entityConverter::convertToModel)
                .toList();
        return books;
    }

    public Book createBook(Book book) {
        checkAuthorExistence(book.authorId());

        BookEntity bookToSave = entityConverter.convertToEntity(book);
        BookEntity savedEntity = bookRepository.save(bookToSave);

        return entityConverter.convertToModel(savedEntity);
    }

    public Book findById(long id) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No found book by id=%s".formatted(id)));

        return entityConverter.convertToModel(bookEntity);
    }

    public void deleteBook(long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("No found book by id=%s".formatted(id));
        }
        bookRepository.deleteById(id);
    }

    public Book updateBook(long id, Book book) {
        checkAuthorExistence(book.authorId());

        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("No found book by id=%s".formatted(id));
        }
        bookRepository.updateBook(
                id,
                book.name(),
                book.authorId(),
                book.publicationYear(),
                book.pageNumber(),
                book.cost()
        );

        return entityConverter.convertToModel(bookRepository.findById(id).orElseThrow());
    }

    private void checkAuthorExistence(Long authorId) {
        if (!authorService.isAuthorExists(authorId)){
            throw new IllegalArgumentException("Author with id=%s does not exist".formatted(authorId));
        }
    }
}
