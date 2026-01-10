package SpringbootProject.entity.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LeadSource {
	FACEBOOK_ADS("FB Ads"),
    GOOGLE_ADS("Google Ads"),
    ZALO_ADS("Zalo Ads"),
    CELLPHONE("Cellphone"),
    EMAIL("Gmail"),
    LISTING("Tin đăng"),
    FIELD_MARKETING("Thị trường"),
    EVENT("Sự kiện"),
    RELATIONSHIP("MQH"),
    UNDEFINED("Chưa xác định");

    private final String label;

    LeadSource(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
    
    private static final Map<String, LeadSource> LABEL_MAP = new HashMap<>();

    static {
        for (LeadSource s : values()) {
            // chuẩn hoá key khi build map
            LABEL_MAP.put(s.label.toLowerCase(Locale.ROOT), s);
        }
    }


    public static LeadSource fromLabel(String label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(label.toLowerCase(Locale.ROOT),UNDEFINED );
    }
}
