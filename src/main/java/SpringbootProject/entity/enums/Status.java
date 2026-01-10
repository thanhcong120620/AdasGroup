package SpringbootProject.entity.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
	CLOSING("Sắp chốt"),
    HIGH_INTEREST("Quan tâm sâu"),
    INTERESTED("Quan tâm"),
    AWARE("Biết dự án"),
    REFERENCING("Tham khảo"),
    ACTIVE_INTERACTION("Đang giữ tương tác"),
    UNDEFINED("Không xác định");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
    
    private static final Map<String, Status> LABEL_MAP = new HashMap<>();

    static {
        for (Status s : values()) {
            // chuẩn hoá key khi build map
            LABEL_MAP.put(s.label.toLowerCase(Locale.ROOT), s);
        }
    }

    public static Status fromLabel(String label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(label.toLowerCase(Locale.ROOT), UNDEFINED );
    }
}
