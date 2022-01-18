package com.abnamro.assessment.users.controllers;

import javax.validation.Valid;

import com.abnamro.assessment.shared.security.JwtTokenService;
import com.abnamro.assessment.users.controllers.models.UserLoginAPIRequest;
import com.abnamro.assessment.users.controllers.models.UserLoginAPIResponse;
import com.abnamro.assessment.users.repositories.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login")
@RestController
@RequestMapping(LoginController.BASE_PATH)
public class LoginController {

    public static final String BASE_PATH = "/api/v1/login";

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    @Autowired
    public LoginController(
        AuthenticationManager authenticationManager,
        JwtTokenService jwtTokenService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    @Operation(
        summary = "User login",
        responses = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request params supplied", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public UserLoginAPIResponse login(@Valid UserLoginAPIRequest request) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        User user = (User) authentication.getPrincipal();
        String token = jwtTokenService.issueToken(user);
        return UserLoginAPIResponse.builder().token(token).build();
    }

}
