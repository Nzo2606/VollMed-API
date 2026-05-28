package med.voll.api.domain.patient;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Entity(name = "patients")
@Table(name = "patients")

//LOMBOK
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String email;
    String phone_number;
    String ssn;

    Boolean active;

    @Embedded
    Address address;

    public Patient(PatientRegistrationData data) {
        this.name = data.name();
        this.email = data.email();
        this.phone_number = data.phone_number();
        this.ssn = data.ssn();
        this.address = new Address(data.address());
        this.active = true;
    }

    public void updateData(@Valid PatientUpdateData data) {
        if (data.name() != null){
            this.name = data.name();
        }
        if (data.address() != null){
            this.address.updateData(data.address());
        }
        if (data.phone_number() != null){
            this.phone_number = data.phone_number();
        }
    }

    public void delete(){
        this.active = false;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


}
