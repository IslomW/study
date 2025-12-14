package com.sharom.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.sharom.entity.User;
import com.sharom.exception.BadRequestException;
import com.sharom.repository.UserRepository;
import com.sharom.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(BadRequestException::userNotFound);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findByIdOptional(id)
                .orElseThrow(BadRequestException::userNotFound);
    }

    @Override
    @Transactional
    public User createUser(User user) {

        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw BadRequestException.userAlreadyExists();
                });

        user.setPasswordHash(BCrypt.withDefaults().hashToString(12, user.getPasswordHash().toCharArray()));

        userRepository.persist(user);

        return user;
    }

    @Override
    @Transactional
    public User updateUser(User user) {

        User existing = userRepository.findByIdOptional(user.getId())
                .orElseThrow(BadRequestException::userNotFound);

        // email update
        if (user.getEmail() != null &&
                !user.getEmail().equals(existing.getEmail())) {
            userRepository.findByEmail(user.getEmail()).orElseThrow(BadRequestException::userAlreadyExists);

            existing.setEmail(user.getEmail());
        }

        // password update
        if (user.getPasswordHash() != null) {
            existing.setPasswordHash(
                    BCrypt.withDefaults().hashToString(
                            12, user.getPasswordHash().toCharArray()
                    )
            );
        }

        // другие поля
        existing.setDisplayName(user.getDisplayName());

        return existing;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
