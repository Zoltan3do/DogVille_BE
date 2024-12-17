package manu_barone.DogVille.repositories;

import manu_barone.DogVille.entities.OggettoOrdine;
import manu_barone.DogVille.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface OggettoOrdineRepo extends JpaRepository<OggettoOrdine, UUID> {
}
