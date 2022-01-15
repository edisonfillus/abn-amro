package com.abnamro.assessment.recipes.repositories.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.abnamro.assessment.shared.references.RecipeRef;

@Converter
public class RecipeRefConverter implements AttributeConverter<RecipeRef, String> {

    @Override
    public String convertToDatabaseColumn(RecipeRef attribute) {
        return attribute.getValue();
    }

    @Override
    public RecipeRef convertToEntityAttribute(String dbData) {
        return RecipeRef.valueOf(dbData);
    }
}
