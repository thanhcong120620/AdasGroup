package SpringbootProject.entity.enums;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ContactTimeSlot {
	MORNING("Buổi sáng", 8, 12),
    AFTERNOON("Buổi chiều", 13, 17),
    EVENING("Buổi tối", 18, 21),
    LATE_EVENING("Tối muộn", 21, 23),
    ANYTIME("Bất kỳ lúc nào", null, null),
    UNDEFINED("Không xác định",null,null);

    private final String label;
    private final Integer fromHour;
    private final Integer toHour;

    ContactTimeSlot(String label, Integer fromHour, Integer toHour) {
        this.label = label;
        this.fromHour = fromHour;
        this.toHour = toHour;
    }

    public String getLabel() {
        return label;
    }

    @JsonValue
    public Integer getFromHour() {
        return fromHour;
    }

    @JsonValue
    public Integer getToHour() {
        return toHour;
    }
    
    private static final Map<String, ContactTimeSlot> LABEL_MAP = new HashMap<>();

    static {
        for (ContactTimeSlot s : values()) {
            // chuẩn hoá key khi build map
            LABEL_MAP.put(s.label.toLowerCase(Locale.ROOT), s);
        }
    }


    public static ContactTimeSlot fromLabel(String label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(
            label.toLowerCase(Locale.ROOT),
            UNDEFINED
        );
    }
}
