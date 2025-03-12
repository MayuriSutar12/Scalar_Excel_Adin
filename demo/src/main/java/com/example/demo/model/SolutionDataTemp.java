package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "solution_data_temp")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolutionDataTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solution_id", nullable = false)
    private int solutionId;

    @Column(name = "solution_latex", nullable = false, length = 1000)
    private String solutionLatex;


    @Column(name = "solution_image_processedUrl", length = 255)
    private String solutionImageProcessedUrl;

    @OneToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private QuestionDataTemp questionDataTemp;
}

