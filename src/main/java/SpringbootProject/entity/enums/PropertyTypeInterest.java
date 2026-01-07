package SpringbootProject.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PropertyTypeInterest {
    LAND_PLOT("Đất nền"),
    APARTMENT("Căn hộ chung cư"),
    CONDOTEL("Căn hộ khách sạn"),
    VILLA("Biệt thự"),
    TOWNHOUSE("Nhà phố"),
    SHOPHOUSE("Shophouse");

    private final String label;

    PropertyTypeInterest(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
