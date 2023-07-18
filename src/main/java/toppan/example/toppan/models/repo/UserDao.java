package toppan.example.toppan.models.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import toppan.example.toppan.models.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
