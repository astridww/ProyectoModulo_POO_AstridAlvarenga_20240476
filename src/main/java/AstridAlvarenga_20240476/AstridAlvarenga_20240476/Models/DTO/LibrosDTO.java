package AstridAlvarenga_20240476.AstridAlvarenga_20240476.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    private Long anioPublicacion;

    private
}
