package com.chari.learning.spring.ai.spring_ai.dvdrental.repository;

import com.chari.learning.spring.ai.spring_ai.dvdrental.model.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Long> {
  List<Movie> findByTitleContainingIgnoreCase(String title);

  @Query(
      value = """
            SELECT *
            FROM film f
            WHERE EXISTS (
                SELECT 1
                FROM unnest( string_to_array(:words, ',') ) AS w
                WHERE LOWER(f.title) LIKE CONCAT('%', LOWER(w), '%')
            )
        """,
      nativeQuery = true
  )
  List<Movie> searchByWords(@Param("words") String words);
}
