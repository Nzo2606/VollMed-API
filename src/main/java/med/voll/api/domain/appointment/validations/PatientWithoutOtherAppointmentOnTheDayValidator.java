package med.voll.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientWithoutOtherAppointmentOnTheDayValidator implements AppointmentSchedulingValidator{

    @Autowired
    AppointmentRepository repository;

    public void validate (AppointmentSchedulingData data){
        var firstTimeSlot = data.data().withHour(7);
        var lastTimeSlot = data.data().withHour(18);
        var patientHasOtherAppointmentOnTheDay = repository.existsByPatientIdAndDataBetween(data.patientId(), firstTimeSlot, lastTimeSlot);
        if (patientHasOtherAppointmentOnTheDay){
            throw new ValidationException("The patient already has an scheduled appointment on this day.");
        }
    }
}
