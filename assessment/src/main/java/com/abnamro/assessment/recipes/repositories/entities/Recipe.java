package com.abnamro.assessment.recipes.repositories.entities;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import com.abnamro.assessment.recipes.repositories.entities.converters.RecipeRefConverter;
import com.abnamro.assessment.shared.references.RecipeRef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RECIPE")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public final class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECIPE_ID")
    private Long recipeId;

    @NotNull
    @Column(name = "RECIPE_REF")
    @Convert(converter = RecipeRefConverter.class)
    private RecipeRef recipeRef;

    @Size(max = 100)
    @NotEmpty
    @Column(name = "RECIPE_NAME")
    private String name;

    @NotNull
    @Column(name = "IS_VEGETARIAN")
    private Boolean isVegetarian;

    @NotNull
    @Positive
    @Column(name = "SUITABLE_FOR")
    private Integer suitableFor;

    @NotNull
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdDate;

    @NotEmpty
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "INGREDIENT", joinColumns = @JoinColumn(name = "RECIPE_ID"))
    @OrderColumn(name = "INGREDIENT_ORDER")
    @Column(name = "INGREDIENT_DESCRIPTION", nullable = false)
    private List<String> ingredients;


    @NotEmpty
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "COOKING_INSTRUCTION", joinColumns = @JoinColumn(name = "RECIPE_ID"))
    @OrderColumn(name = "COOKING_INSTRUCTION_ORDER")
    @Column(name = "COOKING_INSTRUCTION_DESCRIPTION", nullable = false)
    private List<String> cookingInstructions;

}
