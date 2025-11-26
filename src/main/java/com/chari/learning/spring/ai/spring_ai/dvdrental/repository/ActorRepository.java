package com.chari.learning.spring.ai.spring_ai.dvdrental.repository;

import com.chari.learning.spring.ai.spring_ai.dvdrental.model.Actor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActorRepository extends JpaRepository<Actor, Long> {
  @Query(
      value = """
            SELECT * FROM actor
            WHERE LOWER(first_name) LIKE LOWER(CONCAT('%', :name, '%'))
               OR LOWER(last_name) LIKE LOWER(CONCAT('%', :name, '%'))
        """,
      nativeQuery = true
  )
  List<Actor> searchByName(@Param("name") String name);

  @Query(
      value = """
            SELECT *
            FROM actor a
            WHERE EXISTS (
                SELECT 1
                FROM unnest( string_to_array(:names, ',') ) AS w
                WHERE LOWER(a.first_name) LIKE LOWER(CONCAT('%', LOWER(w), '%'))
               OR LOWER(a.last_name) LIKE LOWER(CONCAT('%', LOWER(w), '%'))
            )
        """,
      nativeQuery = true
  )
  List<Actor> searchByNames(@Param("names") String names);
}
