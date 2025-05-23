package hcl.tech.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hcl.tech.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
