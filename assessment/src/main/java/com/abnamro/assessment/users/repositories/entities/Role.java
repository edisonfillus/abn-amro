package com.abnamro.assessment.users.repositories.entities;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter @AllArgsConstructor
public enum Role implements GrantedAuthority {

    VIEWER(1, Role.ROLE_VIEWER, "Viewer"),
    EDITOR(2, Role.ROLE_EDITOR, "Editor");

    public static final String ROLE_VIEWER = "VIEWER";
    public static final String ROLE_EDITOR = "EDITOR";

    @JsonValue
    private final Integer id;

    private final String name;

    private final String description;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Role getById(final Integer id) {
        return Arrays.stream(Role.values())
                     .filter(o -> o.id.equals(id))
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String getAuthority() {
        return name;
    }


}
