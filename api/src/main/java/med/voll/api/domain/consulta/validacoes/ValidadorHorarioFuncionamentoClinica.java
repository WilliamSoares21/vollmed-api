package med.voll.api.domain.consulta.validacoes;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.AgendamentoDeConsultaDTO;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

  public void validar(AgendamentoDeConsultaDTO dados) {
    var dataConsulta = dados.data();

    var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);

    var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;

    var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;

    if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {
      throw new ValidationException("Consulta fora do horário de funcionamento da clínica.");
    }
  }
}
