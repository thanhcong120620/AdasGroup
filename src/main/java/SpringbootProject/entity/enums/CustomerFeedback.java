package SpringbootProject.entity.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CustomerFeedback {
	INTERESTED("Quan tâm"),
    CONSIDERING("Đang suy nghĩ"),
    COMPARING("So sánh"),
    SILENT("Im lặng"),
    DISCUSSING("Bàn bạc"),
    UNDEFINED("Chưa xác định");

//    private final String description;
//
//    CustomerFeedback(String description) {
//        this.description = description;
//    }
//
//    @JsonValue
//    public String getDescription() {
//        return description;
//    }
    
    private final String label;
    
    CustomerFeedback(String label) {
        this.label = label;
    }
    
    @JsonValue
    public String getLabel() {
        return label;
    }
    
    
    private static final Map<String, CustomerFeedback> LABEL_MAP = new HashMap<>();

    static {
        for (CustomerFeedback s : values()) {
            // chuẩn hoá key khi build map
            LABEL_MAP.put(s.label.toLowerCase(Locale.ROOT), s);
        }
    }


    public static CustomerFeedback fromLabel(String label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(
            label.toLowerCase(Locale.ROOT),
            UNDEFINED
        );
    }
}
