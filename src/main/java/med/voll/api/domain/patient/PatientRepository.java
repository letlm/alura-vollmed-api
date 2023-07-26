package med.voll.api.domain.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findAllByAtivoTrue(Pageable pagination);

    @Query("""
            select d.ativo
            from Patient d
            where
            d.id = :id 
            """)
    Boolean findAtivoById(Long id);
}
