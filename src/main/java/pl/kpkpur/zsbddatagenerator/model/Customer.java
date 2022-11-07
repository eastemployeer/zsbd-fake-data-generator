package pl.kpkpur.zsbddatagenerator.model;

import java.sql.Date;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kpkpur.zsbddatagenerator.model.enums.EmployeeRole;

@Data
@Entity
@Table(name = "CUSTOMER")
@NoArgsConstructor
public class Customer {
    public Customer(
            Long id,
            String name,
            String surname,
            String email,
            String password,
            String gender,
            Date birthDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthDate = birthDate;

    }

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "BIRTH_DATE")
    private java.sql.Date birthDate;
}
