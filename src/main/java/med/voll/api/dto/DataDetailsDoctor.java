package med.voll.api.dto;

import med.voll.api.model.Address;
import med.voll.api.model.Doctor;
import med.voll.api.utils.Specialty;

public record DataDetailsDoctor(Long id, String nome, String email, String crm, String telefone, Specialty especialidade, Address endereco) {

    public DataDetailsDoctor(Doctor doctor){
        this(doctor.getId(), doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getTelefone(), doctor.getEspecialidade(), doctor.getEndereco());
    }

}
