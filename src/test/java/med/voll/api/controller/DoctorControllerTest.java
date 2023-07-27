package med.voll.api.controller;

import med.voll.api.domain.address.Address;
import med.voll.api.domain.address.AddressData;
import med.voll.api.domain.doctor.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DoctorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DataRegistrationDoctor> dataRegistrationDoctorJson;

    @Autowired
    private JacksonTester<DataDetailsDoctor> dataDetailsDoctorJson;

    @MockBean
    private DoctorRepository doctorRepository;


    @Test
    @DisplayName("It should return http code 400 when information is invalid")
    @WithMockUser
    void createDoctorScenario1() throws Exception {
        var response = mvc
                .perform(post("/doctors"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return http code 200 when information is valid")
    @WithMockUser
    void createDoctorScenario2() throws Exception {
        var dataRegister = new DataRegistrationDoctor(
                "Medico",
                "medico@voll.med",
                "61999999999",
                "123456",
                Specialty.DERMATOLOGIA,
                dataAddress());

        when(doctorRepository.save(any())).thenReturn(new Doctor(dataRegister));

        var response = mvc
                .perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataRegistrationDoctorJson.write(dataRegister).getJson()))
                .andReturn().getResponse();

        var dataDetails = new DataDetailsDoctor(
                null,
                dataRegister.nome(),
                dataRegister.email(),
                dataRegister.crm(),
                dataRegister.telefone(),
                dataRegister.especialidade(),
                new Address(dataRegister.endereco())
        );
        var jsonExpected = dataDetailsDoctorJson.write(dataDetails).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonExpected);
    }

    @Test
    void list() {
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