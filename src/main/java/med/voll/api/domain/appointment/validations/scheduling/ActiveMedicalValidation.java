package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.NewValidationException;
import med.voll.api.domain.appointment.DataScheduleAppointment;
import med.voll.api.domain.appointment.validations.scheduling.ValidationScheduleAppointment;
import med.voll.api.domain.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveMedicalValidation implements ValidationScheduleAppointment {

    @Autowired
    private DoctorRepository repository;
    public void validate(DataScheduleAppointment data){
        if (data.idMedico() == null) {
            return;
        }

        var doctorIsActive = repository.findAtivoById(data.idMedico());

        if(!doctorIsActive){
            throw new NewValidationException("O médico não está disponível.");
        }

    }
}
