package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.sql.Blob;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
//@Table(name = "questions_group")
@Immutable
public class QuestionsGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 45)
    private String name;

    private Blob image;

    @Column(columnDefinition = "TEXT")
    private String text;

    private Integer no_of_questions;
}
