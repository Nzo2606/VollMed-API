package med.voll.api.domain.appointment.validations.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.appointment.CancellationReason;

import java.time.LocalDateTime;

public record AppointmentDetailData(
                                    Long id,

                                    Long doctorId,

                                    @NotNull
                                    Long patientId,

                                    @NotNull
                                    @Future
                                    LocalDateTime date,

                                    String status,

                                    CancellationReason cancellation_reason) {

    public AppointmentDetailData(Appointment appointment) {
        this(appointment.getId(), appointment.getDoctor().getId(),
                appointment.getPatient().getId(), appointment.getDate(), appointment.getStatus(), appointment.getCancellation_Reason());
    }
}
