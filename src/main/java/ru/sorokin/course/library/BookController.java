package ru.sorokin.course.library;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping
    public Book test() {
        return new Book(1L,
                "a",
                "b",
                1900,
                100,
                1000);
    }
}
