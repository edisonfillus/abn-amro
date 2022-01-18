package com.abnamro.assessment.shared.security;

import com.abnamro.assessment.users.services.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

/**
 * Class responsible to mock the Spring Security Context to be possible to use mock users on tests
 * Use @Import(SecurityTestConfig.class) in all controllers that need to test authentication
 */
@TestConfiguration
@Import(MethodSecurityConfig.class)
public class SecurityTestConfig {

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private UserService userService;

}
