package com.abnamro.assessment.shared.references;

import java.util.UUID;

public final class RecipeRef extends Reference {

    public static final String PREFIX = "REC-";

    private final String value;

    private RecipeRef(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static RecipeRef valueOf(String reference) {
        if (!reference.startsWith(PREFIX)) {
            throw new IllegalArgumentException("Ref must start with \"" + PREFIX + "\"");
        }
        return new RecipeRef(reference);
    }

    public static RecipeRef randomRef() {
        return new RecipeRef(PREFIX + UUID.randomUUID());
    }

}
