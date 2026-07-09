package niccolosciucco.u5_w2_d4.authors.repositories;

import niccolosciucco.u5_w2_d4.authors.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    boolean existsByEmail(String email);
}
