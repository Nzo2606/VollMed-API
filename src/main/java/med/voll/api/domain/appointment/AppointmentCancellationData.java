package med.voll.api.domain.appointment;

public record AppointmentCancellationData (

        Long appointmentId,

        CancellationReason reason){
}
