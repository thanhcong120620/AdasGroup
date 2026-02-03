package SpringbootProject.entity.enums;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DataType {
	RAW_DATA("Raw data"),
    FILTERED_DATA("Filter data"),
    CRM_DATA("CRN data"),
    UNDEFINED("Không xác định");

//    private final String description;
//
//    DataType(String description) {
//        this.description = description;
//    }
//
//    @JsonValue
//    public String getDescription() {
//        return description;
//    }
    
    private final String label;
    
    DataType(String label) {
        this.label = label;
    }
    
    @JsonValue
    public String getLabel() {
        return label;
    }
    
    public static List<String> getAllLabels() {
        return Arrays.stream(values())
                .map(DataType::getLabel)
                .toList();
    }

    
    private static final Map<String, DataType> LABEL_MAP = new HashMap<>();

    static {
        for (DataType s : values()) {
            // chuẩn hoá key khi build map
            LABEL_MAP.put(s.label.toLowerCase(Locale.ROOT), s);
        }
    }

    public static DataType fromLabel(String label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(
            label.toLowerCase(Locale.ROOT),
            UNDEFINED
        );
    }
}
