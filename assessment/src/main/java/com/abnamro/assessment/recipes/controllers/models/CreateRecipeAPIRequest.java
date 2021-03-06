package com.abnamro.assessment.recipes.controllers.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value @Builder
@Jacksonized
public class CreateRecipeAPIRequest {

    @Size(min = 1, max = 100)
    @NotEmpty
    String name;

    @NotNull
    Boolean isVegetarian;

    @NotNull
    @Min(1)
    Integer suitableFor;

    @NotEmpty
    List<String> ingredients;

    @NotEmpty
    List<String> cookingInstructions;

}
