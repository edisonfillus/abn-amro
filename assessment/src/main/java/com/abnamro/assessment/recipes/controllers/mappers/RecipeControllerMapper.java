package com.abnamro.assessment.recipes.controllers.mappers;

import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIRequest;
import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIResponse;
import com.abnamro.assessment.recipes.controllers.models.FindRecipeAPIResponse;
import com.abnamro.assessment.recipes.services.dtos.CreateRecipeDTO;
import com.abnamro.assessment.recipes.services.dtos.RecipeDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RecipeControllerMapper {

    CreateRecipeDTO mapToCreateRecipeDTO(CreateRecipeAPIRequest source);

    CreateRecipeAPIResponse mapToCreateRecipeAPIResponse(RecipeDTO source);

    FindRecipeAPIResponse mapToFindRecipeAPIResponse(RecipeDTO recipeDTO);
}
