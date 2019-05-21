package cl.aws.demo.jpa.security;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.aws.demo.security.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}