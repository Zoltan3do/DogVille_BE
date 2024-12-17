package manu_barone.DogVille.payloads;

import java.util.List;

public record CompatibilitaProfiliDTO(
        String ownerPersonality,
        String description,
        List<String> compatiblePersonalities
) {
}
