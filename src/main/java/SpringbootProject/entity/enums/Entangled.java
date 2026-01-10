package SpringbootProject.entity.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Entangled {
	PRICE("Giá"),
    LEGAL("Pháp lý"),
    TIMING("Thời điểm"),
    INVESTOR("CĐT"),
    LOCATION("Vị trí"),
    AMENITIES("Tiện ích"),
    DESIGN("Thiết kế"),
    UNDEFINED("Chưa xác định");

//    private final String description;
//
//    Entangled(String description) {
//        this.description = description;
//    }
//
//    @JsonValue
//    public String getDescription() {
//        return description;
//    }
    
    
    private final String label;
    
    Entangled(String label) {
        this.label = label;
    }
    
    @JsonValue
    public String getLabel() {
        return label;
    }
    
    private static final Map<String, Entangled> LABEL_MAP = new HashMap<>();

    static {
        for (Entangled s : values()) {
            // chuẩn hoá key khi build map
            LABEL_MAP.put(s.label.toLowerCase(Locale.ROOT), s);
        }
    }


    public static Entangled fromLabel(String label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(
            label.toLowerCase(Locale.ROOT),
            UNDEFINED
        );
    }
}
