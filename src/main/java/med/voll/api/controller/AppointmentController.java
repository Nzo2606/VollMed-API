package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentCancellationData;
import med.voll.api.domain.appointment.AppointmentDetailData;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import med.voll.api.domain.appointment.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService agenda;


    @PostMapping
    @Transactional
    public ResponseEntity schedule(@RequestBody @Valid AppointmentSchedulingData data){

        agenda.schedule(data);

        return ResponseEntity.ok(new AppointmentDetailData(null, null, null, null));

    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancel(@RequestBody @Valid AppointmentCancellationData data){
        agenda.cancel(data);
        return ResponseEntity.noContent().build();
    }
}
