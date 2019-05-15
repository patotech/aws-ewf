package cl.aws.demo.security;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}