package manu_barone.DogVille.payloads;

import jakarta.validation.constraints.*;
import manu_barone.DogVille.entities.enums.StatoSalute;
import manu_barone.DogVille.entities.enums.Taglia;
import manu_barone.DogVille.payloads.validationGroups.Create;

public record CaneDTO(
        @NotBlank(message = "Il nome del cane non può essere vuoto.", groups = Create.class)
        @Size(max = 50, message = "Il nome del cane non può superare i 50 caratteri.")
        String name,

        @Min(value = 0, message = "L'età del cane non può essere negativa.", groups = Create.class)
        @Max(value = 30, message = "L'età del cane non può superare i 30 anni.",groups = Create.class)
        Integer age,

        @NotNull(message = "La taglia del cane è obbligatoria.",groups = Create.class)
        Taglia dogSize,

        @NotBlank(message = "La razza del cane non può essere vuota.",groups = Create.class)
        @Size(max = 50, message = "La razza del cane non può superare i 50 caratteri.")
        String race,

        @NotNull(message = "Lo stato di salute del cane è obbligatorio.",groups = Create.class)
        StatoSalute healthState,

        @Pattern(regexp = "[MF]", message = "Il genere del cane deve essere 'M' (Maschio) o 'F' (Femmina).")
        String gender,

        @Size(max = 255, message = "La descrizione del cane non può superare i 255 caratteri.")
        String description,

        boolean weaned,

        boolean adopted,

        @Pattern(regexp = "\\b(yes|no)\\b")
        String adoptedCheck,

        @Pattern(regexp = "\\b(yes|no)\\b")
        String weanedCheck

) {
}
