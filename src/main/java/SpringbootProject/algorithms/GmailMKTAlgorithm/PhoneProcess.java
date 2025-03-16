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
	public List<String> phoneStringProcessing (String input) {
		
		List<String> phoneNumberList = new ArrayList<String>();
		
//		 Tách chuỗi thành mảng khi số điện thoại trong 1 ô mà xuống hàng
		String[] phoneNumbers = input.split("\\r?\\n");
		for(String phoneNumber : phoneNumbers) {
//			 Tách chuỗi thành mảng khi số điện thoại ngăn cách nhau bởi dấu ";"
	        String[] phoneNumberArray = phoneNumber.split(";");
	        
	        for (String phone : phoneNumberArray) {
	        	if (phone.matches(".*\\.0$")) {
	            	phone = phone.replaceAll("\\.0$", "");
	            }
	            // Loại bỏ các ký tự không phải số
	        	phone = phone.replaceAll("[^0-9]", "");
	            phoneNumberList.add(phone);
	        }
		}
	
		

        
        return phoneNumberList;
	}
	
	
	//-------------------------------------------Advanced----------------------------------------------------

	public List<ExcelObject> deletePhoneDuplicateWithOtherData(List<ExcelObject> excelObjectsOrigin, List<ExcelObject> excelObjectsUse) {
	    // Tạo một Set để lưu trữ các giá trị column1 từ excelObjectsOrigin
	    Set<String> originColumn1Values = new HashSet<>();
	    int countInside = 0;
	    int countOutside = 0;
	    
	    // Thêm các giá trị column1 đã làm sạch vào Set
	    for (ExcelObject excelObject : excelObjectsOrigin) {
	        originColumn1Values.add(excelObject.getColumn1().replaceAll("[.,\\s]", ""));
	    }

	    // Duyệt qua danh sách excelObjectsUse và xóa các phần tử trùng lặp
	    Iterator<ExcelObject> iterator = excelObjectsUse.iterator();
	    while (iterator.hasNext()) {
	        ExcelObject excelObjectUse = iterator.next();
	        countInside = countInside +1;
	        String cleanColumn1 = excelObjectUse.getColumn1().replaceAll("[.,\\s]", "");

	        // Nếu giá trị đã có trong Set, xóa phần tử khỏi excelObjectsUse
	        if (originColumn1Values.contains(cleanColumn1)) {
	        	ExcelObjectError.add(excelObjectUse);
	            iterator.remove();
	            countOutside = countOutside +1;
	        }
	    }
	    
	    countStatus[0] = "Có "+countOutside+" dữ liệu trùng với file gốc !";
	    countStatus[1] = "Có "+countInside+" dữ liệu trùng trùng lặp lẫn nhau trong file cần lọc !";

	    return excelObjectsUse;
	}

	
	public List<ExcelObject> getDeletedPhoneDuplicates() {
		    return ExcelObjectError;  
	}
	
	public String[] getCountStatus() {
	    return countStatus;  
}


}
