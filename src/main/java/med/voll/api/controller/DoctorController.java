package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.doctor.DataDetailsDoctor;
import med.voll.api.domain.doctor.DataListDoctor;
import med.voll.api.domain.doctor.DataRegistrationDoctor;
import med.voll.api.domain.doctor.DataUpdateDoctor;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("doctors")
public class DoctorController {
    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity createDoctor(@RequestBody @Valid DataRegistrationDoctor data, UriComponentsBuilder uriBuilder){
        var doctor = new Doctor(data);
        repository.save(doctor);

        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DataDetailsDoctor(doctor));
    }

    @GetMapping
    public ResponseEntity<Page<DataListDoctor>> list(@PageableDefault(size = 10, sort = {"nome"}) Pageable pagination){
        var doctors = repository.findAllByAtivoTrue(pagination).map(DataListDoctor::new);

        return ResponseEntity.ok(doctors);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid DataUpdateDoctor data){
        var doctor = repository.getReferenceById(data.id());
        doctor.updateInformations(data);

        return ResponseEntity.ok(new DataDetailsDoctor(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var doctor = repository.getReferenceById(id);
        doctor.inactivate(doctor);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity details(@PathVariable Long id){
        var doctor = repository.getReferenceById(id);

        return ResponseEntity.ok(new DataDetailsDoctor(doctor));
    }

    /* @DeleteMapping("/{id}")
        @Transactional
        public void delete(@PathVariable Long id){
            repository.deleteById(id);
    }*/
}
