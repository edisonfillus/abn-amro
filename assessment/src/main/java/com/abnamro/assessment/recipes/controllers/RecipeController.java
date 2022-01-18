package com.abnamro.assessment.recipes.controllers;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.net.URI;

import com.abnamro.assessment.recipes.controllers.mappers.RecipeControllerMapper;
import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIRequest;
import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIResponse;
import com.abnamro.assessment.recipes.controllers.models.RecipeAPIResponse;
import com.abnamro.assessment.recipes.controllers.models.ListRecipesAPIResponse;
import com.abnamro.assessment.recipes.controllers.models.UpdateRecipeAPIRequest;
import com.abnamro.assessment.recipes.services.RecipeService;
import com.abnamro.assessment.recipes.services.dtos.CreateRecipeDTO;
import com.abnamro.assessment.recipes.services.dtos.RecipeDTO;
import com.abnamro.assessment.recipes.services.dtos.UpdateRecipeDTO;
import com.abnamro.assessment.shared.references.RecipeRef;
import com.abnamro.assessment.users.repositories.entities.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
        security = { @SecurityRequirement(name = "bearer-key") },
        responses = {
            @ApiResponse(responseCode = "201", description = "Recipe created"),
            @ApiResponse(responseCode = "400", description = "Invalid recipe supplied", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
    @PostMapping
    @Secured(Role.ROLE_EDITOR)
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
        security = { @SecurityRequirement(name = "bearer-key") },
        responses = {
            @ApiResponse(responseCode = "200", description = "Recipe found"),
            @ApiResponse(responseCode = "400", description = "Invalid recipe reference supplied", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
    @GetMapping("/{recipeRef}")
    @Secured({Role.ROLE_VIEWER, Role.ROLE_EDITOR})
    public ResponseEntity<RecipeAPIResponse> findRecipe(@PathVariable RecipeRef recipeRef) {
        return ResponseEntity.of(
            recipeService.findRecipe(recipeRef)
                         .map(recipeControllerMapper::mapToRecipeAPIResponse)
        );
    }

    @Operation(
        summary = "Update a Recipe",
        security = { @SecurityRequirement(name = "bearer-key") },
        responses = {
            @ApiResponse(responseCode = "200", description = "Recipe updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
    @PatchMapping("/{recipeRef}")
    @Secured(Role.ROLE_EDITOR)
    public RecipeAPIResponse updateRecipe(
        @PathVariable RecipeRef recipeRef,
        @RequestBody @NotNull @Valid UpdateRecipeAPIRequest request
    ) {
        UpdateRecipeDTO updateRecipeDTO = recipeControllerMapper.mapToUpdateRecipeDTO(request);
        RecipeDTO result = recipeService.updateRecipe(recipeRef, updateRecipeDTO);
        return recipeControllerMapper.mapToRecipeAPIResponse(result);
    }

    @Operation(
        summary = "List all recipes ordered by created date",
        security = { @SecurityRequirement(name = "bearer-key") },
        responses = {
            @ApiResponse(responseCode = "200", description = "List returned"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination params supplied", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
    @GetMapping()
    @Secured({Role.ROLE_VIEWER, Role.ROLE_EDITOR})
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
        security = { @SecurityRequirement(name = "bearer-key") },
        responses = {
            @ApiResponse(responseCode = "204", description = "Recipe deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
    @DeleteMapping("/{recipeRef}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Secured(Role.ROLE_EDITOR)
    public void delete(@PathVariable @Valid RecipeRef recipeRef) {
        recipeService.deleteRecipeByReference(recipeRef);
    }

}
