package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "question_data_temp")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDataTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "question_latex", nullable = false, length = 1000)
    private String questionLatex;

    @Column(name = "question_imageUrl", nullable = false, length = 255)
    private String questionImageUrl;

    @Column(name = "question_image_processedUrl", length = 255)
    private String questionImageProcessedUrl;

    @Column(name = "complexity", nullable = false)
    private int complexity;

    @Column(name = "created_on", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updatedOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(name = "IsAnsMatch", nullable = false)
    private Boolean isAnsMatch;

    @Column(name = "Question_type", nullable = false)
    private int questionType;

    @Column(name = "Question_sub_type", nullable = false)
    private int questionSubType;
}
