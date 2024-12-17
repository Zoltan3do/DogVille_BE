package manu_barone.DogVille.repositories;

import manu_barone.DogVille.entities.Cane;
import manu_barone.DogVille.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CaneRepo extends JpaRepository<Cane, UUID> , JpaSpecificationExecutor<Cane> {
    List<Cane> findAllByOrderByLikeCountDesc();
}
