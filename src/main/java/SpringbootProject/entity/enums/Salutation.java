package SpringbootProject.entity.enums;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Salutation {
	ANH("Anh"),
    CHI("Chị"),
    CHU("Chú"),
    CO("Cô"),
    BAN("Bạn"),
    EM("Em"),
    UNDEFINED("Không xác định");

    private final String label;

    Salutation(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
    
    private static final Map<String, Salutation> LABEL_MAP = new HashMap<>();

    static {
        for (Salutation s : values()) {
            LABEL_MAP.put(s.label.toLowerCase(Locale.ROOT), s);
        }
    }


    public static Salutation fromLabel(String label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(label.toLowerCase(Locale.ROOT),UNDEFINED );
    }
}
