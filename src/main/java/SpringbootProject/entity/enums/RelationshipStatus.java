package SpringbootProject.entity.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RelationshipStatus {
	PURCHASED("Đã mua"),
    NOT_PURCHASED("Chưa mua"),
    UNDEFINED("Chưa xác định");

    private final String label;

    RelationshipStatus(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
    
    private static final Map<String, RelationshipStatus> LABEL_MAP = new HashMap<>();

    static {
        for (RelationshipStatus s : values()) {
            LABEL_MAP.put(s.label.toLowerCase(Locale.ROOT), s);
        }
    }


    public static RelationshipStatus fromLabel(String label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(label.toLowerCase(Locale.ROOT),UNDEFINED );
    }
}
