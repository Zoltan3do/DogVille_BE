package manu_barone.DogVille.repositories;

import manu_barone.DogVille.entities.Pagamento;
import manu_barone.DogVille.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PagamentoRepo extends JpaRepository<Pagamento, UUID> {
}
