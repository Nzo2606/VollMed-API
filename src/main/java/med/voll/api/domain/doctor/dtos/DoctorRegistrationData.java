package med.voll.api.domain.doctor.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.address.AddressData;
import med.voll.api.domain.doctor.Specialty;

public record DoctorRegistrationData(
                                     @NotBlank
                                     String name,

                                     @NotBlank
                                     @Email
                                     String email,

                                     @NotBlank
                                     String phone_number,

                                     @NotBlank
                                     @Pattern(regexp = "\\d{4,6}")
                                     String crm,



                                     @NotNull
                                     Specialty specialty,


                                     @NotNull
                                     @Valid
                                     AddressData address) {
}
