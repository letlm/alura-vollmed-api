package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.DataListDoctor;
import med.voll.api.dto.DataRegistrationDoctor;
import med.voll.api.model.Doctor;
import med.voll.api.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("doctors")
public class DoctorController {
    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public void createDoctor(@RequestBody @Valid DataRegistrationDoctor data){
        repository.save(new Doctor(data));
    }


    @GetMapping
    public List<DataListDoctor> list(){
        return repository.findAll().stream().map(DataListDoctor::new).toList();
    }
}
