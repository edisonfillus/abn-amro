package com.abnamro.assessment.recipes.services.mappers;

import com.abnamro.assessment.recipes.services.dtos.CreateRecipeDTO;
import com.abnamro.assessment.recipes.services.dtos.RecipeDTO;
import com.abnamro.assessment.recipes.repositories.entities.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RecipeServiceMapper {

    @Mapping(target = "recipeId", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    Recipe mapToRecipe(CreateRecipeDTO source);

    RecipeDTO mapToRecipeDTO(Recipe source);
}
