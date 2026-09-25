package med.voll.api.domain.doctor;

import med.voll.api.domain.address.AddressData;
import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.doctor.dtos.DoctorRegistrationData;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.dtos.PatientRegistrationData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles ("test")
class DoctorRepositoryTest {

    @Autowired
    DoctorRepository doctorRepository;


    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("It should response `null` when doctor is not available on the date")
    void chooseRandomDoctorAvailableOnTheDateScenario1() {
        // given ou arrange
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = registerDoctor("Doctor", "doctor@voll.med", "123456", Specialty.CARDIOLOGY);
        var patient = registerPatient("Pacient", "pacient@email.com", "00000000000");
        registerAppointment(doctor, patient, nextMondayAt10);

        //when ou act
        var availableDoctor = doctorRepository.chooseRandomDoctorAvailableOnTheDate(Specialty.CARDIOLOGY, nextMondayAt10);

        //then ou assert
        assertThat(availableDoctor).isNull();

    }

    @Test
    @DisplayName("It should response the doctor when he is available on the date")
    void chooseRandomDoctorAvailableOnTheDateScenario2() {
        // given ou arrange
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = registerDoctor("Doctor", "doctor@voll.med", "123456", Specialty.CARDIOLOGY);

        //when ou act
        var availableDoctor = doctorRepository.chooseRandomDoctorAvailableOnTheDate(Specialty.CARDIOLOGY, nextMondayAt10);

        //then ou assert
        assertThat(availableDoctor).isEqualTo(doctor);

    }





    private void registerAppointment(Doctor doctor, Patient patient, LocalDateTime date) {
        em.persist(new Appointment(doctor, patient, date));
    }

    private Doctor registerDoctor(String name, String email, String crm, Specialty specialty) {
        var doctor = new Doctor(doctorData(name, email, crm, specialty));
        em.persist(doctor);
        return doctor;
    }

    private Patient registerPatient(String name, String email, String cpf) {
        var patient = new Patient(patientData(name, email, cpf));
        em.persist(patient);
        return patient;
    }

    private DoctorRegistrationData doctorData(String name, String email, String crm, Specialty specialty) {
        return new DoctorRegistrationData(
                name,
                email,
                "61999999999",
                crm,
                specialty,
                addressData()
        );
    }

    private PatientRegistrationData patientData(String name, String email, String cpf) {
        return new PatientRegistrationData(
                name,
                email,
                "61999999999",
                cpf,
                addressData()
        );
    }

    private AddressData addressData() {
        return new AddressData(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }


}