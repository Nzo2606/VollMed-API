package med.voll.api.domain.appointment.validations.dtos;

import med.voll.api.domain.appointment.CancellationReason;

public record AppointmentCancellationData (

        Long appointmentId,

        CancellationReason reason){
}
