package med.voll.api.domain.appointment.validations.schedulling;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.validations.dtos.AppointmentSchedulingData;
import med.voll.api.domain.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveDoctorValidator implements AppointmentSchedulingValidator{

    @Autowired
    private DoctorRepository repository;

    public void validate (AppointmentSchedulingData data){
        // usuário não escolheu médico (opcional)
        if (data.doctorId() == null){
            return;
        }

        var doctorIsActive = repository.findActiveById(data.doctorId());
        if (!doctorIsActive){
            throw new ValidationException("Appointment could not be scheduled by an inactive doctor");
        }
    }
}
