package med.voll.api.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.address.AddressData;

public record DataUpdatePatient(
        @NotNull
        Long id,
        String nome,
        String telefone,
        @Valid
        AddressData endereco
) {
}
