package com.chari.learning.spring.ai.spring_ai.dvdrental.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "actor")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Actor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name= "actor_id")
  private long actorID;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @ManyToMany(mappedBy = "actors")
  private List<Movie> movies;
}
