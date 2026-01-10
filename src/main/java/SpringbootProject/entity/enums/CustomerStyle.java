package SpringbootProject.entity.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    
    private static final Map<String, CustomerStyle> LABEL_MAP = new HashMap<>();

    static {
        for (CustomerStyle s : values()) {
            // chuẩn hoá key khi build map
            LABEL_MAP.put(s.label.toLowerCase(Locale.ROOT), s);
        }
    }


    public static CustomerStyle fromLabel(String label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(
            label.toLowerCase(Locale.ROOT),
            UNDEFINED
        );
    }
}
