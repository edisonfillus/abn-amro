package com.abnamro.assessment.recipes.repositories;

import java.util.Optional;

import com.abnamro.assessment.recipes.repositories.entities.Recipe;
import com.abnamro.assessment.shared.references.RecipeRef;
import org.springframework.data.repository.Repository;

public interface RecipeRepository extends Repository<Recipe, Long> {

    Recipe save(Recipe entity);

    Long deleteByRecipeRef(RecipeRef reference);

    Optional<Recipe> findRecipeByRecipeRef(RecipeRef recipeRef);

}
