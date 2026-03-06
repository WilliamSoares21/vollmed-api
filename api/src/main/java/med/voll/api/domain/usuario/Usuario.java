package med.voll.api.domain.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@EqualsAndHashCode(of = "id")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String login;
  private String senha;

  public Usuario() {
  }

}
