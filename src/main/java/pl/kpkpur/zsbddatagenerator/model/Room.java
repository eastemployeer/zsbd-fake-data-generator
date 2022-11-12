package pl.kpkpur.zsbddatagenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Room {
    private String name;
    private Integer seatsInRow;
    private Integer rows;
    private String sponsor;
    private Integer is3d;
    private Integer isVip;
}
