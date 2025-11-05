package ru.sorokin.course.library.books;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final BookDtoConverter dtoConverter;

    public BookController(BookService bookService, BookDtoConverter dtoConverter) {
        this.bookService = bookService;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping("/books")
    public List<BookDto> getAllBooks(
            @Valid BookSearchFilter bookSearchFilter
    ) {
        log.info("Get request for get all books");
        return bookService.getBooks(bookSearchFilter)
                .stream()
                .map(dtoConverter::convertToDto)
                .toList();
    }

    @PostMapping("/books")
    public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDto) {
        log.info("Get request for create book: book = {}", bookDto);
        Book createdBook = bookService.createBook(dtoConverter.convertToModel(bookDto));

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("my-header", "123")
                .body(dtoConverter.convertToDto(createdBook));
    }

    @GetMapping("/books/{id}")
    public BookDto getBook(@PathVariable long id) {
        log.info("Get request for get book: id = {}", id);
        Book findedBook = bookService.findById(id);

        return dtoConverter.convertToDto(findedBook);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) {
        log.info("Get request for delete book: id = {}", id);
        bookService.deleteBook(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("books/{id}")
    public BookDto updateBook(@PathVariable long id, @RequestBody @Valid BookDto bookDto) {
        log.info("Get request for update book: id = {}, book to update = {}", id, bookDto);

        Book updatedBook = bookService.updateBook(id,
                dtoConverter.convertToModel(bookDto));

        return dtoConverter.convertToDto(updatedBook);
    }
}
