package SpringbootProject.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Region {
	NORTH("Miền Bắc"),
    CENTRAL("Miền Trung"),
    SOUTH("Miền Nam"),
    MEKONG_DELTA("Miền Tây"),
    CENTRAL_HIGHLANDS("Tây Nguyên");

    private final String label;

    Region(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
