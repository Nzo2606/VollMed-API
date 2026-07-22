package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorTreater

/// classe responsável por tratar exceções do programa
{

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity treatError404(){ // 404 = acionado quando tal entidade não for achada no banco
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity treatError400(MethodArgumentNotValidException ex){ // 400 = acionado quando alguma propriedade da entidade foi inserida de forma incorreta (ou nem foi)
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ValidationErrorData::new).toList());
    }

    private record ValidationErrorData(String field, String message){ //pega somente os campos `field` e `message` do trace de erro
        public ValidationErrorData (FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }

    }
}


