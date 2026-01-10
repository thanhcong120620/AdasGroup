package FileUtil;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EnumExtractorUtil {
	
//	Method lấy giá trị của Enums
	public static Map<String, List<String>> extractEnumMap(Object entity) {
        Map<String, List<String>> enumMap = new LinkedHashMap<>();

        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            Class<?> fieldType = field.getType();

            if (fieldType.isEnum()) {

                List<String> enumValues = Arrays.stream(fieldType.getEnumConstants())
                        .map(e -> ((Enum<?>) e).name())
                        .collect(Collectors.toList());

                enumMap.put(field.getName(), enumValues);
            }
        }
        return enumMap;
    }
	
	
	
	// Method mới: lấy LABEL của từng giá trị của mỗi enums
    public static Map<String, List<String>> extractEnumLabelMap(Object entity) {
        Map<String, List<String>> enumLabelMap = new LinkedHashMap<>();

        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            Class<?> fieldType = field.getType();

            if (fieldType.isEnum()) {

                List<String> labels = Arrays.stream(fieldType.getEnumConstants())
                        .map(e -> getEnumLabelSafely(e))
                        .collect(Collectors.toList());

                enumLabelMap.put(field.getName(), labels);
            }
        }
        return enumLabelMap;
    }

    // Helper an toàn: có getLabel() thì dùng, không có thì fallback
    private static String getEnumLabelSafely(Object enumConstant) {
        try {
            Method getLabelMethod = enumConstant.getClass().getMethod("getLabel");
            Object label = getLabelMethod.invoke(enumConstant);
            return label != null ? label.toString() : ((Enum<?>) enumConstant).name();
        } catch (Exception e) {
            // Không có getLabel()
            return ((Enum<?>) enumConstant).name();
        }
    }
	
	
}
