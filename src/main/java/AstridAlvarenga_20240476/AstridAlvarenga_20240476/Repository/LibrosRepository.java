package AstridAlvarenga_20240476.AstridAlvarenga_20240476.Repository;

import AstridAlvarenga_20240476.AstridAlvarenga_20240476.Entities.LibrosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrosRepository extends JpaRepository<LibrosEntity, Long> { //Aquí se manda a llamar al EntityLibros
}
