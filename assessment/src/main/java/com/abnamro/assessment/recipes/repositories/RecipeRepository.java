package com.abnamro.assessment.recipes.repositories;

import com.abnamro.assessment.recipes.repositories.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Override
    Recipe save(Recipe entity);

}
