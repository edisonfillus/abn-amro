package com.abnamro.assessment.recipes.services.dtos;

import java.time.LocalDateTime;

import com.abnamro.assessment.shared.references.RecipeRef;
import lombok.Builder;
import lombok.Value;

@Value @Builder
public class RecipeListViewDTO {

    RecipeRef recipeRef;

    String name;

    Boolean isVegetarian;

    Integer suitableFor;

    LocalDateTime createdDate;

}
