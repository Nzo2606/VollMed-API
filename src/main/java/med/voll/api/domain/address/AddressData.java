package med.voll.api.domain.address;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressData(
                          @NotBlank
                          String street,

                          @NotBlank
                          String neighborhood,

                          @NotBlank
                          @Pattern(regexp = "\\d{8}")
                          String zip_code,

                          @NotBlank
                          String city,

                          @NotBlank
                          String state,

                          String complement,

                          String number) {
}
