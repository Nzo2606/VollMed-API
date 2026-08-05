package med.voll.api.domain.appointment;


import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<AppointmentSchedulingValidator> validators;

    public void schedule(AppointmentSchedulingData data){

        if (!patientRepository.existsById(data.patientId())){
            throw new ValidationException("Informed patient ID does not exist!");
        }

        if (data.doctorId()!= null && !doctorRepository.existsById(data.doctorId())){
            throw new ValidationException("Informed doctor ID does not exist!");
        }

        var patient = patientRepository.findById(data.patientId()).get();
        var doctor = chooseDoctor(data);
        var appointment = new Appointment(null, doctor, patient, data.data());
        appointmentRepository.save(appointment);
    }

    private Doctor chooseDoctor (AppointmentSchedulingData data){
        if (data.doctorId() != null){
            return doctorRepository.getReferenceById(data.doctorId());
        }

        if (data.specialty() == null){
            throw new ValidationException("Specialty is mandatory when doctor is not choosen");
        }

        return doctorRepository.chooseRandomDoctorAvailableOnTheDate(data.specialty(), data.data());
    }

    public void cancel(@Valid AppointmentCancellationData data) {
        if (!appointmentRepository.existsById(data.appointmentId())){
            throw new ValidationException("Informed appointment Id does not exist!");
        }

        var appointment = appointmentRepository.getReferenceById(data.appointmentId());
        appointment.cancel(data.reason());
    }
}
