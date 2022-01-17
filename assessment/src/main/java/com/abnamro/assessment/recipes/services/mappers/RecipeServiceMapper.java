package com.abnamro.assessment.recipes.services.mappers;

import com.abnamro.assessment.recipes.repositories.projections.RecipeListProjection;
import com.abnamro.assessment.recipes.services.dtos.CreateRecipeDTO;
import com.abnamro.assessment.recipes.services.dtos.RecipeDTO;
import com.abnamro.assessment.recipes.repositories.entities.Recipe;
import com.abnamro.assessment.recipes.services.dtos.RecipeListViewDTO;
import com.abnamro.assessment.recipes.services.dtos.UpdateRecipeDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface RecipeServiceMapper {

    @Mapping(target = "recipeId", ignore = true)
    @Mapping(target = "recipeRef", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    Recipe mapToRecipe(CreateRecipeDTO source);

    RecipeDTO mapToRecipeDTO(Recipe source);

    RecipeListViewDTO mapToRecipeListViewDTO(RecipeListProjection source);

    @Mapping(target = "recipeId", ignore = true)
    @Mapping(target = "recipeRef", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // Don't update on null values
    void mapUpdateRecipeDTOIntoRecipe(UpdateRecipeDTO changes, @MappingTarget Recipe recipe);

}
