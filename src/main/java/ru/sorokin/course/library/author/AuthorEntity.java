package ru.sorokin.course.library.author;

import jakarta.persistence.*;
import ru.sorokin.course.library.books.BookEntity;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "authors")
@NamedEntityGraph(name = "author-with-books", attributeNodes = {
        @NamedAttributeNode("books")
})
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Integer birthYear;

    @OneToMany
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Set<BookEntity> books;

    public AuthorEntity(Long id, String name, Integer birthYear, Set<BookEntity> books) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.books = books;
    }

    public AuthorEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Set<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(Set<BookEntity> books) {
        this.books = books;
    }
}
