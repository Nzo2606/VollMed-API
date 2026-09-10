package med.voll.api.domain.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Boolean existsByDoctorIdAndDate(Long aLong, @NotNull @Future LocalDateTime date);

    Boolean existsByPatientIdAndDateBetween(@NotNull Long id, LocalDateTime firstTimeSlot, LocalDateTime lastTimeSlot);

}
