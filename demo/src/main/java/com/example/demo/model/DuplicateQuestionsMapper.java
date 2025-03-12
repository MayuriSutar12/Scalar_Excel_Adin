package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Immutable
//@Table(name = "duplicate_questions_mapper")
public class DuplicateQuestionsMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 500)
    private String question_ids;
}
