package com.abnamro.assessment.recipes.services.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value @Builder
public class CreateRecipeDTO {

    String name;

    Boolean isVegetarian;

    Integer suitableFor;

    List<String> ingredients;

    List<String> cookingInstructions;

}
