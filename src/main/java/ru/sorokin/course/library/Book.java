package ru.sorokin.course.library;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Book(

        long id,

        String name,

        String author,

        @JsonProperty("pubYear")
        int publicationYear,

        @JsonProperty("pages")
        int pageNumber,

        int cost) {
}
