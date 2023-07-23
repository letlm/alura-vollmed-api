package med.voll.api.domain.doctor;

import med.voll.api.domain.doctor.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByAtivoTrue(Pageable pagination);


    @Query("""
            select d from Doctor d
            where
            d.ativo = 1
            and
            d.especialidade = :especialidade
            and
            d.id not in(
                select a.medico.id from Appointment a
                where
                a.data = :data
            )
            order by rand()
            limit 1
            """)
    Doctor chooseRandomDoctorFreeOnDate(Specialty especialidade, LocalDateTime data);


    @Query("""
            select d.ativo
            from Doctor d
            where
            d.id = :id 
            """)
    Boolean findAtivoById(Long idMedico);
}
