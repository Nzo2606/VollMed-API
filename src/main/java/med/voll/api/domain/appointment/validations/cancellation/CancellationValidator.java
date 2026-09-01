package med.voll.api.domain.appointment.validations.cancellation;

import med.voll.api.domain.appointment.validations.dtos.AppointmentCancellationData;

public interface CancellationValidator {

    void cancel(AppointmentCancellationData data);
}
