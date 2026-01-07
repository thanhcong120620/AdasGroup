package SpringbootProject.entity.enums;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DataType {
	RAW_DATA("Raw data"),
    FILTERED_DATA("Filter data");

    private final String description;

    DataType(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
