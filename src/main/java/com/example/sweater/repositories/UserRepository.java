package com.example.sweater.repositories;

import com.example.sweater.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String userName);

    Optional<User> findByActivationCode(String code);
}
