package com.abnamro.assessment.recipes.services;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;

import com.abnamro.assessment.recipes.services.dtos.CreateRecipeDTO;
import com.abnamro.assessment.recipes.services.dtos.RecipeDTO;
import com.abnamro.assessment.recipes.repositories.RecipeRepository;
import com.abnamro.assessment.recipes.repositories.entities.Recipe;
import com.abnamro.assessment.recipes.services.mappers.RecipeServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        recipe.setCreatedDate(LocalDateTime.now(clock));
        recipeRepository.save(recipe);
        return recipeServiceMapper.mapToRecipeDTO(recipe);
    }

}