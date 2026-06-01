package med.voll.api.domain.patient.dtos;

import med.voll.api.domain.patient.Patient;

public record PatientDetailData(
                                Long id,
                                String name,
                                String email,
                                String phone_number,
                                String ssn
) {
    public PatientDetailData (Patient patient){
        this(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getPhone_number(),
                patient.getSsn());
    }
}
