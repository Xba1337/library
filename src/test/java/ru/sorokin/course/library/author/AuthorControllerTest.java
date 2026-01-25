package ru.sorokin.course.library.author;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.sorokin.course.library.AbstractTest;
import ru.sorokin.course.library.books.Book;
import ru.sorokin.course.library.books.BookService;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthorControllerTest extends AbstractTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Test
    void successAuthorCreation() throws Exception {
        AuthorDto author = new AuthorDto(
                null,
                "test",
                1900,
                Set.of()
        );

        String authorJson = objectMapper.writeValueAsString(author);

        String createdAuthorJson = mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Author authorDtoResponse = objectMapper.readValue(createdAuthorJson, Author.class);

        Assertions.assertNotNull(authorDtoResponse.id());
        Assertions.assertEquals(authorDtoResponse.name(), author.name());
        Assertions.assertTrue(authorRepository.existsById(authorDtoResponse.id()));
    }

    @Test
    void successAuthorDeletion() throws Exception {
        Author author = authorService.createAuthor(new Author(
                null,
                "test",
                1900,
                Set.of()
        ));
        Set<Book> authorBooks = IntStream.range(0, 10)
                .mapToObj(i -> createBookToAuthor(author.id()))
                .collect(Collectors.toSet());

        mockMvc.perform(
                delete("/authors/{id}", author.id()))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));

        Assertions.assertFalse(authorRepository.existsById(author.id()));
        authorBooks.forEach(book -> {
            Book updatedBook = bookService.findById(book.id());
            Assertions.assertNull(updatedBook.authorId());
        });
    }

    private Book createBookToAuthor(Long id){
        return bookService.createBook(new Book(
                null,
                "test" + getRandomInt(),
                id,
                2024,
                100,
                6000
        ));
    }
}
