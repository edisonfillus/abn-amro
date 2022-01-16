package com.abnamro.assessment.recipes.controllers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.abnamro.assessment.recipes.controllers.mappers.RecipeControllerMapperImpl;
import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIRequest;
import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIResponse;
import com.abnamro.assessment.recipes.controllers.models.FindRecipeAPIResponse;
import com.abnamro.assessment.recipes.controllers.models.ListRecipesAPIResponse;
import com.abnamro.assessment.recipes.services.RecipeService;
import com.abnamro.assessment.recipes.services.dtos.RecipeDTO;
import com.abnamro.assessment.recipes.services.dtos.RecipeListViewDTO;
import com.abnamro.assessment.recipes.services.exceptions.RecipeNotFoundException;
import com.abnamro.assessment.shared.controllers.RestPageImpl;
import com.abnamro.assessment.shared.references.RecipeRef;
import com.abnamro.assessment.shared.security.MethodSecurityConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
@Import(value = {RecipeControllerMapperImpl.class, MethodSecurityConfig.class})
class RecipeControllerTest {

    private static final LocalDateTime SOME_DATE = LocalDateTime.of(2020,1,1,10,0,0);
    private static final RecipeRef SOME_RECIPE_REF = RecipeRef.randomRef();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class CreateRecipe {
        @Test
        void givenValidRecipe_whenCreate_thenReturnOK() throws Exception {

            // Given
            CreateRecipeAPIRequest request = CreateRecipeAPIRequest
                .builder()
                .name("Roasted sardines")
                .isVegetarian(false)
                .suitableFor(2)
                .ingredients(List.of(
                    "600 grams fresh whole sardines",
                    "4 tablespoons extra virgin olive oil",
                    "1,5 teaspoon of salt"
                ))
                .cookingInstructions(List.of(
                    "Clean the sardines by removing any scales and rinse well",
                    "Place the sardines on a tray",
                    "Dizzle with olive oil and sprinkle the salt",
                    "Preheat the oven to 250°C",
                    "Cook the sardines for approximately 15 minutes until they get nice and roasted"
                ))
                .build();

            RecipeDTO result = RecipeDTO
                .builder()
                .name("Roasted sardines")
                .isVegetarian(false)
                .suitableFor(2)
                .createdDate(SOME_DATE)
                .recipeRef(SOME_RECIPE_REF)
                .ingredients(List.of(
                    "600 grams fresh whole sardines",
                    "4 tablespoons extra virgin olive oil",
                    "1,5 teaspoon of salt"
                ))
                .cookingInstructions(List.of(
                    "Clean the sardines by removing any scales and rinse well",
                    "Place the sardines on a tray",
                    "Dizzle with olive oil and sprinkle the salt",
                    "Preheat the oven to 250°C",
                    "Cook the sardines for approximately 15 minutes until they get nice and roasted"
                ))
                .build();
            given(recipeService.createRecipe(any())).willReturn(result);

            // When
            String resultBody = mockMvc
                .perform(
                    post(RecipeController.BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

            CreateRecipeAPIResponse response = objectMapper.readValue(resultBody, CreateRecipeAPIResponse.class);

            // Then
            then(recipeService).should().createRecipe(any());
            assertThat(response.getName()).isEqualTo(request.getName());
        }
    }

    @Nested
    class FindRecipe {

        @Test
        void givenExistentRecipe_whenFind_thenReturnRecipeOK() throws Exception {

            // Given
            RecipeDTO result = RecipeDTO
                .builder()
                .name("Roasted sardines")
                .recipeRef(SOME_RECIPE_REF)
                .isVegetarian(false)
                .suitableFor(2)
                .createdDate(SOME_DATE)
                .ingredients(List.of(
                    "600 grams fresh whole sardines",
                    "4 tablespoons extra virgin olive oil",
                    "1,5 teaspoon of salt"
                ))
                .cookingInstructions(List.of(
                    "Clean the sardines by removing any scales and rinse well",
                    "Place the sardines on a tray",
                    "Dizzle with olive oil and sprinkle the salt",
                    "Preheat the oven to 250°C",
                    "Cook the sardines for approximately 15 minutes until they get nice and roasted"
                ))
                .build();

            given(recipeService.findRecipe(SOME_RECIPE_REF)).willReturn(Optional.of(result));

            // When
            String resultBody = mockMvc
                .perform(
                    get(RecipeController.BASE_PATH + "/" + SOME_RECIPE_REF.getValue())
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

            FindRecipeAPIResponse response = objectMapper.readValue(resultBody, FindRecipeAPIResponse.class);

            // Then
            then(recipeService).should().findRecipe(SOME_RECIPE_REF);
            assertThat(response.getName()).isEqualTo(result.getName());
        }

        @Test
        void givenNonExistentRecipe_whenFind_thenReturnNotFound() throws Exception {

            // Given
            given(recipeService.findRecipe(SOME_RECIPE_REF)).willReturn(Optional.empty());

            // When
            mockMvc
                .perform(
                    get(RecipeController.BASE_PATH + "/" + SOME_RECIPE_REF.getValue())
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());

            // Then
            then(recipeService).should().findRecipe(SOME_RECIPE_REF);
        }
    }

    @Nested
    class FindAllRecipes {

        @Test
        void givenExistentRecipes_whenFind_thenReturnRecipesOK() throws Exception {

            // Given
            List<RecipeListViewDTO> result = List.of(
                RecipeListViewDTO
                    .builder()
                    .name("Recipe 1")
                    .recipeRef(RecipeRef.randomRef())
                    .isVegetarian(false)
                    .suitableFor(2)
                    .createdDate(SOME_DATE)
                    .build()
                ,
                RecipeListViewDTO
                    .builder()
                    .name("Recipe 2")
                    .recipeRef(RecipeRef.randomRef())
                    .isVegetarian(false)
                    .suitableFor(2)
                    .createdDate(SOME_DATE)
                    .build()
            );
            Pageable pageable = PageRequest.of(0,20);

            given(recipeService.findAllRecipes(pageable)).willReturn(new PageImpl<>(result,pageable,2));

            // When
            String resultBody = mockMvc
                .perform(
                    get(RecipeController.BASE_PATH).accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

            Page<ListRecipesAPIResponse> response = objectMapper.readValue(resultBody,
                new TypeReference<RestPageImpl<ListRecipesAPIResponse>>() {});

            // Then
            then(recipeService).should().findAllRecipes(pageable);
            assertThat(response.getTotalElements()).isEqualTo(2);
        }
    }

    @Nested
    class DeleteRecipe {
        @Test
        void givenNoRecipe_whenDelete_thenReturnNotFound() throws Exception {

            // Given
            willThrow(new RecipeNotFoundException(SOME_RECIPE_REF)).given(recipeService).deleteRecipeByReference(eq(SOME_RECIPE_REF));

            // When
            mockMvc
                .perform(delete(RecipeController.BASE_PATH + "/" + SOME_RECIPE_REF.getValue()))
                .andDo(print())
                .andExpect(status().isNotFound());

            // Then
            then(recipeService).should().deleteRecipeByReference(eq(SOME_RECIPE_REF));
        }

        @Test
        void givenRecipe_whenDelete_thenReturnOKNoContent() throws Exception {

            // When
            mockMvc
                .perform(delete(RecipeController.BASE_PATH + "/" + SOME_RECIPE_REF.getValue()))
                .andDo(print())
                .andExpect(status().isNoContent());

            // Then
            then(recipeService).should().deleteRecipeByReference(eq(SOME_RECIPE_REF));
        }
    }

}
