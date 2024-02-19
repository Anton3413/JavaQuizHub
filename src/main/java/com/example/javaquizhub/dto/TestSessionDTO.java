package com.example.javaquizhub.dto;

import com.example.javaquizhub.validation.TestSessionDTOValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
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
