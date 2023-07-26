package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.NewValidationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.DataScheduleAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationPacienteSemOutraConsulta implements ValidationScheduleAppointment {

    @Autowired
    private AppointmentRepository repository;
    public void validate(DataScheduleAppointment data){

        var firstTime = data.data().withHour(7);
        var lastTime = data.data().withHour(18);

        var patientIsBusy = repository.existsByPacienteIdAndDataBetween(data.idPaciente(), firstTime, lastTime);

        if (patientIsBusy) {
            throw new NewValidationException("O paciente já possui outra consulta nesse mesmo horário.");
        }

    }

}
