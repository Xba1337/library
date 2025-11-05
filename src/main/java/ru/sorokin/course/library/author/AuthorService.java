package ru.sorokin.course.library.author;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sorokin.course.library.books.BookRepository;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorEntityConverter authorEntityConverter;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, AuthorEntityConverter authorEntityConverter, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.authorEntityConverter = authorEntityConverter;
        this.bookRepository = bookRepository;
    }

    public Author createAuthor(Author author) {
        if (authorRepository.existsByName(author.name())){
            throw new IllegalArgumentException("Author name already exists");
        }
        AuthorEntity entityToSave = authorEntityConverter.converToEntity(author);

        return authorEntityConverter.convertToModel(authorRepository.save(entityToSave));
    }

    public boolean isAuthorExists(Long authorId) {
        return authorRepository.existsById(authorId);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAllWithBooks()
                .stream()
                .map(authorEntityConverter::convertToModel)
                .toList();
    }

    @Transactional
    public void deleteAuthor(Long authorId) {
        if (!isAuthorExists(authorId)) {
            throw new IllegalArgumentException("Author does not exist");
        }
        authorRepository.deleteAuthorFromBooks(authorId);
        authorRepository.deleteById(authorId);
    }
}
