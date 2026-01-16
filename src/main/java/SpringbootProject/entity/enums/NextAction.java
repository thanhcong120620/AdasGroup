package SpringbootProject.entity.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NextAction {
	ADD_ZALO_FRIEND("Kết bạn Zalo"),
	MESSAGE("Nhắn tin"),
    CALL("Gọi"),
    MEETING("Gặp"),
    SMS("SMS"),
    EMAIL("Gmail"),
    FIELD_MARKETING("Làm thị trường"),
    KEEP_INTERACTION("Giữ tương tác"),
    COLLECT_INFORMATION("Thu thập thông tin"),
    UNDEFINED("Không xác định");

    private final String label;

    NextAction(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
    
    private static final Map<String, NextAction> LABEL_MAP = new HashMap<>();

    static {
        for (NextAction s : values()) {
            LABEL_MAP.put(s.label.toLowerCase(Locale.ROOT), s);
        }
    }


    public static NextAction fromLabel(String label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(label.toLowerCase(Locale.ROOT),UNDEFINED );
    }
}
