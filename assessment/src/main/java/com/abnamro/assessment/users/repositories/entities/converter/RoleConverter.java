package com.abnamro.assessment.users.repositories.entities.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.abnamro.assessment.users.repositories.entities.Role;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return role.getId();
    }

    @Override
    public Role convertToEntityAttribute(Integer dbData) {
        return Role.getById(dbData);
    }
}
