package com.abnamro.assessment.mothers;

import java.time.LocalDateTime;
import java.util.List;

import com.abnamro.assessment.shared.references.RecipeRef;
import com.abnamro.assessment.recipes.repositories.entities.Recipe;

public class RecipesMother {

    private static final LocalDateTime SOME_DATE = LocalDateTime.of(2020,1,1,10,0,0);

    public static Recipe.RecipeBuilder roastedSardinesRecipe() {
        return Recipe.builder()
                     .name("Roasted sardines")
                     .isVegetarian(false)
                     .suitableFor(2)
                     .createdDate(SOME_DATE)
                     .recipeRef(RecipeRef.randomRef())
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

    public static Recipe.RecipeBuilder roastedPorkLoinRecipe() {
        return Recipe.builder()
                     .name("Roasted pork loin")
                     .isVegetarian(false)
                     .suitableFor(8)
                     .createdDate(SOME_DATE.plusDays(1))
                     .recipeRef(RecipeRef.randomRef())
                     .ingredients(List.of(
                         "2 pounds boneless pork loin roast",
                         "3 cloves garlic, minced",
                         "¼ cup olive oil",
                         "½ cup white wine",
                         "salt and pepper to taste"
                     ))
                     .cookingInstructions(List.of(
                         "Preheat oven to 175 degrees C.",
                         "Crush garlic with salt and pepper, making a paste.",
                         "Pierce meat with a sharp knife in several places and press the garlic paste into the openings.",
                         "Place pork loin into oven. Cook until the pork is no longer pink in the center, about 1 hour.",
                         "Heat the wine in the pan and stir to loosen browned bits of food on the bottom."
                     ));
    }

}
