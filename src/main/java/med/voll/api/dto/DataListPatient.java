package med.voll.api.dto;

import med.voll.api.model.Doctor;
import med.voll.api.model.Patient;
import med.voll.api.utils.Specialty;

public record DataListPatient(String nome, String email, String cpf) {

    public DataListPatient(Patient patient) {
        this(patient.getNome(), patient.getEmail(), patient.getCpf());
    }
}
