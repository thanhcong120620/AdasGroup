package SpringbootProject.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CustomerHesitate {
	DISCOUNT("Giảm giá"),
    PAYMENT_PROGRESS("Tiến độ thanh toán"),
    CUT_LOSS("Cắt máu");

    private final String description;

    CustomerHesitate(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
