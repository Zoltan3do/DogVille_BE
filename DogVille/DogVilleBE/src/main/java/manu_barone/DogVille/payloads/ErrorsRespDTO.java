package manu_barone.DogVille.payloads;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ErrorsRespDTO(String message, LocalDateTime data) {
}
