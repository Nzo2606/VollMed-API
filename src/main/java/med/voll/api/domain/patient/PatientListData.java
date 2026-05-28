package med.voll.api.domain.patient;

public record PatientListData(
                              Long id,
                              String name,
                              String email,
                              String ssn) {
    public PatientListData (Patient patient){
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getSsn());
    }
}
