package com.abnamro.assessment.recipes.repositories;

import java.util.Optional;

import com.abnamro.assessment.recipes.repositories.entities.Recipe;
import com.abnamro.assessment.shared.references.RecipeRef;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.Repository;

public interface RecipeRepository extends Repository<Recipe, Long> {

    Recipe save(Recipe entity);

    Long deleteByRecipeRef(RecipeRef reference);

    @EntityGraph(attributePaths = {"ingredients", "cookingInstructions"}) // Eager load in find to optimize, as we return the full context
    Optional<Recipe> findRecipeByRecipeRef(RecipeRef recipeRef);

}
