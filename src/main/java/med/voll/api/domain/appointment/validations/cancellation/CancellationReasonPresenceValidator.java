package med.voll.api.domain.appointment.validations.cancellation;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.validations.dtos.AppointmentCancellationData;
import med.voll.api.domain.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CancellationReasonPresenceValidator implements CancellationValidator{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void cancel(AppointmentCancellationData data) {
        if(appointmentRepository.appointmentIsCanceled(data.appointmentId()) != null){
            throw new ValidationException("You can't cancel an appointment that is already canceled!");
        }
    }
}
