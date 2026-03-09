package med.voll.api.domain.paciente;

import med.voll.api.domain.endereco.Endereco;

public record DetalhamentoPacienteDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        Endereco endereco) {

    public DetalhamentoPacienteDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(),
                paciente.getCpf(), paciente.getTelefone(), paciente.getEndereco());
    }
}
