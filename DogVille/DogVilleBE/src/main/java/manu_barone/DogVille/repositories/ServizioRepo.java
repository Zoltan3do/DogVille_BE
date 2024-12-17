package manu_barone.DogVille.repositories;

import manu_barone.DogVille.entities.Servizio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServizioRepo extends JpaRepository<Servizio, UUID> {
}
