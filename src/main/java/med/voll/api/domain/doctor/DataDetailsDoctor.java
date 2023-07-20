package med.voll.api.domain.doctor;

import med.voll.api.domain.address.Address;

public record DataDetailsDoctor(Long id, String nome, String email, String crm, String telefone, Specialty especialidade, Address endereco) {

    public DataDetailsDoctor(Doctor doctor){
        this(doctor.getId(), doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getTelefone(), doctor.getEspecialidade(), doctor.getEndereco());
    }

}
