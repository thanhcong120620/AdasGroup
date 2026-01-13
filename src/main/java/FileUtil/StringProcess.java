package FileUtil;

import java.lang.reflect.Field;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringProcess {

	/*
	 * Thuật toán kết hợp:
	 * removeDuplicates và  mergeUnique
	 * 1. Xử lý các chuỗi (có định dạng "String1;String2") trùng
	 * 2. Tách và đổ vào List
	 * */
	public static List<String> spitAndRemoveDuplicatest (List<String> input){
//		List<String> splitList = splitAndMerge(input); 
		
		List<String> outPutList = removeDuplicates(splitAndMerge(input));
		

		return outPutList;
	}
	
	
	
//=======================================================================================================================	
	
	/*
	 * Hàm tách chuỗi đc cấu tạo bởi các chuỗi con đc ngăn cách bởi dấu ";" và " "
	 * Tạo một List String mới và thêm các chuỗi con này vào
	 * */
	public static List<String> splitAndMerge(List<String> inputList) {
	    if (inputList == null || inputList.isEmpty()) {
	        return List.of(); // Java 9+
	    }

	    return inputList.stream()
	            .filter(s -> s != null && !s.isBlank())
	            .flatMap(s -> List.of(s.split("\\s*;\\s*")).stream())
	            .collect(Collectors.toList());
	}
	
	/**
     * Loại bỏ các phần tử trùng lặp trong List
     * - Giữ lại 1 phần tử đại diện
     * - Giữ nguyên thứ tự xuất hiện ban đầu
     *
     * @param input danh sách đầu vào
     * @return danh sách đã loại bỏ trùng lặp
     */
    public static List<String> removeDuplicates(List<String> input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return new ArrayList<>(new LinkedHashSet<>(input));
    }
	
    /*
     * Tìm kiếm chuỗi bằng tiền tố
     * */
    public static List<String> findByPrefix(String[] sortedData, String prefix) {
        List<String> foundStrings = new ArrayList<>();

        // 3. TÌM KIẾM NHỊ PHÂN: Tìm vị trí đầu tiên có thể chứa prefix
        int index = Arrays.binarySearch(sortedData, prefix);

        // Nếu không tìm thấy chính xác prefix, binarySearch trả về: -(vị trí chèn) - 1
        if (index < 0) {
            index = -(index + 1);
        }

        // 4. DUYỆT TUYẾN TÍNH: Từ vị trí tìm được, lấy ra các chuỗi khớp prefix
        // Vì mảng đã sắp xếp, các chuỗi có cùng tiền tố sẽ nằm cạnh nhau
        for (int i = index; i < sortedData.length; i++) {
            if (sortedData[i].startsWith(prefix)) {
                foundStrings.add(sortedData[i]);
            } else {
                // Đã vượt qua nhóm chuỗi có cùng tiền tố, dừng lại để tối ưu
                break;
            }
        }

        return foundStrings;
    }
    
	
	//Lấy dữ liệu mới từ chuỗi dữ liệu cũ ngăn cách bởi ";", và ko lấy dữ liệu bị trùng lặp
	public static  String mergeUnique(String c, String input) {
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
