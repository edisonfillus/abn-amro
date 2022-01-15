package com.abnamro.assessment.recipes.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.net.URI;

import com.abnamro.assessment.recipes.controllers.mappers.RecipeControllerMapper;
import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIRequest;
import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIResponse;
import com.abnamro.assessment.recipes.services.RecipeService;
import com.abnamro.assessment.recipes.services.dtos.CreateRecipeDTO;
import com.abnamro.assessment.recipes.services.dtos.RecipeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(RecipeController.BASE_PATH)
public class RecipeController {

    public static final String BASE_PATH = "/api/v1/recipes";

    private final RecipeService recipeService;
    private final RecipeControllerMapper recipeControllerMapper;

    @Autowired
    public RecipeController(RecipeService recipeService, RecipeControllerMapper recipeControllerMapper) {
        this.recipeService = recipeService;
        this.recipeControllerMapper = recipeControllerMapper;
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<CreateRecipeAPIResponse> createRecipe(
        @RequestBody @NotNull @Valid CreateRecipeAPIRequest request,
        UriComponentsBuilder uriBuilder
    ) {
        CreateRecipeDTO createRecipeDTO = recipeControllerMapper.mapToCreateRecipeDTO(request);
        RecipeDTO result = recipeService.createRecipe(createRecipeDTO);
        CreateRecipeAPIResponse response = recipeControllerMapper.mapToCreateRecipeAPIResponse(result);
        URI uri = uriBuilder.path("{recipeId}").buildAndExpand("1").toUri();
        return ResponseEntity.created(uri).body(response);
    }

}
