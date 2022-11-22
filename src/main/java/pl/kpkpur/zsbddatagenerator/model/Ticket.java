package pl.kpkpur.zsbddatagenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.kpkpur.zsbddatagenerator.model.enums.TicketDiscountType;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Ticket {
    private Long id;
    private Long screeningId;
    private Long customerId;
    private Long employeeId;
    private String row;
    private String seat;
    private Double discount;
    private TicketDiscountType discountType;
    private Timestamp purchaseDatetime;
}
