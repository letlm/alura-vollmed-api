package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.DataCancelAppointment;
import med.voll.api.domain.appointment.DataDetailsAppointment;
import med.voll.api.domain.appointment.DataScheduleAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("appointments")
public class AppointmentController {


    @Autowired
    private AppointmentSchedule schedule;
    @PostMapping
    @Transactional
    public ResponseEntity schedule(@RequestBody @Valid DataScheduleAppointment data){
        schedule.toSchedule(data);
        return ResponseEntity.ok(new DataDetailsAppointment(null, null, null, null));
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity cancel(@RequestBody @Valid DataCancelAppointment data){
        schedule.toCancel(data);
        return ResponseEntity.noContent().build();
    }
}
