package med.voll.api.dto;

import med.voll.api.model.Doctor;
import med.voll.api.utils.Specialty;

public record DataListDoctor(Long id, String nome, String email, String crm, Specialty especialidade) {

    public DataListDoctor (Doctor doctor) {
        this(doctor.getId(), doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getEspecialidade());
    }
}
