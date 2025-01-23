package manu_barone.DogVille.controllers;

import jakarta.persistence.EntityNotFoundException;
import manu_barone.DogVille.entities.Adozione;
import manu_barone.DogVille.entities.Utente;
import manu_barone.DogVille.entities.enums.StatoAdozione;
import manu_barone.DogVille.exceptions.BadRequestException;
import manu_barone.DogVille.payloads.AdoptionDTO;
import manu_barone.DogVille.payloads.validationGroups.Create;
import manu_barone.DogVille.services.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adozioni")
public class AdoptionController {

    @Autowired
    private AdoptionService as;

    @GetMapping("/{adozioneId}")
    public Adozione findAdozioneById(@PathVariable UUID adozioneId) {
        return as.findById(adozioneId);
    }

    @GetMapping("/{id}/state")
    public StatoAdozione getAdoptionState(@PathVariable UUID id) {
        return as.getAdoptionStateById(id);
    }

    @GetMapping("/user")
    public Page<Adozione> getAdoptionByUser(@RequestParam String email, Pageable pageable) {
        return as.findAdoptionByUser(email, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Adozione createAdozione(@RequestBody @Validated(Create.class) AdoptionDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Payload error: " + message);
        }
        return as.createAdoptionRequest(body);
    }

    @PatchMapping("/{adozioneId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public Adozione updateAdoption(@PathVariable UUID adozioneId, @RequestParam String statoAdozione) {
        return as.updateAdoptionState(adozioneId, statoAdozione);
    }

    @DeleteMapping("/{adozioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCane(@PathVariable UUID adozioneId) {
        as.deleteCane(adozioneId);
    }

    @PatchMapping("/{adoptionId}/document")
    public String addDocument(@PathVariable("adoptionId") UUID adoptionId, @RequestParam("document") MultipartFile file, @AuthenticationPrincipal Utente currentUtente) {
        return as.uploadDocument(file, adoptionId, currentUtente);
    }

    @PatchMapping("/{adoptionId}/sign")
    public String addSign(@PathVariable("adoptionId") UUID adoptionId, @RequestParam("sign") MultipartFile file, @AuthenticationPrincipal Utente currentUtente) {
        return as.uploadSign(file, adoptionId, currentUtente);
    }

    @PatchMapping("/{adoptionId}/setVisit/{datavisita}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addVisitDate(
            @PathVariable UUID adoptionId,
            @PathVariable String datavisita,
            @AuthenticationPrincipal Utente currentUtente) {
        try {
            LocalDate visitDate = LocalDate.parse(datavisita);
            LocalDate result = as.addVisitDate(visitDate, adoptionId, currentUtente);
            return ResponseEntity.ok(result);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Formato della data non valido. Utilizzare il formato YYYY-MM-DD.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adozione non trovata con l'ID fornito.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'aggiornamento della data della visita.");
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Adozione>> findAll() {
        List<Adozione> adoptions = as.findAll();
        return ResponseEntity.ok(adoptions);
    }


}
