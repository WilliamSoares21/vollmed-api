package med.voll.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.AgendamentoDeConsultaDTO;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DetalhamentoDeConsultaDTO;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;

@Service
public class ConsultaService {

  @Autowired
  private ConsultaRepository consultaRepository;

  @Autowired
  private MedicoRepository medicoRepository;

  @Autowired
  private PacienteRepository pacienteRepository;

  @Autowired
  private List<ValidadorAgendamentoDeConsulta> validadores;

  public DetalhamentoDeConsultaDTO agendar(AgendamentoDeConsultaDTO dados) {
    validadores.forEach(v -> v.validar(dados));

    var medico = escolherMedico(dados);

    var paciente = pacienteRepository.findById(dados.idPaciente())
        .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado com id: " + dados.idPaciente()));

    var consulta = new Consulta(null, medico, paciente, dados.data());
    consultaRepository.save(consulta);

    return new DetalhamentoDeConsultaDTO(consulta);
  }

  public Medico escolherMedico(AgendamentoDeConsultaDTO dados) {
    if (dados.idMedico() != null) {
      return medicoRepository.getReferenceById(dados.idMedico());
    }
    if (dados.especialidade() == null) {
      throw new ValidationException("Especialidade é obrigatoria quando o médico não for escolhido");
    }

    Medico medico = medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    if (medico == null) {
      throw new ValidationException("Não há médicos disponíveis para esta especialidade e data.");
    }
    return medico;
  }
}
