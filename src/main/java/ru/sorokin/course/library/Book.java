package ru.sorokin.course.library;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Book(

        @Null
        Long id,

        @NotBlank
        @Size(max = 50)
        String name,

        @NotBlank
        @Size(max = 50)
        String author,

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
