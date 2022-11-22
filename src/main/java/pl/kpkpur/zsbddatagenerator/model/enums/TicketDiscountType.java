package pl.kpkpur.zsbddatagenerator.model.enums;

public enum TicketDiscountType {
    CHILD_UNDER_3(100),
    STUDENT(25),
    PENSIONER(30);

    public final int discountPercentage;

    TicketDiscountType(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
