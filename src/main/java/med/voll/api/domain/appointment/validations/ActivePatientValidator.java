package med.voll.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ActivePatientValidator implements AppointmentSchedulingValidator{

    @Autowired
    private PatientRepository repository;

    public void validate (AppointmentSchedulingData data){

        var patientIsActive = repository.findActiveById(data.patientId());
        if (!patientIsActive){
            throw new ValidationException("Appointment could not be scheduled by an inactive doctor");
        }
    }

}
