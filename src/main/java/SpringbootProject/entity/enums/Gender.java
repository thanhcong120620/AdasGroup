package SpringbootProject.entity.enums;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
	MALE("Nam"),
    FEMALE("Nữ"),
    OTHER("Khác"),
    UNDEFINED("Trống");

    private final String label;

    Gender(String label) {
        this.label = label;
    }


    @JsonValue
    public String getLabel() {
        return label;
    }
    
    private static final Map<String, Gender> LABEL_MAP = new HashMap<>();

    static {
        for (Gender s : values()) {
            // chuẩn hoá key khi build map
            LABEL_MAP.put(s.label.toLowerCase(Locale.ROOT), s);
        }
    }


    public static Gender fromLabel(String label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(label.toLowerCase(Locale.ROOT),UNDEFINED );
    }
}
