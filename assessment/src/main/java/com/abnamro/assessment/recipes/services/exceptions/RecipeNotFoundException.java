package com.abnamro.assessment.recipes.services.exceptions;

import java.util.NoSuchElementException;

import com.abnamro.assessment.shared.references.RecipeRef;

public class RecipeNotFoundException extends NoSuchElementException {

    public RecipeNotFoundException(RecipeRef reference) {
        super("No recipe found with reference " + reference.getValue());
    }

}
