package com.example.javaquizhub.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Data
@Table(name = "answer")
public class TestAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "option_letter")
    private char optionLetter;

    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne
    @JoinColumn(name ="question_id" )
    private Test test;

    @Column(name= "is_correct")
    private boolean isCorrect;

    @Override
    public String toString() {
        return "TestAnswer{" +
                "id=" + id +
                ", optionLetter=" + optionLetter +
                ", answerText='" + answerText + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
