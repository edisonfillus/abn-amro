package com.abnamro.assessment.recipes.controllers.mappers;

import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIRequest;
import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIResponse;
import com.abnamro.assessment.recipes.controllers.models.RecipeAPIResponse;
import com.abnamro.assessment.recipes.controllers.models.ListRecipesAPIResponse;
import com.abnamro.assessment.recipes.controllers.models.UpdateRecipeAPIRequest;
import com.abnamro.assessment.recipes.services.dtos.CreateRecipeDTO;
import com.abnamro.assessment.recipes.services.dtos.RecipeDTO;
import com.abnamro.assessment.recipes.services.dtos.RecipeListViewDTO;
import com.abnamro.assessment.recipes.services.dtos.UpdateRecipeDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface RecipeControllerMapper {

    CreateRecipeDTO mapToCreateRecipeDTO(CreateRecipeAPIRequest source);

    CreateRecipeAPIResponse mapToCreateRecipeAPIResponse(RecipeDTO source);

    RecipeAPIResponse mapToRecipeAPIResponse(RecipeDTO source);

    ListRecipesAPIResponse mapToListRecipesAPIResponse(RecipeListViewDTO source);

    UpdateRecipeDTO mapToUpdateRecipeDTO(UpdateRecipeAPIRequest request);

}
