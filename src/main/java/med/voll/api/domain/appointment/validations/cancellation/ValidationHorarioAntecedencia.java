package med.voll.api.domain.appointment.validations.cancellation;

import med.voll.api.domain.NewValidationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.DataCancelAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidationHorarioAntecendenciaCancelamento")
public class ValidationHorarioAntecedencia implements ValidationCancelScheduleAppointment {

    @Autowired
    private AppointmentRepository repository;

    public void validate(DataCancelAppointment data){

        var consultation = repository.getReferenceById(data.idAppointment());
        var now = LocalDateTime.now();
        var differenceInHours = Duration.between(now, consultation.getData()).toHours();

        if (differenceInHours < 24) {
            throw new NewValidationException("A consulta somente pode ser cancelada com antecedência mínima de 24 horas!");
        }
    }

}
