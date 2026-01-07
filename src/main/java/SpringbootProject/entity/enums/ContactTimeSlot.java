package SpringbootProject.entity.enums;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ContactTimeSlot {
	MORNING("Buổi sáng", 8, 12),
    AFTERNOON("Buổi chiều", 13, 17),
    EVENING("Buổi tối", 18, 21),
    LATE_EVENING("Tối muộn", 21, 23),
    ANYTIME("Bất kỳ lúc nào", null, null);

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
}
