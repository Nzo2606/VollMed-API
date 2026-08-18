package med.voll.api.domain.appointment;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.patient.Patient;

import java.time.LocalDateTime;

@Table(name = "appointment")
@Entity(name = "Appointment")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Appointment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "doctor_id")
        private Doctor doctor;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "patient_id")
        private Patient patient;

        private LocalDateTime data;

        @Enumerated(EnumType.STRING)
        private CancellationReason cancellation_Reason;

        public void cancel(CancellationReason reason) {
                this.cancellation_Reason = reason;
        }

        public Appointment(Long id, Doctor doctor, Patient patient, LocalDateTime data) {
                this.id = id;
                this.doctor = doctor;
                this.patient = patient;
                this.data = data;
        }


}
