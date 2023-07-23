package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.NewValidationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.DataScheduleAppointment;
import med.voll.api.domain.appointment.validations.scheduling.ValidationScheduleAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusyDoctorValidation implements ValidationScheduleAppointment {

    @Autowired
    private AppointmentRepository repository;
    public void validate(DataScheduleAppointment data){

        var doctorIsBusy = repository.existsByMedicoIdAndData(data.idMedico(), data.data());

        if (doctorIsBusy) {
            throw new NewValidationException("O médico já possui outra consulta nesse mesmo horário.");
        }

    }

}
