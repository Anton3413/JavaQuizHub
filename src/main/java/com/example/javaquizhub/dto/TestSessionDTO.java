package com.example.javaquizhub.dto;

import com.example.javaquizhub.validation.TestSessionDTOValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TestSessionDTOValidation
public class TestSessionDTO {

    int bookId;


    @Positive(message = "Must be greater than 0!")
    int numberOfTests;

    @NotNull
    List<String> testCategories;
}
