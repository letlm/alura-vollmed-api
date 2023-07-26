package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.patient.DataDetailsPatient;
import med.voll.api.domain.patient.DataListPatient;
import med.voll.api.domain.patient.DataPatientRegistration;
import med.voll.api.domain.patient.DataUpdatePatient;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("patients")
@SecurityRequirement(name = "bearer-key")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity createPatient(@RequestBody @Valid DataPatientRegistration data, UriComponentsBuilder uriBuilder){
        var patient = new Patient(data);
        repository.save(patient);

        var uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(new DataDetailsPatient(patient));
    }

    @GetMapping
    public ResponseEntity<Page<DataListPatient>> list(@PageableDefault(size = 10, sort = {"nome"})Pageable pagination){
        var patients =  repository.findAllByAtivoTrue(pagination).map(DataListPatient::new);

        return ResponseEntity.ok(patients);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid DataUpdatePatient data){
        var patient = repository.getReferenceById(data.id());
        patient.updateInformations(data);

        return ResponseEntity.ok(new DataDetailsPatient(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var patient = repository.getReferenceById(id);
        patient.inactivate(patient);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity details(@PathVariable Long id){
        var patient = repository.getReferenceById(id);

        return ResponseEntity.ok(new DataDetailsPatient(patient));
    }
}
