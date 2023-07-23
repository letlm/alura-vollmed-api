package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.NewValidationException;
import med.voll.api.domain.appointment.DataScheduleAppointment;
import java.time.Duration;
import java.time.LocalDateTime;

public class AdvanceTimeValidation {
    public void validate(DataScheduleAppointment data){

        var consultationDate = data.data();
        var now = LocalDateTime.now();
        var differenceInMinutes = Duration.between(now, consultationDate).toMinutes();

        if (differenceInMinutes < 30) {
            throw new NewValidationException("A consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
