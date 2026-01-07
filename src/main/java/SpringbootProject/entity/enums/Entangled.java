package SpringbootProject.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Entangled {
	PRICE("Giá"),
    LEGAL("Pháp lý"),
    TIMING("Thời điểm"),
    INVESTOR("CĐT"),
    LOCATION("Vị trí"),
    AMENITIES("Tiện ích"),
    DESIGN("Thiết kế");

    private final String description;

    Entangled(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
