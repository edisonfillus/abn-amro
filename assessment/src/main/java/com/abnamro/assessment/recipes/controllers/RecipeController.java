package com.abnamro.assessment.recipes.controllers;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.net.URI;

import com.abnamro.assessment.recipes.controllers.mappers.RecipeControllerMapper;
import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIRequest;
import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIResponse;
import com.abnamro.assessment.recipes.controllers.models.FindRecipeAPIResponse;
import com.abnamro.assessment.recipes.controllers.models.ListRecipesAPIResponse;
import com.abnamro.assessment.recipes.services.RecipeService;
import com.abnamro.assessment.recipes.services.dtos.CreateRecipeDTO;
import com.abnamro.assessment.recipes.services.dtos.RecipeDTO;
import com.abnamro.assessment.shared.references.RecipeRef;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Recipes")
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


    @Operation(
        summary = "Create a new Recipe",
        responses = {
            @ApiResponse(responseCode = "201", description = "Recipe created"),
            @ApiResponse(responseCode = "400", description = "Invalid recipe supplied", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
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

    @Operation(
        summary = "Find a Recipe by reference",
        responses = {
            @ApiResponse(responseCode = "200", description = "Recipe found"),
            @ApiResponse(responseCode = "400", description = "Invalid recipe reference supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
    @GetMapping("/{recipeRef}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<FindRecipeAPIResponse> findRecipe(@PathVariable RecipeRef recipeRef) {
        return ResponseEntity.of(
            recipeService.findRecipe(recipeRef)
                         .map(recipeControllerMapper::mapToFindRecipeAPIResponse)
        );
    }

    @Operation(
        summary = "List all recipes ordered by created date",
        responses = {
            @ApiResponse(responseCode = "200", description = "List returned"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination params supplied", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
    @GetMapping()
    @PreAuthorize("permitAll()")
    public Page<ListRecipesAPIResponse> findAllRecipes(
        @RequestParam(value = "page", defaultValue = "0") @Positive @Max(Integer.MAX_VALUE) int page,
        @RequestParam(value = "size", defaultValue = "20") @Positive @Max(100) int size
    ) {
        // Not using Spring's Pageable as request params to not expose sort operations.
        return recipeService.findAllRecipes(PageRequest.of(page, size))
                            .map(recipeControllerMapper::mapToListRecipesAPIResponse);
    }

    @Operation(
        summary = "Delete an existent Recipe",
        responses = {
            @ApiResponse(responseCode = "204", description = "Recipe deleted"),
            @ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
    @DeleteMapping("/{recipeRef}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("permitAll()")
    public void delete(@PathVariable @Valid RecipeRef recipeRef) {
        recipeService.deleteRecipeByReference(recipeRef);
    }

}
