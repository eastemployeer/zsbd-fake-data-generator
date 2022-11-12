package pl.kpkpur.zsbddatagenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.kpkpur.zsbddatagenerator.model.enums.ScreeningEmployeeResponsibility;

@Data
@AllArgsConstructor
public class ScreeningEmployee {
    private ScreeningEmployeeId screeningEmployeeId;
    private ScreeningEmployeeResponsibility responsibility;
}
