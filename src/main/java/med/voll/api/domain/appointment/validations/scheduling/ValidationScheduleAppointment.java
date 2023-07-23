package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.appointment.DataScheduleAppointment;

public interface ValidationScheduleAppointment {
    void validate(DataScheduleAppointment data);
}
