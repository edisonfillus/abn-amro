package com.abnamro.assessment.recipes.controllers.models;

import java.time.LocalDateTime;
import java.util.List;

import com.abnamro.assessment.shared.references.RecipeRef;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value @Builder @Jacksonized
public class CreateRecipeAPIResponse {

    RecipeRef recipeRef;

    String name;

    Boolean isVegetarian;

    Integer suitableFor;

    List<String> ingredients;

    List<String> cookingInstructions;

    @Schema(type = "string", pattern = "^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}", example = "01-01-2020 10:00")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    LocalDateTime createdDate;
}
