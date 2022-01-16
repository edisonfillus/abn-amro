package com.abnamro.assessment.recipes.services;

import javax.validation.ConstraintViolationException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

import com.abnamro.assessment.mothers.RecipesMother;
import com.abnamro.assessment.recipes.repositories.entities.Recipe;
import com.abnamro.assessment.recipes.services.dtos.CreateRecipeDTO;
import com.abnamro.assessment.recipes.services.dtos.RecipeDTO;
import com.abnamro.assessment.recipes.services.exceptions.RecipeNotFoundException;
import com.abnamro.assessment.recipes.services.mappers.RecipeServiceMapper;
import com.abnamro.assessment.recipes.repositories.RecipeRepository;
import com.abnamro.assessment.shared.references.RecipeRef;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    private static final LocalDateTime SOME_DATE = LocalDateTime.of(2020,1,1,10,0,0);

    @Spy
    private RecipeServiceMapper recipeMapper = Mappers.getMapper(RecipeServiceMapper.class);

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private Clock clock;

    @InjectMocks
    private RecipeService recipeService;

    @Nested
    class CreateRecipe {

        @Test
        void givenRecipe_whenSuccessful_thenReturnCreatedRecipe() {
            // Given
            CreateRecipeDTO toCreate = CreateRecipeDTO.builder()
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

            given(clock.instant()).willReturn(SOME_DATE.toInstant(ZoneOffset.UTC));
            given(clock.getZone()).willReturn(ZoneOffset.UTC);

            // When
            final RecipeDTO result = recipeService.createRecipe(toCreate);

            // Then
            then(recipeRepository).should().save(any());
            assertThat(result.getCreatedDate()).isEqualTo(SOME_DATE);
            assertThat(result.getRecipeRef()).isNotNull();

        }
    }

    @Nested
    class DeleteRecipe {

        @Test
        void givenNoRecipe_whenDelete_thenThrowNotFoundException() {
            // Given
            RecipeRef reference = RecipeRef.randomRef();
            given(recipeRepository.deleteByRecipeRef(reference)).willReturn(0L);

            // When
            Throwable thrown = catchThrowable(() -> recipeService.deleteRecipeByReference(reference));

            // Then
            assertThat(thrown).isInstanceOf(RecipeNotFoundException.class).hasMessageContaining(reference.getValue());

        }

        @Test
        void givenRecipeOnRepository_whenDelete_thenFinishSuccessful() {

            // Given
            RecipeRef reference = RecipeRef.randomRef();
            given(recipeRepository.deleteByRecipeRef(reference)).willReturn(1L);

            // When
            recipeService.deleteRecipeByReference(reference);

            // Then
            then(recipeRepository).should().deleteByRecipeRef(reference);

        }

    }




}
