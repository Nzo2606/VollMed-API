package med.voll.api.domain.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentDetailData(
                                    Long id,

                                    Long doctorId,

                                    @NotNull
                                    Long patientId,

                                    @NotNull
                                    @Future
                                    LocalDateTime data) {
}
