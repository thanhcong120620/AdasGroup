package SpringbootProject.entity.enums;

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
    RELATIONSHIP("MQH");

    private final String label;

    LeadSource(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
