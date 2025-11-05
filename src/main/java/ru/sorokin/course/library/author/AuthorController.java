package ru.sorokin.course.library.author;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService authorService;
    private final AuthorDtoConverter authorDtoConverter;

    public AuthorController(AuthorService authorService, AuthorDtoConverter authorDtoConverter) {
        this.authorService = authorService;
        this.authorDtoConverter = authorDtoConverter;
    }

    @PostMapping
    public ResponseEntity<AuthorDto> addAuthor(@RequestBody AuthorDto authorToCreate) {
        log.info("Get request for create author: {}", authorToCreate);
        Author createdAuthor = authorService.createAuthor(authorDtoConverter.convertToModel(authorToCreate));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authorDtoConverter.convertToDto(createdAuthor));
    }

    @GetMapping
    public List<AuthorDto> getAuthors() {
        log.info("Get request for all authors");

        return authorService.getAllAuthors()
                .stream()
                .map(authorDtoConverter::convertToDto)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable long id) {
        log.info("Get request for delete author: {}", id);
        authorService.deleteAuthor(id);

        return ResponseEntity.noContent().build();
    }
}
