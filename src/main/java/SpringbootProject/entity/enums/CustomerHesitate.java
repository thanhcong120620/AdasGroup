package SpringbootProject.entity.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CustomerHesitate {
	DISCOUNT("Giảm giá"),
    PAYMENT_PROGRESS("Tiến độ thanh toán"),
    CUT_LOSS("Cắt máu"),
    UNDEFINED("Không xác định");

//    private final String description;
	


//    CustomerHesitate(String description) {
//        this.description = description;
//    }


//    @JsonValue
//    public String getDescription() {
//        return description;
//    }
	
    private final String label;
    
    CustomerHesitate(String label) {
        this.label = label;
    }
    
    @JsonValue
    public String getLabel() {
        return label;
    }
    

    private static final Map<String, CustomerHesitate> LABEL_MAP = new HashMap<>();

    static {
        for (CustomerHesitate s : values()) {
            // chuẩn hoá key khi build map
            LABEL_MAP.put(s.label.toLowerCase(Locale.ROOT), s);
        }
    }


    public static CustomerHesitate fromLabel(String label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(
            label.toLowerCase(Locale.ROOT),
            UNDEFINED
        );
    }
}
