package com.abnamro.assessment.recipes.repositories.projections;

import java.time.LocalDateTime;

import com.abnamro.assessment.shared.references.RecipeRef;

public interface RecipeListProjection {

    RecipeRef getRecipeRef();

    String getName();

    Boolean getIsVegetarian();

    Integer getSuitableFor();

    LocalDateTime getCreatedDate();

}
