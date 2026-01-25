package ru.sorokin.course.library.author;

import org.springframework.stereotype.Component;
import ru.sorokin.course.library.books.BookDtoConverter;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorDtoConverter {

    private final BookDtoConverter bookDtoConverter;

    public AuthorDtoConverter(BookDtoConverter bookDtoConverter) {
        this.bookDtoConverter = bookDtoConverter;
    }

    public AuthorDto convertToDto(Author author) {
        return new AuthorDto(
                author.id(),
                author.name(),
                author.birthYear(),
                author.books() == null
                        ? Set.of()
                        : author.books()
                        .stream()
                        .map(bookDtoConverter::convertToDto)
                        .collect(Collectors.toSet())
        );
    }

    public Author convertToModel(AuthorDto authorDto) {
        return new Author(
                authorDto.id(),
                authorDto.name(),
                authorDto.birthYear(),
                authorDto.books() == null
                        ? Set.of()
                        : authorDto.books()
                        .stream()
                        .map(bookDtoConverter::convertToModel)
                        .collect(Collectors.toSet())
        );
    }

}
