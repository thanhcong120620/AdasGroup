package SpringbootProject.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
	CLOSING("Sắp chốt"),
    HIGH_INTEREST("Quan tâm sâu"),
    INTERESTED("Quan tâm"),
    AWARE("Biết dự án"),
    REFERENCING("Tham khảo"),
    ACTIVE_INTERACTION("Đang giữ tương tác");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
