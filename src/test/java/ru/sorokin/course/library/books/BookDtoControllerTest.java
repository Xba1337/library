package ru.sorokin.course.library.books;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.sorokin.course.library.AbstractTest;
import ru.sorokin.course.library.author.Author;
import ru.sorokin.course.library.author.AuthorService;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookDtoControllerTest extends AbstractTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookRepository bookRepository;


    @Test
    void successCreateBook() throws Exception {
        Author author = createDummyAuthor();
        BookDto bookDto = new BookDto(
                null,
                "name",
                author.id(),
                2000,
                100,
                1000
        );

        String bookJson = objectMapper.writeValueAsString(bookDto);

        String createdBookJson = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        BookDto bookDtoResponse = objectMapper.readValue(createdBookJson, BookDto.class);

        Assertions.assertNotNull(bookDtoResponse.id());
        Assertions.assertEquals(bookDto.name(), bookDtoResponse.name());
        Assertions.assertTrue(bookRepository.existsById(bookDtoResponse.id()));
    }

    @Test
    void notValidCreateBook() throws Exception {
        BookDto bookDto = new BookDto(
                null,
                null,
                1L,
                2000,
                100,
                1000
        );

        String bookJson = objectMapper.writeValueAsString(bookDto);

        String createdBookJson = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().is(400))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void successSearchBookById() throws Exception {
        Author author = createDummyAuthor();
        Book book = new Book(
                null,
                "name",
                author.id(),
                2000,
                100,
                1000
        );

        book = bookService.createBook(book);
        String foundBookJson = mockMvc.perform(get("/books/{id}", book.id()))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        BookDto bookDtoResponse = objectMapper.readValue(foundBookJson, BookDto.class);

        org.assertj.core.api.Assertions.assertThat(book)
                .usingRecursiveComparison()
                .isEqualTo(bookDtoResponse);
    }

    @Test
    void failSearchBookById() throws Exception {
        mockMvc.perform(get("/books/{id}", Integer.MAX_VALUE))
                .andExpect(status().is(404))
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    private Author createDummyAuthor(){
    return authorService.createAuthor(new Author(
            null,
            "test" + getRandomInt(),
            1900,
            Set.of()
    ));
    }
}