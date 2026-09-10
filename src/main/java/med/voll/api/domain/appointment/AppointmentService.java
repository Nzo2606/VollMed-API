package med.voll.api.domain.appointment;


import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.validations.cancellation.CancellationValidator;
import med.voll.api.domain.appointment.validations.dtos.AppointmentCancellationData;
import med.voll.api.domain.appointment.validations.dtos.AppointmentDetailData;
import med.voll.api.domain.appointment.validations.dtos.AppointmentSchedulingData;
import med.voll.api.domain.appointment.validations.schedulling.AppointmentSchedulingValidator;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<AppointmentSchedulingValidator> schedulingValidators;

    @Autowired
    private List<CancellationValidator> cancellationValidators;


    //Método de agendamento de consultas
    public AppointmentDetailData schedule(AppointmentSchedulingData data){

        if (!patientRepository.existsById(data.patientId())){
            throw new ValidationException("Informed patient ID does not exist!");
        }

        if (data.doctorId()!= null && !doctorRepository.existsById(data.doctorId())){
            throw new ValidationException("Informed doctor ID does not exist!");
        }

        schedulingValidators.forEach(v -> v.validate(data));

        var patient = patientRepository.findById(data.patientId()).get();
        var doctor = chooseDoctor(data);

        if (doctor == null){
            throw new ValidationException("There is no available doctor on this date!");
        }

        var appointment = new Appointment(doctor, patient, data.date());
        appointment.setStatus();
        appointmentRepository.save(appointment);

        return new AppointmentDetailData(appointment);
    }

    @Transactional
    //Método de listagem de consultas
    public Page<AppointmentDetailData> listAll(Pageable pagination){
        return appointmentRepository.findAll(pagination)
                .map(appointment -> {
                    appointment.setStatus();
                    return new AppointmentDetailData(appointment);
                });

    }

    //Método de escolha de médico
    private Doctor chooseDoctor (AppointmentSchedulingData data){
        if (data.doctorId() != null){
            return doctorRepository.getReferenceById(data.doctorId());
        }

        if (data.specialty() == null){
            throw new ValidationException("Specialty is mandatory when doctor is not choosen");
        }
        return doctorRepository.chooseRandomDoctorAvailableOnTheDate(data.specialty(), data.date());
    }

    //Método de cancelamento de consulta
    public void cancel(@Valid AppointmentCancellationData data) {
        if (!appointmentRepository.existsById(data.appointmentId())){
            throw new ValidationException("Informed appointment Id does not exist!");
        }

        cancellationValidators.forEach(v -> v.cancel(data));

        var appointment = appointmentRepository.getReferenceById(data.appointmentId());
        appointment.cancel(data.reason());
        appointment.setStatus();
    }

}
