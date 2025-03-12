package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Immutable
//@Table(name = "questioninfo")
public class QuestionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long question_id;

    private BigInteger duration;

    @Column(columnDefinition = "TINYINT")
    private Integer complexity;

    @Column(columnDefinition = "TINYINT")
    private Integer approved;

    private Date createdOn;

    private Date updatedOn;

    @Size(max = 45)
    private String copyright;

    @Size(max = 2048)
    private String summary;

    @Size(max = 1024)
    private String question_text;

    private Integer question_status;

    @Column(columnDefinition = "TINYINT")
    private Integer isDeleted;

    private Integer question_type;

    private Integer is_solution_available;

    @JoinColumn(name = "question_type_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private QuestionTypeMgmt question_type_id;

    @JoinColumn(name = "group_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private QuestionsGroup questions_group;

    private Integer question_sub_type;

    @JoinColumn(name = "duplicate_mapper_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DuplicateQuestionsMapper duplicateQuestionsMapper;

    private Integer dedup_action_status;


}
