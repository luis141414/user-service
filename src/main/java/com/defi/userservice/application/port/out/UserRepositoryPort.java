package com.defi.userservice.application.port.out;

import com.defi.userservice.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User save(User user);
    List<User> findAll();
}