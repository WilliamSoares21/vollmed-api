package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.AgendamentoDeConsultaDTO;
import med.voll.api.domain.consulta.ConsultaRepository;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

  @Autowired
  private ConsultaRepository repository;

  public void validar(AgendamentoDeConsultaDTO dados) {
    var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());

    if (medicoPossuiOutraConsultaNoMesmoHorario) {
      throw new ValidationException("Médico já possui outra consulta agendada nesse mesmo horário");
    }

  }
}
