package com.abnamro.assessment.recipes.controllers.models;

import java.util.List;

import com.abnamro.assessment.shared.references.RecipeRef;
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
}
