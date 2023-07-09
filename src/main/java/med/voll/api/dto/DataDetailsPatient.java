package med.voll.api.dto;

import med.voll.api.model.Address;
import med.voll.api.model.Patient;

public record DataDetailsPatient(String nome, String email, String telefone, String cpf, Address endereco) {
    public DataDetailsPatient(Patient patient){
        this(patient.getNome(), patient.getEmail(), patient.getTelefone(), patient.getCpf(), patient.getEndereco());
    }
}
