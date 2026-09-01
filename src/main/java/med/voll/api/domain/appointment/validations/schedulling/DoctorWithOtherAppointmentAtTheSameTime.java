package med.voll.api.domain.appointment.validations.schedulling;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.validations.dtos.AppointmentSchedulingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorWithOtherAppointmentAtTheSameTime implements AppointmentSchedulingValidator{

    @Autowired
    private AppointmentRepository repository;

    public void validate (AppointmentSchedulingData data){
        var doctorHasOtherAppointmentAtTheSameTime = repository.existsByDoctorIdAndDate(data.doctorId(), data.date());
        if (doctorHasOtherAppointmentAtTheSameTime){
            throw new ValidationException("This Doctor has other appointment in this same time slot");
        }
    }
}
