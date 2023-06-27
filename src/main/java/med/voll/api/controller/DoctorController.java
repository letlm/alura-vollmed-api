package med.voll.api.controller;

import med.voll.api.address.Address;
import med.voll.api.doctor.DataRegistrationDoctor;
import med.voll.api.doctor.Doctor;
import med.voll.api.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doctor")
public class DoctorController {
    @Autowired
    private DoctorRepository repository;

    @PostMapping
    public void createDoctor(@RequestBody DataRegistrationDoctor data){
        repository.save(new Doctor(data));
    }
}
