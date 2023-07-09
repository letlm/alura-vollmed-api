package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.DataListPatient;
import med.voll.api.dto.DataPatientRegistration;
import med.voll.api.dto.DataUpdatePatient;
import med.voll.api.model.Patient;
import med.voll.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Page<DataListPatient> list(@PageableDefault(size = 10, sort = {"nome"})Pageable pagination){
        return repository.findAllByAtivoTrue(pagination).map(DataListPatient::new);
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid DataUpdatePatient data){
        var patient = repository.getReferenceById(data.id());
        patient.updateInformations(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> delete(@PathVariable Long id){
        var patient = repository.getReferenceById(id);
        patient.inactivate(patient);

        return ResponseEntity.status(HttpStatus.OK).body("Paciente inativado");
    }
}
