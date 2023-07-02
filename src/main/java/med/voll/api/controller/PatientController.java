package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.DataListPatient;
import med.voll.api.dto.DataPatientRegistration;
import med.voll.api.model.Patient;
import med.voll.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("patients")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public void createPatient(@RequestBody @Valid DataPatientRegistration data){
        repository.save(new Patient(data));
    }

    @GetMapping
    public List<DataListPatient> list(){
        return repository.findAll().stream().map(DataListPatient::new).toList();
    }
}
