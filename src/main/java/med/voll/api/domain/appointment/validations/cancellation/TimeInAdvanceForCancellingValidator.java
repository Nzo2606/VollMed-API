package med.voll.api.domain.appointment.validations.cancellation;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.validations.dtos.AppointmentCancellationData;
import med.voll.api.domain.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class TimeInAdvanceForCancellingValidator implements CancellationValidator{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void cancel(AppointmentCancellationData data) {
        var appointmentDate = appointmentRepository.getReferenceById(data.appointmentId());

        var now = LocalDateTime.now();

         var differenceInHours = Duration.between(now, appointmentDate.getDate()).toHours();

         if (differenceInHours < 24){
             throw new ValidationException("The Appointment can only be cancelled in a 24 hours advance!");
         }
    }
}
