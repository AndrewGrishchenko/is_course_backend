package com.andrew.security;

import java.util.Collections;
import java.util.Optional;

import com.andrew.model.User;
import com.andrew.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

@ApplicationScoped
public class DatabaseIdentityStore implements IdentityStore {
    @Inject
    private UserRepository userRepository;

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        Optional<User> user = userRepository.findByUsername(credential.getCaller());

        if (user.isPresent()) {
            if (user.get().getPasswordHash().equals(credential.getPasswordAsString())) {
                return new CredentialValidationResult(
                    user.get().getUsername(),
                    Collections.singleton(user.get().getRole().name())
                );
            }
        }

        return CredentialValidationResult.INVALID_RESULT;
    }
}
