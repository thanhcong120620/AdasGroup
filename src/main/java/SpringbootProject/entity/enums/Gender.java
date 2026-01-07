package SpringbootProject.entity.enums;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
	MALE("Nam"),
    FEMALE("Nữ"),
    UNDEFINED("Không xác định");

    private final String label;

    Gender(String label) {
        this.label = label;
    }


    @JsonValue
    public String getLabel() {
        return label;
    }
}
