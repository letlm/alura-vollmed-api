package med.voll.api.domain.doctor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.address.AddressData;

public record DataUpdateDoctor(
        @NotNull
        Long id,
        String nome,
        String telefone,
        @Valid
        AddressData endereco
) {
}
