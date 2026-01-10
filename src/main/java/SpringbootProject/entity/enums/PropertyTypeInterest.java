package SpringbootProject.entity.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PropertyTypeInterest {
    LAND_PLOT("Đất nền"),
    APARTMENT("Căn hộ chung cư"),
    CONDOTEL("Căn hộ khách sạn"),
    VILLA("Biệt thự"),
    TOWNHOUSE("Nhà phố"),
    SHOPHOUSE("Shophouse"),
    UNDEFINED("Chưa xác định");

    private final String label;

    PropertyTypeInterest(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
    
    private static final Map<String, PropertyTypeInterest> LABEL_MAP = new HashMap<>();

    static {
        for (PropertyTypeInterest s : values()) {
            LABEL_MAP.put(s.label.toLowerCase(Locale.ROOT), s);
        }
    }


    public static PropertyTypeInterest fromLabel(String label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(label.toLowerCase(Locale.ROOT),UNDEFINED );
    }
}
