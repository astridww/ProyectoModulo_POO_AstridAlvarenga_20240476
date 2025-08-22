package AstridAlvarenga_20240476.AstridAlvarenga_20240476.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter @Setter @ToString @EqualsAndHashCode
@Table(name = "LIBROS")
public class LibrosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AUTORES")
    @SequenceGenerator(name = "SEQ_AUTORES", sequenceName = "SEQ_AUTORES", allocationSize = 1)

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "ANIOPUBLICACION")
    private Long anioPublicacion;

    @Column(name = "GENERO")
    private String genero;

    @Column(name = "AUTORID")
    private Date autorId;
}
