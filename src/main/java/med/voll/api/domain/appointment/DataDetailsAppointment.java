package med.voll.api.domain.appointment;
import java.time.LocalDateTime;

public record DataDetailsAppointment(
        Long id,

        Long idMedico,

        Long idPaciente,
        LocalDateTime data
) {
        public DataDetailsAppointment(Appointment appointment) {
                this(appointment.getId(), appointment.getMedico().getId(), appointment.getPaciente().getId(), appointment.getData());
        }
}
