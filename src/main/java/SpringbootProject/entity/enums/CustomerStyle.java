package SpringbootProject.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CustomerStyle {
	CONTROL("Kiểm soát"),
    ANALYTICAL("Phân tích"),
    EXPRESSIVE("Thể hiện"),
    AMIABLE("Yêu mến"),
    UNDEFINED("Chưa xác định");

    private final String label;

    CustomerStyle(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
