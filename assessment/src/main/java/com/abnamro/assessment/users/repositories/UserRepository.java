package com.abnamro.assessment.users.repositories;

import java.util.Optional;

import com.abnamro.assessment.users.repositories.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<User> findUserByUserName(String userName);

}
