package med.voll.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DataUpdatePatient(
        @NotNull
        Long id,
        String nome,
        String telefone,
        @Valid
        AddressData endereco
) {
}
