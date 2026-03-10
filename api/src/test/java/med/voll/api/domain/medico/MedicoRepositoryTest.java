package med.voll.api.domain.medico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.temporal.TemporalAdjusters;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.repository.MedicoRepository;

// @DataJpaTest: sobe apenas o contexto de persistência (JPA/Hibernate + banco).
// Muito mais rápido que @SpringBootTest, pois não carrega controllers, services, etc.
@DataJpaTest
// Replace.NONE: usa o banco real configurado em application-test.properties
// em vez de banco em memória (H2). Importante para testar comportamentos MySQL específicos.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// Ativa o profile "test", fazendo o Spring carregar application-test.properties
@ActiveProfiles("test")
public class MedicoRepositoryTest {

  @Autowired
  private MedicoRepository medicoRepository;

  // TestEntityManager: substitui o EntityManager nos testes.
  // Permite persistir objetos diretamente no banco sem passar pela camada de serviço.
  @Autowired
  private TestEntityManager em;

  @Test
  @DisplayName("Deveria devolver null quando único médico cadastrado não está disponível na data")
  void escolherMedicoAleatorioLivreNaData_medicoOcupado() {
    // Calcula a próxima segunda-feira às 10h a partir de hoje
    var proximaSegundaAs10 = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY)) // TemporalAdjusters (classe utilitária)
        .atTime(10, 0);

    // Cenário: único médico cadastrado está com consulta marcada nessa data
    var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
    var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "000.000.000-00"); // CPF no formato correto
    cadastrarConsulta(medico, paciente, proximaSegundaAs10);

    // Executa a query do repositório
    var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

    // Como o médico está ocupado, espera-se retornar null
    assertThat(medicoLivre).isNull();
  }

  @Test
  @DisplayName("Deveria devolver médico quando estiver disponível na data")
  // Nome de método diferente do anterior — Java não permite dois métodos com o mesmo nome na mesma classe
  void escolherMedicoAleatorioLivreNaData_medicoDisponivel() {
    var proximaSegundaAs10 = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        .atTime(10, 0);

    // Cenário: médico cadastrado SEM consulta na data — deve ser retornado
    var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

    // Nenhuma consulta é agendada, logo o médico está livre
    var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

    // Verifica que o médico retornado é exatamente o que foi cadastrado
    assertThat(medicoLivre).isEqualTo(medico);
  }

  // --- Métodos auxiliares (helpers) ---
  // Ficam privados: são detalhes de implementação do teste, não devem ser expostos.

  private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
    em.persist(new Consulta(null, medico, paciente, data));
  }

  private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
    var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
    em.persist(medico);
    return medico;
  }

  private Paciente cadastrarPaciente(String nome, String email, String cpf) {
    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
    em.persist(paciente);
    return paciente;
  }

  private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
    return new DadosCadastroMedico(
        nome,
        email,
        "61999999999",
        crm,
        especialidade,
        dadosEndereco());
  }

  private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
    return new DadosCadastroPaciente(
        nome,
        email,
        "61999999999",
        cpf,
        dadosEndereco());
  }

  // Endereço genérico reutilizado em todos os testes
  private DadosEndereco dadosEndereco() {
    return new DadosEndereco(
        "rua xpto",
        "bairro",
        "00000000",
        "Brasilia",
        "DF",
        null,
        null);
  }
}
