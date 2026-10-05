package med.voll.api.controller;

import med.voll.api.domain.appointment.AppointmentService;
import med.voll.api.domain.appointment.validations.dtos.AppointmentDetailData;
import med.voll.api.domain.appointment.validations.dtos.AppointmentSchedulingData;
import med.voll.api.domain.doctor.Specialty;
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
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE_TIME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;

    // JSON RECEBIDO PELA API
    @Autowired
    private JacksonTester<AppointmentSchedulingData> appointmentSchedulingDataJson;

    // JSON DEVOLVIDO PELA API
    @Autowired
    private JacksonTester<AppointmentDetailData> appointmentDetailDataJson;

    @MockBean
    private AppointmentService agenda;


    @Test
    @DisplayName("It should response 400 http code when information are invalid")
    @WithMockUser
    void schedule_scenario1() throws Exception {
        var response = mvc.perform(post("/appointment"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("It should response 200 http code when information are valid")
    @WithMockUser
    void schedule_scenario2() throws Exception {
        var date = LocalDateTime.now().plusHours(1);
        var specialty = Specialty.CARDIOLOGY;

        var detailData = new AppointmentDetailData(null, 2l, 5l, date, null, null);


        when(agenda.schedule(any())).thenReturn(detailData);

        var response = mvc.perform(
                post("/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(appointmentSchedulingDataJson.write(
                                new AppointmentSchedulingData(2l, 5l, date, specialty)
                        ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = appointmentDetailDataJson.write(
                detailData
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}