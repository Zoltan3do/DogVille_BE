package manu_barone.DogVille.repositories;

import manu_barone.DogVille.entities.ProfiloPsicologico;
import manu_barone.DogVille.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfiloPsicologicoRepo extends JpaRepository<ProfiloPsicologico, UUID> {
    Optional<ProfiloPsicologico> findFirstByType(String type);
}
