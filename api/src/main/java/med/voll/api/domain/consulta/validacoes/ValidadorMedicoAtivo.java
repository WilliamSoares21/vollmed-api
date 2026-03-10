package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.AgendamentoDeConsultaDTO;
import med.voll.api.repository.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

  @Autowired
  private MedicoRepository repository;

  public void validar(AgendamentoDeConsultaDTO dados) {

    if (dados.idMedico() == null) {
      return;
    }

    var medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
    if (!medicoEstaAtivo) {
      throw new ValidationException("Consulta não pode ser agendada com médico excluído");
    }
  }
}
