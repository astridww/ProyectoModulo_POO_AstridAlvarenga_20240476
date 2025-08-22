package AstridAlvarenga_20240476.AstridAlvarenga_20240476.Models.DTO;

import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode
public class LibrosDTO {

    private Long id;

    @Size(max = 200)
    private String titulo;

    @Size(max = 20)
    private String isbn;

    @Max(value = 4) //Para que solo acepte 4 caracteres, por ejemplo años como 2025, 2024, etc
    @Min(value = 4)
    private Long anioPublicacion;

    @NotBlank
    private String genero;

    private Long autorId;
}
