package med.voll.api.controller;

import med.voll.api.domain.address.Address;
import med.voll.api.domain.address.AddressData;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.doctor.Specialty;
import med.voll.api.domain.doctor.dtos.DoctorDetailData;
import med.voll.api.domain.doctor.dtos.DoctorRegistrationData;
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

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DoctorControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DoctorRegistrationData> doctorRegistrationDataJson;

    @Autowired
    private JacksonTester<DoctorDetailData> doctorDetailDataJson;

    @MockBean
    private DoctorRepository repository;


    @Test
    @DisplayName("It should response 400 http error when information are invalid")
    @WithMockUser
    void register_scenario1() throws Exception{
        var response = mvc.perform(post("/doctors"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should response 201 http code when information are valid")
    @WithMockUser
    void register_scenario2() throws Exception{
        var registerData = new DoctorRegistrationData(
            "Doctor",
            "doctor@voll.med",
     "619999999999",
             "123456",
                Specialty.CARDIOLOGY,
                addressData());

        when(repository.save(any())).thenReturn(new Doctor(registerData));

        var response = mvc
                .perform(post("/doctors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(doctorRegistrationDataJson.write(registerData).getJson()));

        var detailData = new DoctorDetailData(
                null,
                registerData.name(),
                registerData.email(),
                registerData.crm(),
                registerData.phone_number(),
                registerData.specialty(),
                new Address(registerData.address()));

        var expectedJson = doctorDetailDataJson.write(detailData).getJson();

        assertThat(response.getStatus()).isEqualTo((HttpStatus.CREATED.value());
        assertThat(response.getContentAsSrtring()).isEqualTo((expectedJson);

                )
    }


    private AddressData addressData(){
        return new AddressData(
                "street xpto",
                "neighborhood",
                "00000000",
                "Brasília",
                "DF",
                null,
                null
        );


    }
}