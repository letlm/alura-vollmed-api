package med.voll.api.controller;

import med.voll.api.domain.appointment.*;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.doctor.Specialty;
import med.voll.api.domain.patient.PatientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DataScheduleAppointment> dataScheduleAppointmentJson;

    @Autowired
    private JacksonTester<DataDetailsAppointment> dataDetailsAppointmentJson;

    @Autowired
    private JacksonTester<DataCancelAppointment> dataCancelAppointmentJson;

    @MockBean
    private AppointmentSchedule appointmentSchedule;

    @MockBean
    private DoctorRepository doctorRepository;

    @MockBean
    private PatientRepository patientRepository;

    @Test
    @DisplayName("It should return http code 400 when information is invalid")
    @WithMockUser
    void scheduleScenario1() throws Exception {
        var response = mvc
                .perform(post("/appointments"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return http code 200 when information is valid")
    @WithMockUser
    void scheduleScenario2() throws Exception {
        var date = LocalDateTime.now().plusHours(1);
        var especialidade = Specialty.DERMATOLOGIA;

        var dataDetails = new DataDetailsAppointment(null,2l, 5l, date);

        when(appointmentSchedule.toSchedule(any())).thenReturn(dataDetails);

        var response = mvc
                .perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataScheduleAppointmentJson.write(
                                new DataScheduleAppointment(2l,5l, date, especialidade)
                        ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonExpected = dataDetailsAppointmentJson.write(
                dataDetails
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonExpected);
    }

    @Test
    @DisplayName("It should return http code 400 when information is invalid")
    @WithMockUser
    void cancelScenario1() throws Exception {
        var response = mvc
                .perform(delete("/appointments"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return http code 200 when information is valid")
    @WithMockUser
    void cancelScenario2() throws Exception {

        var reason = CancellationReason.OUTROS;

        var response = mvc
                .perform(delete("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataCancelAppointmentJson.write(
                                new DataCancelAppointment(1l,  reason)
                        ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getContentAsString()).isEmpty();
    }
}