package com.oceanview.repository;

import com.oceanview.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository Pattern: Spring Data generates all MongoDB queries automatically.
 * MongoRepository<User, String> means:
 *   - User  = the document type
 *   - String = the type of the @Id field
 *
 * Spring auto-implements: save(), findById(), findAll(), delete() etc.
 * We declare custom queries as method names — Spring translates them to MongoDB queries.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Spring reads this method name and builds: db.users.findOne({username: value})
    Optional<User> findByUsername(String username);

    // Checks if a username already exists
    boolean existsByUsername(String username);
}
