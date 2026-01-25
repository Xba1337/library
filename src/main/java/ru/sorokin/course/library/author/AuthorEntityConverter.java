package ru.sorokin.course.library.author;

import org.springframework.stereotype.Component;
import ru.sorokin.course.library.books.BookEntityConverter;

import java.util.stream.Collectors;

@Component
public class AuthorEntityConverter {

    private final BookEntityConverter bookEntityConverter;

    public AuthorEntityConverter(BookEntityConverter bookEntityConverter) {
        this.bookEntityConverter = bookEntityConverter;
    }

    public AuthorEntity converToEntity(Author author) {
        return new AuthorEntity(
                author.id(),
                author.name(),
                author.birthYear(),
                author.books()
                        .stream()
                        .map(bookEntityConverter::convertToEntity)
                        .collect(Collectors.toSet())
        );
    }

    public Author convertToModel(AuthorEntity authorEntity) {
        return new Author(
                authorEntity.getId(),
                authorEntity.getName(),
                authorEntity.getBirthYear(),
                authorEntity.getBooks()
                        .stream()
                        .map(bookEntityConverter::convertToModel)
                        .collect(Collectors.toSet())
        );
    }
}
