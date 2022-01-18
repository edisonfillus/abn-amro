package com.abnamro.assessment.users.controllers.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value @Builder @Jacksonized
public class UserLoginAPIRequest {

    @NotEmpty
    @Size(max = 100)
    String userName;

    @NotEmpty
    @Size(max = 100)
    String password;

}
