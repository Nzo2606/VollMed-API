package med.voll.api.domain.appointment.validations.cancellation;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.validations.dtos.AppointmentCancellationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentStatusValidatorToCheckCancelling implements CancellationValidator{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void cancel(AppointmentCancellationData data) {
        var appointment = appointmentRepository.getReferenceById(data.appointmentId());

        if (appointment.getStatus().equals("CONCLUÍDA")){
            throw new ValidationException("You can't cancel an appointment that has already been completed!");
        }

        if(appointment.getStatus().equals("CANCELADA")){
            throw new ValidationException("You can't cancel an appointment that has already been canceled!");
        }
    }
}
