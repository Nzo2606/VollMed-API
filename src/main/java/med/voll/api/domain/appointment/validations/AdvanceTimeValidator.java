package med.voll.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AdvanceTimeValidator implements AppointmentSchedulingValidator{
    // trata horário de regra de 30 minutos de antecedência no mínimo para agendamento de consultas

    public void validate (AppointmentSchedulingData data){
        var appointmentDate = data.data();

        //pega o tempo atual
        var now = LocalDateTime.now();

        // calcula diferença em minutos entre o tempo atual e a data da consulta
        var differenceInMinutes = Duration.between(now, appointmentDate).toMinutes();

        // se a diferença for menor que 30 min, da exceção
        if (differenceInMinutes < 30){
            throw new ValidationException("Appointment must be schedule with at least 30 minutes in advance");
        }
    }

}
