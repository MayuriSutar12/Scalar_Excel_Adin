package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "M_questioninfo_ai")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionInfoAI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "status_ai")
    private int statusAi;

    @Column(name = "createdOn", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "GenAi_topics", nullable = false, length = 255)
    private String genAiTopics;

    @OneToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private QuestionInfo questionInfo;

}
