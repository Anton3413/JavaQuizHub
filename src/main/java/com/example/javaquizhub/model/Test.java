package com.example.javaquizhub.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "test" )
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "question")
    private String questionText;

    @Column(name = "explanation")
    private String questionExplanation;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;

    @OneToMany(mappedBy = "test")
    List<TestAnswer> testAnswers;

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", questionExplanation='" + questionExplanation + '\'' +
                ", category=" + category +
                '}';
    }
}
