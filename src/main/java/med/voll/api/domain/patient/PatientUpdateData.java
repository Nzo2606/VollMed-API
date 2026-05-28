package med.voll.api.domain.patient;

import med.voll.api.domain.address.AddressData;

public record PatientUpdateData(
                                Long id,
                                String name,
                                String phone_number,
                                AddressData address) {
}
