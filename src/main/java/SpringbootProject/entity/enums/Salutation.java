package SpringbootProject.entity.enums;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Salutation {
	ANH("Anh"),
    CHI("Chị"),
    CHU("Chú"),
    CO("Cô"),
    BAN("Bạn"),
    EM("Em");

    private final String label;

    Salutation(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
