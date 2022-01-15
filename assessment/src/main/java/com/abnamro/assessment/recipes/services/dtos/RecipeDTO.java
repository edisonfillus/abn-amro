package com.abnamro.assessment.recipes.services.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value @Builder
public class RecipeDTO {

    String name;

    Boolean isVegetarian;

    Integer suitableFor;

    LocalDateTime createdDate;

    List<String> ingredients;

    List<String> cookingInstructions;

}
