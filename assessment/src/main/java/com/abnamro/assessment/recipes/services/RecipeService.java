package com.abnamro.assessment.recipes.services;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import com.abnamro.assessment.recipes.services.dtos.CreateRecipeDTO;
import com.abnamro.assessment.recipes.services.dtos.RecipeDTO;
import com.abnamro.assessment.recipes.repositories.RecipeRepository;
import com.abnamro.assessment.recipes.repositories.entities.Recipe;
import com.abnamro.assessment.recipes.services.exceptions.RecipeNotFoundException;
import com.abnamro.assessment.recipes.services.mappers.RecipeServiceMapper;
import com.abnamro.assessment.shared.references.RecipeRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeServiceMapper recipeServiceMapper;
    private final Clock clock;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, RecipeServiceMapper recipeServiceMapper, Clock clock) {
        this.recipeRepository = recipeRepository;
        this.recipeServiceMapper = recipeServiceMapper;
        this.clock = clock;
    }

    @Transactional
    public RecipeDTO createRecipe(CreateRecipeDTO createRecipeDTO) {
        Recipe recipe = recipeServiceMapper.mapToRecipe(createRecipeDTO);
        recipe.setRecipeRef(RecipeRef.randomRef());
        recipe.setCreatedDate(LocalDateTime.now(clock));
        recipeRepository.save(recipe);
        return recipeServiceMapper.mapToRecipeDTO(recipe);
    }

    @Transactional(readOnly = true)
    public Optional<RecipeDTO> findRecipe(RecipeRef recipeRef) {
        return recipeRepository.findRecipeByRecipeRef(recipeRef)
                               .map(recipeServiceMapper::mapToRecipeDTO);
    }


    @Transactional
    public void deleteRecipeByReference(RecipeRef recipeRef) {
        Long count = recipeRepository.deleteByRecipeRef(recipeRef);
        if (count == 0) {
            throw new RecipeNotFoundException(recipeRef);
        }
    }

}
