package med.voll.api.domain.patient.dtos;

import med.voll.api.domain.patient.Patient;

public record PatientListData(
                              Long id,
                              String name,
                              String email,
                              String ssn) {
    public PatientListData (Patient patient){
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getSsn());
    }
}
