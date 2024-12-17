package manu_barone.DogVille.controllers;

import manu_barone.DogVille.entities.ProfiloPsicologico;
import manu_barone.DogVille.entities.Utente;
import manu_barone.DogVille.exceptions.BadRequestException;
import manu_barone.DogVille.payloads.ProfiloPsicologicoDTO;
import manu_barone.DogVille.payloads.UtenteDTO;
import manu_barone.DogVille.payloads.validationGroups.Update;
import manu_barone.DogVille.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentUtente) {
        return currentUtente;
    }

    @PutMapping("/me")
    public Utente updateProfile(@AuthenticationPrincipal Utente currentUtente, @RequestBody @Validated(Update.class) UtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return utenteService.updateUtente(currentUtente.getId(), body);
    }

    @PatchMapping("/{id}/switchRole")
    public ResponseEntity<Utente> switchRole(@PathVariable UUID id) {
        Utente updatedUser = utenteService.switchRole(id);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<Utente>> findAll() {
        List<Utente> utenti = utenteService.findAll();
        return ResponseEntity.ok(utenti);
    }

    @DeleteMapping("/{utenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUtente(@PathVariable UUID utenteId) {
        utenteService.deleteUtente(utenteId);
    }


    @PatchMapping("/avatar")
    public String addAvatar(@AuthenticationPrincipal Utente utente, @RequestParam("avatar") MultipartFile file) {
        return utenteService.uploadPhoto(file, utente.getId());
    }

    @GetMapping("/{userId}/profiles")
    public ResponseEntity<List<ProfiloPsicologico>> getUserProfiles(@PathVariable UUID userId) {
        return ResponseEntity.ok(utenteService.getUserProfiles(userId));
    }

    @PostMapping("/{userId}/profiles/{profileType}")
    public ResponseEntity<ProfiloPsicologico> addProfileToUser(
            @PathVariable UUID userId,
            @PathVariable String profileType
    ) {
        ProfiloPsicologico profilo = utenteService.addProfileToUser(userId, profileType);
        return ResponseEntity.ok(profilo);
    }

    @DeleteMapping("/{userId}/profiles/{profileType}")
    public ResponseEntity<Void> removeProfileFromUser(
            @PathVariable UUID userId,
            @PathVariable String profileType
    ) {
        utenteService.removeProfileFromUser(userId, profileType);
        return ResponseEntity.noContent().build();
    }


}