package com.example.sweater.repositories;

import com.example.sweater.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String userName);
}
