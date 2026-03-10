package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.AgendamentoDeConsultaDTO;

public interface ValidadorAgendamentoDeConsulta {

  void validar(AgendamentoDeConsultaDTO dados);
}
