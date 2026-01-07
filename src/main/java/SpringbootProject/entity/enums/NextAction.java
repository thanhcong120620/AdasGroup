package SpringbootProject.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NextAction {
	MESSAGE("Nhắn tin"),
    CALL("Gọi"),
    MEETING("Gặp"),
    SMS("SMS"),
    EMAIL("Gmail"),
    FIELD_MARKETING("Làm thị trường"),
    KEEP_INTERACTION("Giữ tương tác");

    private final String label;

    NextAction(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
