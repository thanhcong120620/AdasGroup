package SpringbootProject.entity.enums;

public enum InteractionLevel {
	VERY_LOW(1),
    LOW(2),
    MEDIUM(3),
    HIGH(4),
    VERY_HIGH(5),
    EXTREME(6);

    private final int value;

    InteractionLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
