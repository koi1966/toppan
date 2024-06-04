package toppan.example.toppan.models.repo;

import org.springframework.data.repository.CrudRepository;
import toppan.example.toppan.models.ApiKeyEntity;

import java.util.Optional;

public interface ApiKeyRepository extends CrudRepository<ApiKeyEntity, String> {

    Optional<ApiKeyEntity> findByApiKey(String apiKey);
}
