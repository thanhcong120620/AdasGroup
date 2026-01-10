package SpringbootProject.entity.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum InteractionLevel {
	VERY_LOW(1),
    LOW(2),
    MEDIUM(3),
    HIGH(4),
    VERY_HIGH(5),
    EXTREME(6),
    UNDEFINED(0);

    private final int value;

    InteractionLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    private static final Map<Integer, InteractionLevel> LABEL_MAP = new HashMap<>();

    static {
        for (InteractionLevel s : values()) {
            // chuẩn hoá key khi build map
            LABEL_MAP.put(s.value, s);
        }
    }


    public static InteractionLevel fromLabel(Integer label) {
        if (label == null) {
            return UNDEFINED;
        }

        return LABEL_MAP.getOrDefault(label, UNDEFINED);
    }
}
