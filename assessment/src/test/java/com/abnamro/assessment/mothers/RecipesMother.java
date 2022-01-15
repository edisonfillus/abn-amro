package com.abnamro.assessment.mothers;

import java.time.LocalDateTime;
import java.util.List;

import com.abnamro.assessment.recipes.repositories.entities.Recipe;

public class RecipesMother {

    private static final LocalDateTime SOME_DATE = LocalDateTime.of(2020,1,1,10,0,0);

    public static Recipe.RecipeBuilder roastedSardinesRecipe() {
        return Recipe.builder()
                     .name("Roasted sardines")
                     .isVegetarian(false)
                     .suitableFor(2)
                     .createdDate(SOME_DATE)
                     .ingredients(List.of(
                         "600 grams fresh whole sardines",
                         "4 tablespoons extra virgin olive oil",
                         "1,5 teaspoon of salt"
                     ))
                     .cookingInstructions(List.of(
                         "Clean the sardines by removing any scales and rinse well",
                         "Place the sardines on a tray",
                         "Dizzle with olive oil and sprinkle the salt",
                         "Preheat the oven to 250°C",
                         "Cook the sardines for approximately 15 minutes until they get nice and roasted"
                     ));
    }

}
