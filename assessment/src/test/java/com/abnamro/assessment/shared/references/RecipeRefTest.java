package com.abnamro.assessment.shared.references;

import com.abnamro.assessment.recipes.services.exceptions.RecipeNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class RecipeRefTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "XXX-3ab24d9e-bce3-40fd-bee8-f23220a78982",
        "REC-3ab24d9e-bce3-40fd-bee8-f23220a789827"
    })
    void givenInvalidReferences_whenInstantiating_thenThrow(String value) {

        // When
        Throwable thrown = catchThrowable(() -> RecipeRef.valueOf(value));

        // Then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);

    }

    @ParameterizedTest
    @ValueSource(strings = {
        "REC-3ab24d9e-bce3-40fd-bee8-f23220a78982",
        "REC-c396e172-8ce0-4b6d-ac80-8212e25d7453",
        "REC-5628EEDC-525A-4879-9D3F-6B21AD590FCD"
    })
    void givenValidReferences_whenInstantiating_thenReturnInstance(String value) {

        // When
        RecipeRef result = RecipeRef.valueOf(value);

        // Then
        assertThat(result.getValue()).isEqualTo(value);

    }

}
