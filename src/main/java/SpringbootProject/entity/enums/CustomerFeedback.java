package SpringbootProject.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CustomerFeedback {
	INTERESTED("Quan tâm"),
    CONSIDERING("Đang suy nghĩ"),
    COMPARING("So sánh"),
    SILENT("Im lặng"),
    DISCUSSING("Bàn bạc"),
    UNDEFINED("Chưa xác định");

    private final String description;

    CustomerFeedback(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
