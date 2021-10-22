package toppan.example.toppan.models.repo;

import org.springframework.data.repository.CrudRepository;
import toppan.example.toppan.models.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

}
