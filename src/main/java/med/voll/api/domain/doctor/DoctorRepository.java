package med.voll.api.domain.doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByAtivoTrue(Pageable pagination);

    @Query("""
            select m from Doctor m
            where
            m.ativo = true
            and
            m.especialidade = :especialidade
            and
            m.id not in(
                select c.medico.id from Appointment c
                where
                c.data = :data
                and
                c.motivoCancelamento is null
            )
            order by rand()
            limit 1
        """)
    Doctor chooseRandomDoctorFreeOnDate(Specialty especialidade, LocalDateTime data);

    @Query("""
            select m.ativo
            from Doctor m
            where
            m.id = :id
            """)
    Boolean findAtivoById(Long id);
}
