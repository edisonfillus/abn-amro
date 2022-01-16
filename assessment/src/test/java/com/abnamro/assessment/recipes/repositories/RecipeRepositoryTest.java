package com.abnamro.assessment.recipes.repositories;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Optional;

import com.abnamro.assessment.recipes.repositories.entities.Recipe;
import com.abnamro.assessment.mothers.RecipesMother;
import com.abnamro.assessment.shared.references.RecipeRef;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@DataJpaTest
class RecipeRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private RecipeRepository recipeRepository;

    @Nested
    class Save {
        @Test
        void givenRecipeWithoutIngredients_whenSave_thenThrowException() {
            // Given
            Recipe toCreate = RecipesMother.roastedSardinesRecipe()
                                           .ingredients(Collections.emptyList())
                                           .build();

            // When
            Throwable thrown = catchThrowable(() -> {
                recipeRepository.save(toCreate);
                em.flush();
            });

            // Then
            assertThat(thrown).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("ingredients");

        }

        @Test
        void givenRecipeWithoutCookingInstructions_whenSave_thenThrowException() {
            // Given
            Recipe toCreate = RecipesMother.roastedSardinesRecipe()
                                           .cookingInstructions(Collections.emptyList())
                                           .build();

            // When
            Throwable thrown = catchThrowable(() -> {
                recipeRepository.save(toCreate);
                em.flush();
            });

            // Then
            assertThat(thrown).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("cookingInstructions");

        }


        @Test
        void givenValidRecipe_whenSave_thenPersistOK() {
            // Given
            Recipe toCreate = RecipesMother.roastedSardinesRecipe().build();

            // When
            Recipe created = recipeRepository.save(toCreate);
            em.flush();
            Recipe found = em.find(Recipe.class, created.getRecipeId());

            // Then
            assertThat(found).usingRecursiveComparison().ignoringFields("recipeId").isEqualTo(toCreate);

        }
    }

    @Nested
    class FindByReference {

        @Test
        void givenNonExistentRecipe_whenFind_thenReturnsEmpty() {

            // When
            Optional<Recipe> recipeReturned = recipeRepository.findRecipeByRecipeRef(RecipeRef.randomRef());

            // Then
            assertThat(recipeReturned).isEmpty();

        }

        @Test
        void givenExistentRecipe_whenFind_thenReturnsRecipe() {
            // Given
            Recipe recipe = RecipesMother.roastedSardinesRecipe().build();
            em.persist(recipe);
            em.flush();

            // When
            Optional<Recipe> recipeReturned = recipeRepository.findRecipeByRecipeRef(recipe.getRecipeRef());

            // Then
            assertThat(recipeReturned).isPresent().hasValue(recipe);

        }

    }

    @Nested
    class DeleteByReference {

        @Test
        void givenExistentRecipe_whenDelete_thenEliminateRecipe() {
            // Given
            Recipe recipe = RecipesMother.roastedSardinesRecipe().build();
            em.persist(recipe);
            em.flush();

            // When
            final Long count = recipeRepository.deleteByRecipeRef(recipe.getRecipeRef());
            em.flush();
            Recipe found = em.find(Recipe.class, recipe.getRecipeId());

            // Then
            assertThat(count).isEqualTo(1);
            assertThat(found).isNull();

        }

    }



}
