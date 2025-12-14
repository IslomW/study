package com.sharom.service;

import com.sharom.entity.User;
import org.jose4j.jwk.Use;

public interface UserService {

    User getUserByEmail(String email);

    User getUserById(Long id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);




}
