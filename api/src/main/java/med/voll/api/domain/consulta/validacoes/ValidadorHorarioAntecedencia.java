package med.voll.api.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.AgendamentoDeConsultaDTO;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {

  public void validar(AgendamentoDeConsultaDTO dados) {
    var dataConsulta = dados.data();

    var agora = LocalDateTime.now();

    var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

    if (diferencaEmMinutos < 30) {
      throw new ValidationException("A consulta deve ser agendada com antecedencia mínima de 30 minutos");
    }

  }

}
