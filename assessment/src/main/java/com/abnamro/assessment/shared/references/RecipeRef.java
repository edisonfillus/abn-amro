package com.abnamro.assessment.shared.references;

import java.util.UUID;
import java.util.regex.Pattern;

public final class RecipeRef extends Reference {

    private static final String PREFIX = "REC-";
    private static final Pattern REGEX_PATTERN = Pattern.compile(
        PREFIX + "[0-9A-F]{8}-[0-9A-F]{4}-[4][0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}", Pattern.CASE_INSENSITIVE
    );

    private final String value;

    private RecipeRef(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static RecipeRef valueOf(String reference) {
        // First validate size in order to avoid RegEx Denial of Service (ReDoS) attacks.
        if (reference.length() != 40 || !REGEX_PATTERN.matcher(reference).matches()) {
            throw new IllegalArgumentException("Reference is not valid");
        }
        return new RecipeRef(reference);
    }

    public static RecipeRef randomRef() {
        return new RecipeRef(PREFIX + UUID.randomUUID());
    }

}
