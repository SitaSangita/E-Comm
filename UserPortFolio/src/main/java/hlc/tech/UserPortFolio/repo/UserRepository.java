package hlc.tech.UserPortFolio.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hlc.tech.UserPortFolio.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}