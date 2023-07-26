package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import med.voll.api.domain.appointment.DataScheduleAppointment;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentSchedule;
import med.voll.api.domain.appointment.DataCancelAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("appointments")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {
    @Autowired
    private AppointmentSchedule scheduleAppointment;
    @PostMapping
    @Transactional
    public ResponseEntity schedule(@RequestBody @Valid DataScheduleAppointment data){
        var dto = scheduleAppointment.toSchedule(data);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity cancel(@RequestBody @Valid DataCancelAppointment data){
        scheduleAppointment.toCancel(data);
        return ResponseEntity.noContent().build();
    }
}
