package pl.kpkpur.zsbddatagenerator.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScreeningEmployeeId implements Serializable {
    private Long employeeId;
    private Long screeningId;
}
