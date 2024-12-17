package manu_barone.DogVille.controllers;

import manu_barone.DogVille.entities.ProfiloPsicologico;
import manu_barone.DogVille.payloads.ProfiloPsicologicoDTO;
import manu_barone.DogVille.services.ProfiloPsicologicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/psycologicalProfiles")
public class ProfiloPsicologicoController {
    @Autowired
    private ProfiloPsicologicoService profiloPsicologicoService;

    @PostMapping("/save")
    public ResponseEntity<ProfiloPsicologico> saveProfiloPsicologico(@RequestBody ProfiloPsicologico profiloPsicologico) {
        ProfiloPsicologico savedProfile = profiloPsicologicoService.saveProfiloPsicologico(profiloPsicologico);
        return ResponseEntity.ok(savedProfile);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProfiloPsicologicoDTO>> getAllProfiliPsicologici() {
        List<ProfiloPsicologicoDTO> profiles = profiloPsicologicoService.getAllProfilesAsDTO();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfiloPsicologico> getProfiloPsicologicoById(@PathVariable UUID id) {
        ProfiloPsicologico profile = profiloPsicologicoService.getProfiloPsicologicoById(id);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/byType/{type}")
    public ResponseEntity<ProfiloPsicologicoDTO> getProfiloPsicologicoByType(@PathVariable String type){
        ProfiloPsicologicoDTO profile = profiloPsicologicoService.toDTO(profiloPsicologicoService.getProfiloPsicologicoByType(type));
        return ResponseEntity.ok(profile);
    }



}
