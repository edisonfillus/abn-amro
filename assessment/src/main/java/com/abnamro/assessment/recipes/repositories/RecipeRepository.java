package com.abnamro.assessment.recipes.repositories;

import com.abnamro.assessment.recipes.repositories.entities.Recipe;
import com.abnamro.assessment.shared.references.RecipeRef;
import org.springframework.data.repository.Repository;

public interface RecipeRepository extends Repository<Recipe, Long> {

    Recipe save(Recipe entity);

    Long deleteByRecipeRef(RecipeRef reference);

}
