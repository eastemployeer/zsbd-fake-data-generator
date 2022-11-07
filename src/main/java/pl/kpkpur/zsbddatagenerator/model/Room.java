package pl.kpkpur.zsbddatagenerator.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kpkpur.zsbddatagenerator.model.enums.PaymentMethod;

@Data
@Entity
@Table(name = "ROOM")
@NoArgsConstructor
public class Room {
    public Room(
            String name,
            Long seatsInRow,
            Long rows,
            String sponsor,
            Integer is3d,
            Integer isVip) {
        this.name = name;
        this.seatsInRow = seatsInRow;
        this.rows = rows;
        this.sponsor = sponsor;
        this.is3d = is3d;
        this.isVip = isVip;
    }

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "SEATS_IN_ROW")
    private Long seatsInRow;

    @Column(name = "ROWS")
    private Long rows;

    @Column(name = "SPONSOR")
    private String sponsor;

    @Column(name = "IS_3D")
    private Integer is3d;

    @Column(name = "IS_VIP")
    private Integer isVip;
}
