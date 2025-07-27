package ru.sorokin.course.library;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks(@RequestParam(required = false) String authorName,
                                  @RequestParam(required = false) Integer maxCost) {
        log.info("Get request for get all books");
        return bookService.getBooks(authorName, maxCost);
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody @Valid Book book) {
        log.info("Get request for create book: book = {}", book);
        Book createdBook = bookService.createBook(book);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("my-header", "123")
                .body(createdBook);
    }

    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable long id) {
        log.info("Get request for get book: id = {}", id);
        return bookService.findById(id);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) {
        log.info("Get request for delete book: id = {}", id);
        bookService.deleteBook(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("books/{id}")
    public Book updateBook(@PathVariable long id, @RequestBody @Valid Book book) {
        log.info("Get request for update book: id = {}, book to update = {}", id, book);
        return bookService.updateBook(id, book);
    }
}
