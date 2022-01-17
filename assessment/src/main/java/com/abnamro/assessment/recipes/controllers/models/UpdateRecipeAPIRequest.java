package com.abnamro.assessment.recipes.controllers.models;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value @Builder @Jacksonized
public class UpdateRecipeAPIRequest {

    @Size(min = 1, max = 100)
    String name;

    Boolean isVegetarian;

    @Positive
    Integer suitableFor;

    @Size(min = 1)
    List<String> ingredients;

    @Size(min = 1)
    List<String> cookingInstructions;

}
