package med.voll.api.dto;

import med.voll.api.model.Doctor;
import med.voll.api.model.Patient;
import med.voll.api.utils.Specialty;

public record DataListPatient(Long id, String nome, String email, String cpf) {

    public DataListPatient(Patient patient) {
        this(patient.getId(), patient.getNome(), patient.getEmail(), patient.getCpf());
    }
}
