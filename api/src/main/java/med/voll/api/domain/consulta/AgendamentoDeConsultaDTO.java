package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record AgendamentoDeConsultaDTO(

    Long idMedico,

    @NotNull Long idPaciente,

    @NotNull @Future LocalDateTime data) {
}
