package SpringbootProject.algorithms.GmailMKTAlgorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import SpringbootProject.algorithms.IOAlgorithm.AlgorithmReaderExcel;
import SpringbootProject.entity.notSaving.ExcelObject;

public class PhoneProcess {
	
	private static List<ExcelObject> ExcelObjectError = new  ArrayList<>();
	private static String[] countStatus = new String[2];
	
	public PhoneProcess() {
	}
	
	/*
	 * xử lý cac ký tự ko phải là số điênj thoại và có ".0" ở cuối dãy
	 * */
	public List<String> phoneStringProcessing(String input) {
	    List<String> phoneNumberList = new ArrayList<>();
//	    System.out.println("phoneStringProcessing");
	    // Tách chuỗi theo các dấu phân cách, ví dụ: xuống dòng, dấu phẩy, dấu gạch chéo, dấu chấm phẩy
	    String[] phoneNumbers = input.split("[,\\n;/-]+");

	    for (String phone : phoneNumbers) {
	    	phone = phone.replaceAll(" ", "");
	        // Loại bỏ phần ".0" nếu có trước khi loại bỏ các ký tự không phải số
	    	if (phone.matches(".*\\.0$")) {
            	phone = phone.replaceAll("\\.0$", "");
	            
	        }

	        // Loại bỏ các ký tự không phải số
	        phone = phone.replaceAll("[^0-9]", "");

	        // Thêm số điện thoại vào danh sách nếu không rỗng
	        if (!phone.isEmpty()) {
	            phoneNumberList.add(phone);
	        }
	    }

	    return phoneNumberList;
	}
	
	
	//-------------------------------------------Advanced----------------------------------------------------

	public List<ExcelObject> deletePhoneDuplicateWithOtherData(List<ExcelObject> excelObjectsOrigin, List<ExcelObject> excelObjectsUse) {
        // Tạo một Set để lưu trữ các giá trị column1 từ excelObjectsOrigin
        Set<String> originColumn1Values = new HashSet<>();
        // Tạo một Set để lưu trữ các giá trị column1 đã gặp trong excelObjectsUse
        Set<String> usedColumn1Values = new HashSet<>();

        int countInside = 0;
        int countOutside = 0;

        // Thêm các giá trị column1 đã làm sạch vào Set từ excelObjectsOrigin
        for (ExcelObject excelObject : excelObjectsOrigin) {
            originColumn1Values.add(excelObject.getColumn1().replaceAll("[.,\\s]", ""));
        }

        // Duyệt qua danh sách excelObjectsUse và xóa các phần tử trùng lặp với Origin hoặc trùng lặp trong Use
        Iterator<ExcelObject> iterator = excelObjectsUse.iterator();
        while (iterator.hasNext()) {
            ExcelObject excelObjectUse = iterator.next();
            
            String cleanColumn1 = excelObjectUse.getColumn1().replaceAll("[.,\\s]", "");

            // Nếu giá trị đã có trong Set originColumn1Values hoặc đã gặp trước đó trong usedColumn1Values, xóa phần tử khỏi excelObjectsUse
            if (originColumn1Values.contains(cleanColumn1) ) {
                ExcelObjectError.add(excelObjectUse);  // Thêm vào danh sách lỗi nếu cần
                iterator.remove();
                countOutside = countOutside + 1;
            } else if(usedColumn1Values.contains(cleanColumn1)) {
            	countInside = countInside + 1;
            }
            
            
            else {
                // Nếu chưa gặp giá trị này, thêm vào usedColumn1Values
                usedColumn1Values.add(cleanColumn1);
            }
        }

        // Cập nhật thông báo về số lượng trùng lặp
        countStatus[0] = "Có " + countOutside + " dữ liệu trùng với file gốc!";
        countStatus[1] = "Có " + countInside + " dữ liệu trùng trùng lặp lẫn nhau trong file cần lọc!";

        return excelObjectsUse;
    }

	
	public List<ExcelObject> getDeletedPhoneDuplicates() {
		    return ExcelObjectError;  
	}
	
	public String[] getCountStatus() {
	    return countStatus;  
}


}
