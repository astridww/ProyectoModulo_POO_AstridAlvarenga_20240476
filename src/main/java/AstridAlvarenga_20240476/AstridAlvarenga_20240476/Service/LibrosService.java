package AstridAlvarenga_20240476.AstridAlvarenga_20240476.Service;

import AstridAlvarenga_20240476.AstridAlvarenga_20240476.Entities.LibrosEntity;
import AstridAlvarenga_20240476.AstridAlvarenga_20240476.Exceptions.ExceptionLibrosNotFound;
import AstridAlvarenga_20240476.AstridAlvarenga_20240476.Exceptions.ExceptionLibrosUnregistered;
import AstridAlvarenga_20240476.AstridAlvarenga_20240476.Models.DTO.LibrosDTO;
import AstridAlvarenga_20240476.AstridAlvarenga_20240476.Repository.LibrosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LibrosService {

    @Autowired
    private LibrosRepository repo;
    //Esto funciona para que podamos acceder a los métodos del LibrosRepository, practicamente inyectamos el repository al service

    //Metodo para obtener todos los libros
    public List<LibrosDTO>getAllLibros(){
        List<LibrosEntity>librosEntities = repo.findAll();
        return librosEntities.stream().map(this::convertToDTOLibros).collect(Collectors.toList());
    }

    public LibrosDTO getLibros(Long id){
        LibrosEntity librosEntity = repo.findById(id).
                orElseThrow(()-> new ExceptionLibrosNotFound("No se encontró el ID"));
        return convertToDTOLibros(librosEntity);
    }

    //Metodo para insertar libros (POST)
    public LibrosDTO insertLibros(LibrosDTO objLibrosDTO){
        if (objLibrosDTO == null){
            throw new IllegalArgumentException("Ningún campo puede estar vacío");
        }
        try {
            //1. Pasaremos los datos que vienen del frontend a datos DTO
            LibrosEntity librosEntity = convertToEntityLibros(objLibrosDTO);
            //Como ahora se tienen los valores ahora se van a guardar en la base de datos
            LibrosEntity librosSave = repo.save(librosEntity);
            //Ahora se guardan en la base de datos pero se tienen que retornar nuevamente como datos DTO
            return convertToDTOLibros(librosSave);
        }catch (Exception e){
            log.error("Error al ingresar un libro");
            throw new ExceptionLibrosUnregistered("Error al ingresar el libro");
        }
    }

    //Metodo para actualizar los libros (PUT)
    public LibrosDTO updateLibros(Long id, LibrosDTO librosDTO){
        //Se verificará si existe el ID
        LibrosEntity librosExisting = repo.findById(id).orElseThrow(()-> new ExceptionLibrosNotFound("ID no encontrado"));
        //Ahora se actualizan los campos
        librosExisting.setTitulo(librosDTO.getTitulo());
        librosExisting.setIsbn(librosDTO.getIsbn());
        librosExisting.setAnioPublicacion(librosDTO.getAnioPublicacion());
        librosExisting.setGenero(librosDTO.getGenero());
        librosExisting.setAutorId(librosDTO.getAutorId());
        //Ahora se actualizan los campos actualizados y se guardan en la base de datos
        LibrosEntity librosUpdate = repo.save(librosExisting);
        //Se convierten a DTO para mostrarlo en el frontend
        return convertToDTOLibros(librosUpdate);
    }

    //Metodo para eliminar a un libro (DELETE)
    public Boolean removeLibros(Long id){
        try{
            LibrosEntity librosExisting = repo.findById(id).orElse(null);
            if (librosExisting != null){
                repo.delete(librosExisting);
                return true;
            }else {
                System.out.println("No se encontró el ID a eliminar");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontró el ID", 1);
        }
    }

    private LibrosDTO convertToDTOLibros(LibrosEntity objLibrosEntity){
        //Acá accedemos a la clase LibrosDTO para poder asignarles valor a los atributos
        LibrosDTO objLibrosDTO = new LibrosDTO();

        objLibrosDTO.setId(objLibrosEntity.getId());
        objLibrosDTO.setTitulo(objLibrosEntity.getTitulo());
        objLibrosDTO.setIsbn(objLibrosEntity.getIsbn());
        objLibrosDTO.setAnioPublicacion(objLibrosEntity.getAnioPublicacion());
        objLibrosDTO.setGenero(objLibrosEntity.getGenero());
        objLibrosDTO.setAutorId(objLibrosEntity.getAutorId());
        return objLibrosDTO;
    }

    private LibrosEntity convertToEntityLibros(LibrosDTO objLibrosDTO){
        //Accedemos a la clase LibrosEntity para poder asignar los valores que vienen del frontend
        LibrosEntity objLibrosEntity = new LibrosEntity();

        objLibrosEntity.setTitulo(objLibrosDTO.getTitulo());
        objLibrosEntity.setIsbn(objLibrosDTO.getIsbn());
        objLibrosEntity.setAnioPublicacion(objLibrosDTO.getAnioPublicacion());
        objLibrosEntity.setGenero(objLibrosDTO.getGenero());
        objLibrosEntity.setAutorId(objLibrosDTO.getAutorId());
        return objLibrosEntity;
    }

}
