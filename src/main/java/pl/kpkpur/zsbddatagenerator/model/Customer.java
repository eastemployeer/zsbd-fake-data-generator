package pl.kpkpur.zsbddatagenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String gender;
    private Date birthDate;
}
