package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "options_data_temp")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionsDataTemp{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "option_id", nullable = false)
    private Long optionId;

    @Column(name = "is_answer")
    private Boolean isAnswer;

    @Column(name = "answer", length = 50)
    private String answer;

    @Column(name = "option_latex", nullable = false, length = 255)
    private String optionLatex;


    @Column(name = "option_image_processedUrl", length = 255)
    private String optionImageProcessedUrl;

    @Column(name = "genAi_isAnswer")
    private Integer genAiIsAnswer;

    @Column(name = "genAi_Answer", length = 50)
    private String genAiAnswer;


    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id", nullable = false)
    private QuestionDataTemp questionDataTemp;
}