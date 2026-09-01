package med.voll.api.domain.appointment.validations.schedulling;

import med.voll.api.domain.appointment.validations.dtos.AppointmentSchedulingData;

public interface AppointmentSchedulingValidator {

    void validate (AppointmentSchedulingData data);
}
