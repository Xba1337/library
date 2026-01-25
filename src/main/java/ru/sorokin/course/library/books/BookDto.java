package ru.sorokin.course.library.books;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookDto(

        @Null
        Long id,

        @NotBlank
        @Size(max = 50)
        String name,

        Long authorId,

        @JsonProperty("pubYear")
        @Min(0)
        @NotNull
        Integer publicationYear,

        @JsonProperty("pages")
        @Min(1)
        @Max(10000)
        @NotNull
        Integer pageNumber,

        @NotNull
        @Min(0)
        @Max(100000)
        Integer cost
) {
}
