package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.NewValidationException;
import med.voll.api.domain.appointment.DataScheduleAppointment;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidationHorarioFuncionamento implements ValidationScheduleAppointment {

    public void validate(DataScheduleAppointment data){
        var consultationDate = data.data();

        var sunday = consultationDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeOpeningTheClinic = consultationDate.getHour() < 7;
        var afterClosingTheClinic = consultationDate.getHour() > 18;
        if (sunday || beforeOpeningTheClinic || afterClosingTheClinic) {
            throw new NewValidationException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
