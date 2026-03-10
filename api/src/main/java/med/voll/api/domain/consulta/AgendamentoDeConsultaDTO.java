package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

public record AgendamentoDeConsultaDTO(

    Long idMedico,

    @NotNull Long idPaciente,

    @NotNull @Future LocalDateTime data,

    Especialidade especialidade

) {
}
