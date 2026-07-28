package med.voll.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;

import java.time.DayOfWeek;

public class ClinicsHoursOfOperationValidator {

    public void validate (AppointmentSchedulingData data){
        var appointmentDate = data.data();

        // validador de domingo
        var sunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        // validador antes do horário
        var beforeClinicOperation = appointmentDate.getHour() < 7;

        // validador depois do horário
        var afterClinicOperation = appointmentDate.getHour() > 18;

        if (sunday || beforeClinicOperation || afterClinicOperation){
            throw new ValidationException("Appointment outside the clinic's hours of operation");
        }
    }

}
