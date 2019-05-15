package cl.aws.demo.jpa.security;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.aws.demo.security.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}