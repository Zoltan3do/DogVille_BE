package manu_barone.DogVille.payloads;

import java.util.List;

public record ProfiloPsicologicoDTO(
        String type,
        String descrizione,
        List<String> compatibleProfiles
) {
}
