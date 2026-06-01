package med.voll.api.domain.doctor.dtos;

import med.voll.api.domain.address.Address;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.Specialty;

public record DoctorDetailData(
                                Long id,
                                String name,
                                String email,
                                String crm,
                                String phone_number,
                                Specialty specialty,
                                Address address
                               )
{
    public DoctorDetailData (Doctor doctor){
        this(
                doctor.getId(),
                doctor.getName(),
                doctor.getEmail(),
                doctor.getCrm(),
                doctor.getPhone_number(),
                doctor.getSpecialty(),
                doctor.getAddress()
            );
    }
}
