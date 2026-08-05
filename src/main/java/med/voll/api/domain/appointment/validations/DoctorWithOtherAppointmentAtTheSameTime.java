package med.voll.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorWithOtherAppointmentAtTheSameTime implements AppointmentSchedulingValidator{

    @Autowired
    private AppointmentRepository repository;

    public void validate (AppointmentSchedulingData data){
        var doctorHasOtherAppointmentAtTheSameTime = repository.existsByDoctorIdAndData(data.doctorId(), data.data());
        if (doctorHasOtherAppointmentAtTheSameTime){
            throw new ValidationException("This Doctor has other appointment in this same time slot");
        }
    }
}
