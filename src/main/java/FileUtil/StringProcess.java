package FileUtil;

import java.lang.reflect.Field;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class StringProcess {
	
	//Lấy dữ liệu mới từ chuỗi dữ liệu cũ ngăn cách bởi ";", và ko lấy dữ liệu bị trùng lặp
	public  String mergeUnique(String c, String input) {
        Set<String> set = new LinkedHashSet<>();

        // dữ liệu gốc
        for (String s : c.split("; ")) {
            set.add(s);
        }

        // dữ liệu mới
        for (String s : input.split("; ")) {
            set.add(s);
        }

        return String.join("; ", set);
    }
	
	
	/**
     * Trả về danh sách tên field của 1 object bất kỳ
     * @param obj Object bất kỳ (entity)
     * @return String[] chứa tên các field
     */
    public static String[] getAllFieldNames(Object obj) {
        if (obj == null) {
            return new String[0];
        }

        // Lấy Class của object
        Class<?> clazz = obj.getClass();

        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                     .map(Field::getName)
                     .toArray(String[]::new);
    }
    
    
    //Lấy index của giá trị trong mảng
    public static int indexOfIgnoreCase(String[] arr, String target) {
        if (arr == null || target == null) {
            return -1;
        }

        for (int i = 0; i < arr.length; i++) {
            if (target.equalsIgnoreCase(arr[i])) {
                return i;
            }
        }
        return -1;
    }
    
    
    
}
