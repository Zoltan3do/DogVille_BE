package manu_barone.DogVille.controllers;

import manu_barone.DogVille.entities.Utente;
import manu_barone.DogVille.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/likes")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/dogs/{dogId}")
    public ResponseEntity<String> addLike(
            @PathVariable UUID dogId,
            @AuthenticationPrincipal Utente currentUtente) {
        likeService.addLike(dogId, currentUtente);
        return ResponseEntity.ok("Like aggiunto con successo.");
    }

    @DeleteMapping("/dogs/{dogId}")
    public ResponseEntity<String> removeLike(
            @PathVariable UUID dogId,
            @AuthenticationPrincipal Utente currentUtente) {
        likeService.removeLike(dogId, currentUtente);
        return ResponseEntity.ok("Like rimosso con successo.");
    }
}

