package com.github.gustavoflor.bootelemetry.repository;

import com.github.gustavoflor.bootelemetry.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByGithubId(Long githubId);

    Optional<User> findByGithubId(Long githubId);
}
