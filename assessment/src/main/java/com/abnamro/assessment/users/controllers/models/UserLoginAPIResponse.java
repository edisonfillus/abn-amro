package com.abnamro.assessment.users.controllers.models;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value @Builder @Jacksonized
public class UserLoginAPIResponse {

    String token;

}
