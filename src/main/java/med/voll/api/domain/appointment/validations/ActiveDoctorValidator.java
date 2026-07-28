package med.voll.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import med.voll.api.domain.doctor.DoctorRepository;

public class ActiveDoctorValidator {

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
