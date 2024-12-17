package manu_barone.DogVille.services;

import manu_barone.DogVille.entities.Cane;
import manu_barone.DogVille.entities.Utente;
import manu_barone.DogVille.exceptions.NotFoundException;
import manu_barone.DogVille.payloads.UtenteDTO;
import manu_barone.DogVille.repositories.CaneRepo;
import manu_barone.DogVille.repositories.UtenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LikeService {
    @Autowired
    private CaneService caneService;

    @Autowired
    private UtenteRepo ur;

    @Autowired
    private CaneRepo cr;


    public void addLike(UUID dogId, Utente currentUtente) {
        Cane dog = caneService.findById(dogId);

        if (!currentUtente.getLikes().contains(dog)) {
            currentUtente.getLikes().add(dog);
            dog.setLikeCount(dog.getLike_count() + 1);
            ur.save(currentUtente);
            cr.save(dog);
        }
    }

    public void removeLike(UUID dogId, Utente currentUtente) {


        currentUtente = ur.findById(currentUtente.getId()).orElseThrow(() -> new NotFoundException("Utente non trovato"));
        Cane dog = caneService.findById(dogId);

        if (currentUtente.getLikes().contains(dog)) {
            currentUtente.getLikes().remove(dog);
            dog.getLikes().remove(currentUtente);
            dog.setLikeCount(dog.getLike_count() - 1);
            ur.save(currentUtente);
            cr.save(dog);
        } else {
            throw new IllegalStateException("Il cane non è tra i like dell'utente");
        }

    }

}

