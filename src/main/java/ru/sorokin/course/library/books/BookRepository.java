package ru.sorokin.course.library.books;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

//    List<BookEntity> findAllByAuthorIsAndCostLessThan(String authorName, Integer cost);

    @Query(value = """
            SELECT * FROM books 
            WHERE (:authorId IS NULL OR author_id = :authorId)
            AND (:cost IS NULL OR cost < :cost)
            """, nativeQuery = true)
    List<BookEntity> searchBooks(
            Long authorId,
            Integer cost,
            Pageable pageable);

    @Transactional
    @Modifying
    @Query("""
            UPDATE BookEntity b
            SET b.name = :name, b.authorId = :authorId, b.publicationYear = :pubYear, b.pageNumber = :pageNum, b.cost = :cost
            WHERE b.id = :id
             """)
    void updateBook(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("authorId") Long authorId,
            @Param("pubYear") Integer publicationYear,
            @Param("pageNum") Integer pageNumber,
            @Param("cost") Integer cost
    );
}
