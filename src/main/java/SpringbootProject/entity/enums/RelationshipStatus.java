package SpringbootProject.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RelationshipStatus {
	PURCHASED("Đã mua"),
    NOT_PURCHASED("Chưa mua");

    private final String label;

    RelationshipStatus(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
