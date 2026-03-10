package med.voll.api.infra.exceptions;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity tratarErro404() {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(InvalidDataAccessApiUsageException.class)
  public ResponseEntity<String> tratarErroIdNulo(InvalidDataAccessApiUsageException ex) {
    return ResponseEntity.badRequest().body("O id informado não pode ser nulo. Verifique os campos enviados.");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
    var erros = ex.getFieldErrors();
    return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
  }

  private record DadosErroValidacao(String campo, String mensagem) {
    public DadosErroValidacao(FieldError e) {
      this(e.getField(), e.getDefaultMessage());
    }
  }
}
