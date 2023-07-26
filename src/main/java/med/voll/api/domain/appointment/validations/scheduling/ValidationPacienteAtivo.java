package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.NewValidationException;
import med.voll.api.domain.appointment.DataScheduleAppointment;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidationPacienteAtivo implements ValidationScheduleAppointment {

    @Autowired
    private PatientRepository repository;
    public void validate(DataScheduleAppointment data){
        var patientIsActive = repository.findAtivoById(data.idPaciente());

        if(!patientIsActive){
            throw new NewValidationException("Consulta não pode ser agendada com paciente excluído.");
        }

    }
}
