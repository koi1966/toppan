package toppan.example.toppan.models.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import toppan.example.toppan.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email);
}
