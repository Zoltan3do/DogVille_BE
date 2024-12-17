package manu_barone.DogVille.repositories;

import manu_barone.DogVille.entities.Adozione;
import manu_barone.DogVille.entities.Cane;
import manu_barone.DogVille.entities.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface AdozioneRepo extends JpaRepository<Adozione, UUID> {
    boolean existsByDog(Cane dog);
    Page<Adozione> findByUserAdoptions(Utente user, Pageable pageable);

}
