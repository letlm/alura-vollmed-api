package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.NewValidationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.DataScheduleAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationMedicoComOutraConsulta implements ValidationScheduleAppointment {

    @Autowired
    private AppointmentRepository repository;
    public void validate(DataScheduleAppointment data){

        var doctorIsBusy = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(data.idMedico(), data.data());

        if (doctorIsBusy) {
            throw new NewValidationException("O médico já possui outra consulta nesse mesmo horário.");
        }

    }

}
