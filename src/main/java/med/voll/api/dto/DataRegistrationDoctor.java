package med.voll.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.utils.Specialty;

public record DataRegistrationDoctor(
        @NotBlank(message = "O campo nome é obrigatório")
        String nome,
        @NotBlank(message = "O campo email é obrigatório")
        @Email
        String email,

        @NotBlank(message = "O campo telefone é obrigatório")
        @Pattern(regexp = "\\d{11}")
        String telefone,

        @NotBlank(message = "O campo crm é obrigatório")
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull(message = "O campo especialidade é obrigatório")
        Specialty especialidade,
        @NotNull(message = "Nome é obrigatório")
        @Valid
        AddressData endereco
) {
}
