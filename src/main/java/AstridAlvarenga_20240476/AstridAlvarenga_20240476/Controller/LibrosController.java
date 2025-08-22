package AstridAlvarenga_20240476.AstridAlvarenga_20240476.Controller;

import AstridAlvarenga_20240476.AstridAlvarenga_20240476.Exceptions.ExceptionDuplicateData;
import AstridAlvarenga_20240476.AstridAlvarenga_20240476.Exceptions.ExceptionLibrosNotFound;
import AstridAlvarenga_20240476.AstridAlvarenga_20240476.Models.DTO.LibrosDTO;
import AstridAlvarenga_20240476.AstridAlvarenga_20240476.Service.LibrosService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController //Se indica que se comporte como Controller
@RequestMapping("/api/libros") //EndPoint inicial
public class LibrosController {
    @Autowired
    private LibrosService access;
    //Esto se hace para que el controller pueda acceder a los metodos del Service

    @GetMapping("/getLibros")
    public List<LibrosDTO>getAllLibros(){
        return access.getAllLibros();
    }

    @GetMapping("/getLibrosOne/{id}")
    public LibrosDTO getLibros(@PathVariable Long id){
        return access.getLibros(id);
    }

    //Metodo de Insert (POST)
    @PostMapping("/insertLibros")
    public ResponseEntity<Map<String, Object>>registerLibros(@Valid @RequestBody LibrosDTO librosDTO, BindingResult bindingResult, HttpServletRequest request){
        if (bindingResult.hasErrors()){
            Map<String, String> ValidationErros = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> ValidationErros.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "ERROR_DE_VALIDACIÓN",
                    "errors", ValidationErros
            ));
        }
        try {
            LibrosDTO reply = access.insertLibros(librosDTO);
            if (reply == null){
                //Se creará la respuesta que dará el código (400) de BAD REQUEST
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción incorrecta",
                        "errorType", "VALIDACION_ERROR",
                        "message", "Datos de los libros inválidos"
                ));
            }else {
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                        "status", "Success",
                        "data", reply
                ));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al registrar el libro",
                    "detail", e.getMessage()
            ));
        }
    }

    //Metodo PUT (Actualizar)
    @PutMapping("/updateLibros/{id}")
    public ResponseEntity<?> updateLibros(@PathVariable Long id, @Valid @RequestBody LibrosDTO librosDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> ValidationErros = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> ValidationErros.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "ERROR_DE_VALIDACIÓN",
                    "errors", ValidationErros
            ));
        }try {
            LibrosDTO librosUpdate = access.updateLibros(id, librosDTO);
            //Se muestra el mensaje OK (200)
            return ResponseEntity.ok(librosUpdate);
        }catch (ExceptionLibrosNotFound e){
            return ResponseEntity.notFound().build();
        }catch (ExceptionDuplicateData e){
            //Muestra el error (409)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "datos duplicados",
                    "campo", e.getDuplicateField()
            ));
        }
    }

    //Metodo del DELETE
    @DeleteMapping("/deleteLibros/{id}")
    public ResponseEntity<Map<String, Object>> deleteLibros(@PathVariable Long id){
        try {
            if (!access.removeLibros(id)){
                //Muestra el error (400)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header("X-Mensajes-error", "libro no encontrado").body(Map.of(
                        "mensaje", "libro no encontrado",
                        "timestam", Instant.now().toString()
                ));
            }
            //Muestra el mensaje de OK (200)
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "libro eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "error al eliminar el libro",
                    "detail", e.getMessage()
            ));
        }


    }
}
