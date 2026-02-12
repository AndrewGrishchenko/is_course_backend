package com.andrew.service;

import java.util.Optional;

import com.andrew.dto.user.UserCreateDTO;
import com.andrew.dto.user.UserLoginDTO;
import com.andrew.dto.user.UserLoginResponseDTO;
import com.andrew.dto.user.UserCreateResponseDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.exceptions.ValidationException;
import com.andrew.mapper.dto.UserMapper;
import com.andrew.model.User;
import com.andrew.repository.UserRepository;
import com.andrew.security.CurrentUser;
import com.andrew.util.JwtUtil;
import com.andrew.util.PasswordUtil;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    @Inject
    UserMapper userMapper;

    @Inject
    CurrentUser currentUser;

    @Transactional
    public UserCreateResponseDTO register(UserCreateDTO dto) {
        if (userRepository.findByUsername(dto.username()).isPresent())
            throw new ValidationException("User " + dto.username() + " already exists");

        User user = new User(dto.username(), dto.fullname(), dto.role(), dto.email(), PasswordUtil.hash(dto.password()));
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserLoginResponseDTO login(UserLoginDTO dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.username());

        if (userOpt.isEmpty())
            throw new NotFoundException("User with username " + dto.username() + " not found");
        if (!PasswordUtil.verify(dto.password(), userOpt.get().getPasswordHash()))
            throw new ValidationException("Invalid username or password");

        User user = userOpt.get();
        return new UserLoginResponseDTO(
            user.getId(),
            JwtUtil.generateToken(user.getUsername(), user.getId(), user.getRole().toString())
        );
    }

    @Transactional
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean authenticate(String username, String password) {
        return userRepository.findByUsername(username)
            .map(user -> PasswordUtil.verify(password, user.getPasswordHash()))
            .orElse(false);
    }
}
