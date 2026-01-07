package SpringbootProject.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DemandForProperty {
	O_CN("Ở - Công năng"),
    O_ND("Ở - Nghỉ dưỡng"),
    O_CNND("Ở - Cả Công năng và Nghỉ dưỡng"),
    O_DT("Ở và Đầu tư"),

    DT_DT("Đầu tư dòng tiền"),
    DT_LV("Đầu tư - Sinh lời/Lãi vốn"),

    O_UNDEFINED("Ở - Chưa xác định"),
    DT_UNDEFINED("Đầu tư - Chưa xác định"),
	HG("Hỏi giúp"),
	
	UNDEFINED("Chưa xác định"),
	KNC("Ko còn nhu cầu");

    private final String label;

    DemandForProperty(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
