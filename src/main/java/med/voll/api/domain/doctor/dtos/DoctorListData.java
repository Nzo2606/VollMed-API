package med.voll.api.domain.doctor.dtos;

import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.Specialty;

public record DoctorListData(Long id,
                             String name,
                             String email,
                             String crm,
                             Specialty specialty) {

    public DoctorListData (Doctor doctor){
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getSpecialty());
    }
}



