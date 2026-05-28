package med.voll.api.domain.address;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//LOMBOK
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class Address {
    String street;
    String neighborhood;
    @Column(name = "zip_code")
    String zip_code;
    String city;
    String state;
    String complement;
    String number;

    public Address(AddressData address) {
        this.street = address.street();
        this.neighborhood = address.neighborhood();
        this.zip_code = address.zip_code();
        this.city = address.city();
        this.state = address.state();
        this.complement = address.complement();
        this.number = address.number();
    }

    public void updateData(AddressData data) {
        if (data.street() != null){
            this.street = data.street();
        }
        if (data.neighborhood() != null){
            this.neighborhood = data.neighborhood();
        }
        if (data.zip_code() != null){
            this.zip_code = data.zip_code();
        }
        if (data.city() != null){
            this.city = data.city();
        }
        if (data.state() != null){
            this.state = data.state();
        }
        if (data.complement() != null){
            this.complement = data.complement();
        }
        if (data.number() != null){
            this.number = data.number();
        }
    }
}
