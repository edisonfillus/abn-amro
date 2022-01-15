package com.abnamro.assessment.recipes.repositories;

import javax.validation.ConstraintViolationException;

import java.util.Collections;

import com.abnamro.assessment.recipes.repositories.entities.Recipe;
import com.abnamro.assessment.mothers.RecipesMother;
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
