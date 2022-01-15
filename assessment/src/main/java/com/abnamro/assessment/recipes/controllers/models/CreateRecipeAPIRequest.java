package com.abnamro.assessment.recipes.controllers.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value @Builder
@Jacksonized
public class CreateRecipeAPIRequest {

    @Size(max = 100)
    @NotEmpty
    String name;

    @NotNull
    Boolean isVegetarian;

    @NotNull
    @Positive
    Integer suitableFor;

    @NotEmpty
    List<String> ingredients;

    @NotEmpty
    List<String> cookingInstructions;

}
