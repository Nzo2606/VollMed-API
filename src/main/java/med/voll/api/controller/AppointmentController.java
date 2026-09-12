package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.*;
import med.voll.api.domain.appointment.validations.dtos.AppointmentCancellationData;
import med.voll.api.domain.appointment.validations.dtos.AppointmentDetailData;
import med.voll.api.domain.appointment.validations.dtos.AppointmentSchedulingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    @Autowired
    AppointmentService agenda;



    @PostMapping
    @Transactional
    public ResponseEntity schedule(@RequestBody @Valid AppointmentSchedulingData data){

        var dto = agenda.schedule(data);

        return ResponseEntity.ok(dto);

    }

    @GetMapping
    @Transactional
    public ResponseEntity<Page<AppointmentDetailData>> listAppointments(@PageableDefault(size = 10, sort = {"date"}) Pageable pagination){
        var appointments = agenda.listAll(pagination);
        return ResponseEntity.ok(appointments);
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity cancel(@RequestBody @Valid AppointmentCancellationData data){
        agenda.cancel(data);
        return ResponseEntity.noContent().build();
    }
}
