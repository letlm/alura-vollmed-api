package med.voll.api.domain.appointment;

import med.voll.api.domain.NewValidationException;
import med.voll.api.domain.appointment.validations.cancellation.ValidationCancelScheduleAppointment;
import med.voll.api.domain.appointment.validations.scheduling.ValidationScheduleAppointment;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentSchedule {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<ValidationScheduleAppointment> validations;

    @Autowired
    private List<ValidationCancelScheduleAppointment> cancelValidations;

    public DataDetailsAppointment toSchedule(DataScheduleAppointment data){
        if (!patientRepository.existsById(data.idPaciente())){
            throw new NewValidationException("Id do paciente informado não existe.");
        }

        if(data.idMedico() != null && !doctorRepository.existsById(data.idMedico())){
            throw new NewValidationException("Id do médico informado não existe.");
        }

        validations.forEach(v -> v.validate(data));

        var doctor = chooseDoctor(data);
        var patient = patientRepository.getReferenceById(data.idPaciente());

        if(doctor == null){
            throw new NewValidationException("Nenhum médico disponível nesta data.");
        }

        var appointment = new Appointment(null, doctor, patient, data.data(), null);
        appointmentRepository.save(appointment);

        return new DataDetailsAppointment(appointment);
    }

    public void toCancel(DataCancelAppointment data){
        if(!appointmentRepository.existsById(data.idAppointment())){
            throw new NewValidationException("Id da consulta informado não existe.");
        }

        cancelValidations.forEach(v -> v.validate(data));

        var appointment = appointmentRepository.getReferenceById(data.idAppointment());
        appointment.cancel(data.reason());
    }

   private Doctor chooseDoctor(DataScheduleAppointment data) {
        if(data.idMedico() != null){
            return doctorRepository.getReferenceById(data.idMedico());
        }
        if (data.especialidade() == null) {
            throw new NewValidationException("Especialidade é obrigatória quando o médico não é informado.");
        }

        return doctorRepository.chooseRandomDoctorFreeOnDate(data.especialidade(), data.data());
    }
}
