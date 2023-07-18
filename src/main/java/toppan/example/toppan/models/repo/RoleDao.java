package toppan.example.toppan.models.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import toppan.example.toppan.models.Role;

public interface RoleDao extends JpaRepository<Role, Long> {
}
