package med.voll.api.domain.appointment.validations.cancellation;

import med.voll.api.domain.appointment.DataCancelAppointment;

public interface ValidationCancelScheduleAppointment {
    void validate(DataCancelAppointment data);
}
