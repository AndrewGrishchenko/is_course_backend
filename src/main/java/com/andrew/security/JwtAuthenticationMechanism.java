package com.andrew.security;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.andrew.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;

// @ApplicationScoped
public class JwtAuthenticationMechanism {//implements HttpAuthenticationMechanism {
    // @Override
    // public AuthenticationStatus validateRequest(
    //         HttpServletRequest request,
    //         HttpServletResponse response,
    //         HttpMessageContext context) {
    //     String path = request.getRequestURI();

    //     System.err.println("PATH: " + path);

    //     if (path.equals("/api/auth/login")) {
    //         return context.doNothing();
    //     }

    //     String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    //     if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    //         return context.responseUnauthorized();
    //     }

    //     String token = authHeader.substring("Bearer ".length());

    //     Claims claims;
    //     try {
    //         claims = JwtUtil.validateToken(token);
    //     } catch (JwtException e) {
    //         return context.responseUnauthorized();
    //     }

    //     String username = claims.getSubject();
    //     String role = claims.get("role", String.class);

    //     System.err.println("ROLE: " + role);

    //     if (username == null || role == null) {
    //         return context.responseUnauthorized();
    //     }

    //     Principal callerPrincipal = new Principal() {
    //         @Override
    //         public String getName() {
    //             return username;
    //         }
    //     };

    //     Set<String> roles = Collections.singleton(role);

    //     return context.notifyContainerAboutLogin(callerPrincipal, roles);
    // }
}
