package manu_barone.DogVille.repositories;

import manu_barone.DogVille.entities.Ordine;
import manu_barone.DogVille.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrdineRepo extends JpaRepository<Ordine, UUID> {
}
