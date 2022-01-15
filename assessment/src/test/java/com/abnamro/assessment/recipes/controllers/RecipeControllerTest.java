package com.abnamro.assessment.recipes.controllers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import com.abnamro.assessment.shared.security.MethodSecurityConfig;
import com.abnamro.assessment.recipes.controllers.mappers.RecipeControllerMapperImpl;
import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIRequest;
import com.abnamro.assessment.recipes.controllers.models.CreateRecipeAPIResponse;
import com.abnamro.assessment.recipes.services.RecipeService;
import com.abnamro.assessment.recipes.services.dtos.RecipeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
@Import(value = {RecipeControllerMapperImpl.class, MethodSecurityConfig.class})
class RecipeControllerTest {

    private static final LocalDateTime SOME_DATE = LocalDateTime.of(2020,1,1,10,0,0);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

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
