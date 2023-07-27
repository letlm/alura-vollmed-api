package med.voll.api.domain.doctor;

import med.voll.api.domain.address.AddressData;
import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.patient.DataPatientRegistration;
import med.voll.api.domain.patient.Patient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager em;
    @Test
    @DisplayName("It should return null when the only registered doctor is not available on the date")
    void chooseRandomDoctorFreeOnDateScenario1() {
        //given ou arrange
        var nextMondayAt10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var doctor = registerDoctor("Medica", "medica@voll.med", "123456", Specialty.DERMATOLOGIA);
        var patient = registerPatient("Paciente", "paciente@email.com", "00000000000");
        registerConsultation(doctor, patient, nextMondayAt10);

        //when ou act
        var doctorFree = doctorRepository.chooseRandomDoctorFreeOnDate(Specialty.DERMATOLOGIA, nextMondayAt10);

        //then ou assert
        assertThat(doctorFree).isNull();
    }

    @Test
    @DisplayName("It should return doctor when he is available on the date")
    void chooseRandomDoctorFreeOnDateScenario2() {
        var nextMondayAt10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var doctor = registerDoctor("Medica", "medica@voll.med", "123456", Specialty.DERMATOLOGIA);

        var doctorFree = doctorRepository.chooseRandomDoctorFreeOnDate(Specialty.DERMATOLOGIA, nextMondayAt10);
        assertThat(doctorFree).isEqualTo(doctor);
    }

    private void registerConsultation(Doctor medico, Patient paciente, LocalDateTime data) {
        em.persist(new Appointment(null, medico, paciente, data, null));
    }

    private Doctor registerDoctor(String nome, String email, String crm, Specialty especialidade) {
        var doctor = new Doctor(dataDoctor(nome, email, crm, especialidade));
        em.persist(doctor);
        return doctor;
    }

    private Patient registerPatient(String nome, String email, String cpf) {
        var patient = new Patient(dataPatient(nome, email, cpf));
        em.persist(patient);
        return patient;
    }

    private DataRegistrationDoctor dataDoctor(String nome, String email, String crm, Specialty especialidade) {
        return new DataRegistrationDoctor(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dataAddress()
        );
    }

    private DataPatientRegistration dataPatient(String nome, String email, String cpf) {
        return new DataPatientRegistration(
                nome,
                email,
                "61999999999",
                cpf,
                dataAddress()
        );
    }

    private AddressData dataAddress() {
        return new AddressData(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }


}