package SpringbootProject.entity.enums;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
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
    
    public static List<String> getAllLabels() {
        return Arrays.stream(values())
                .map(Salutation::getLabel)
                .toList();
    }
    
    @JsonCreator
    public static Salutation fromJson(String value) {
        return fromLabel(value);
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
