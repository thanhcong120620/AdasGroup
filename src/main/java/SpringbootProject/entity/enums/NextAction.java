package SpringbootProject.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NextAction {
	MESSAGE("Nhắn tin"),
    CALL("Gọi"),
    MEETING("Gặp"),
    SMS("SMS"),
    EMAIL("Gmail"),
    FIELD_MARKETING("Làm thị trường"),
    KEEP_INTERACTION("Giữ tương tác"),
    COLLECT_INFORMATION("Thu thập thông tin"),
    UNDEFINED("Không xác định");

    private final String label;

    NextAction(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
