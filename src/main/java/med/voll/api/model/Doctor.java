package med.voll.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.DataRegistrationDoctor;
import med.voll.api.dto.DataUpdateDoctor;
import med.voll.api.utils.Specialty;

@Table(name="doctors")
@Entity(name="Doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Specialty especialidade;

    @Embedded
    private Address endereco;

    private Boolean ativo;

    public Doctor(DataRegistrationDoctor data) {
        this.ativo = true;
        this.nome = data.nome();
        this.email = data.email();
        this.telefone = data.telefone();
        this.crm = data.crm();
        this.especialidade = data.especialidade();
        this.endereco = new Address(data.endereco());
    }

    public void updateInformations(DataUpdateDoctor data) {
        if (data.nome() != null){
            this.nome = data.nome();
        }
        if (data.telefone() != null) {
            this.telefone = data.telefone();
        }
        if (data.endereco() != null) {
            this.endereco.updateInformations(data.endereco());
        }
    }

    public void inactivate(Doctor doctor) {
        this.ativo = false;
    }
}
