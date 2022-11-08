package pl.kpkpur.zsbddatagenerator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "TICKET")
@NoArgsConstructor
public class Ticket {
    public Ticket(
            Long id,
            Long screeningId,
            Long customerId,
            Long employeeId,
            String row,
            String seat,
            Double discount,
            Timestamp purchaseDatetime) {
        this.id = id;
        this.screeningId = screeningId;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.row = row;
        this.seat = seat;
        this.discount = discount;
        this.purchaseDatetime = purchaseDatetime;
    }

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SCREENING_ID")
    private Long screeningId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "ROW")
    private String row;

    @Column(name = "SEAT")
    private String seat;

    @Column(name = "DISCOUNT")
    private Double discount;

    @Column(name = "PURCHASE_DATETIME")
    private Timestamp purchaseDatetime;
}
