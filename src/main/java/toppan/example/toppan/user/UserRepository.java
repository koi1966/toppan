package toppan.example.toppan.user;

import org.springframework.data.repository.CrudRepository;
import toppan.example.toppan.user.UserEntity;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);
}
