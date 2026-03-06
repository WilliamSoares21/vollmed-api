package med.voll.api.domain.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import med.voll.api.domain.endereco.Endereco;

@Entity(name = "Medico")
@Table(name = "medicos")
@EqualsAndHashCode(of = "id")
public class Medico {

  protected Medico() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Boolean ativo;

  private String nome;

  private String email;

  private String telefone;

  private String crm;

  @Enumerated(EnumType.STRING)
  private Especialidade especialidade;

  @Embedded
  private Endereco endereco;

  public Long getId() {
    return id;
  }

  public Boolean getAtivo() {
    return ativo;
  }

  public String getNome() {
    return nome;
  }

  public String getEmail() {
    return email;
  }

  public String getTelefone() {
    return telefone;
  }

  public String getCrm() {
    return crm;
  }

  public Especialidade getEspecialidade() {
    return especialidade;
  }

  public Endereco getEndereco() {
    return endereco;
  }

  public Medico(DadosCadastroMedico dados) {
    this.ativo = true;
    this.nome = dados.nome();
    this.email = dados.email();
    this.telefone = dados.telefone();
    this.crm = dados.crm();
    this.especialidade = dados.especialidade();
    this.endereco = new Endereco(dados.endereco());
  }

  public void atualizarInformacoes(@Valid DadosAtualizacaoMedico dados) {
    if (dados.endereco() != null) {
      this.endereco.atualizarInformacoes(dados.endereco());
    }
    if (dados.nome() != null) {
      this.nome = dados.nome();
    }
    if (dados.telefone() != null) {
      this.telefone = dados.telefone();
    }
  }

  public void excluir() {
    this.ativo = false;
  }
}
