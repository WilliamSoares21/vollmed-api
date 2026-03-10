package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.AgendamentoDeConsultaDTO;
import med.voll.api.repository.PacienteRepository;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

  @Autowired
  private PacienteRepository repository;

  public void validar(AgendamentoDeConsultaDTO dados) {
    var pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());
    if (!pacienteEstaAtivo) {
      throw new ValidationException("Consulta não pode ser agendada por que o paciente não está ativo");

    }
  }
}
