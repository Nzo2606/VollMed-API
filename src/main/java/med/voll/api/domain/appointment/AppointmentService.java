package med.voll.api.domain.appointment;


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


    public void schedule(AppointmentSchedulingData data){

        var patient = patientRepository.findById(data.patientId()).get();
        var doctor = doctorRepository.findById(data.doctorId()).get();
        var appointment = new Appointment(null, doctor, patient, data.data());
        appointmentRepository.save(appointment);
    }


}
