package niccolosciucco.u5_w2_d4.blogs.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record BlogDTO(
        @NotBlank(message = "La categoria è obbligatoria")
        @Size(min = 2, max = 50, message = "La categoria deve essere compresa tra 2 e 50 caratteri")
        String category,

        @NotBlank(message = "Il titolo è obbligatorio")
        @Size(min = 2, max = 100, message = "Il titolo deve essere compreso tra 2 e 100 caratteri")
        String title,

        @NotBlank(message = "La trama è obbligatoria")
        @Size(min = 10, message = "La trama deve contenere almeno 10 caratteri")
        String plot,

        @NotNull(message = "Il tempo di lettura è obbligatorio")
        @Min(value = 1, message = "Il tempo di lettura deve essere di almeno 1 minuto")
        int readingTime,

        @NotNull(message = "L'ID dell'autore è obbligatorio")
        UUID authorId
) {
}