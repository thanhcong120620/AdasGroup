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
	
	public PhoneProcess() {
	}

	public List<ExcelObject> deletePhoneDuplicateWithOtherData(List<ExcelObject> excelObjectsOrigin, List<ExcelObject> excelObjectsUse) {
	    // Tạo một Set để lưu trữ các giá trị column1 từ excelObjectsOrigin
	    Set<String> originColumn1Values = new HashSet<>();
	    
	    // Thêm các giá trị column1 đã làm sạch vào Set
	    for (ExcelObject excelObject : excelObjectsOrigin) {
	        originColumn1Values.add(excelObject.getColumn1().replaceAll("[.,\\s]", ""));
	    }

	    // Duyệt qua danh sách excelObjectsUse và xóa các phần tử trùng lặp
	    Iterator<ExcelObject> iterator = excelObjectsUse.iterator();
	    while (iterator.hasNext()) {
	        ExcelObject excelObjectUse = iterator.next();
	        String cleanColumn1 = excelObjectUse.getColumn1().replaceAll("[.,\\s]", "");

	        // Nếu giá trị đã có trong Set, xóa phần tử khỏi excelObjectsUse
	        if (originColumn1Values.contains(cleanColumn1)) {
	        	ExcelObjectError.add(excelObjectUse);
	            iterator.remove();
	        }
	    }
	    return excelObjectsUse;
	}

	
	public List<ExcelObject> getDeletedPhoneDuplicates() {
		    return ExcelObjectError;  
		}


}
