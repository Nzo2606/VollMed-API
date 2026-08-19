package med.voll.api.domain.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Boolean existsByDoctorIdAndData(Long aLong, @NotNull @Future LocalDateTime data);

    Boolean existsByPatientIdAndDataBetween(@NotNull Long id, LocalDateTime firstTimeSlot, LocalDateTime lastTimeSlot);

    @Query("""
            select a.cancellation_Reason
            from Appointment a
            where
            a.id = :id
            """)
    CancellationReason appointmentIsCanceled (Long id);
}
