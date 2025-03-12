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
//@Table(name = "question_type_mgmt")
@Immutable
public class QuestionTypeMgmt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 25)
    private String name;
}
