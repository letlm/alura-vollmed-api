package med.voll.api.controller;

import med.voll.api.domain.NewValidationException;
import med.voll.api.domain.appointment.*;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentSchedule {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public void toSchedule(DataScheduleAppointment data){
        if (!patientRepository.existsById(data.idPaciente())){
            throw new NewValidationException("Id do paciente informado não existe.");
        }

        if(data.idMedico() != null && !doctorRepository.existsById(data.idMedico())){
            throw new NewValidationException("Id do médico informado não existe.");
        }

        var doctor = chooseDoctor(data);
        var patient = patientRepository.getReferenceById(data.idPaciente());
        var appointment = new Appointment(null, doctor, patient, data.data(), null);
        appointmentRepository.save(appointment);
    }

    public void toCancel(DataCancelAppointment data){
        if(!appointmentRepository.existsById(data.idAppointment())){
            throw new NewValidationException("Id da consulta informado não existe.");
        }

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
