package med.voll.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.paciente.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

  Page<Paciente> findAllByAtivoTrue(Pageable paginacao);

  boolean findAtivoById(@NotNull Long idPaciente);
}
