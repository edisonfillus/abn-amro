package com.abnamro.assessment.shared.security;

import lombok.Builder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class JwtPreAuthenticationToken extends PreAuthenticatedAuthenticationToken {

    @Builder
    public JwtPreAuthenticationToken(UserDetails principal, WebAuthenticationDetails details) {
        super(principal, null, principal.getAuthorities());
        super.setDetails(details);
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
