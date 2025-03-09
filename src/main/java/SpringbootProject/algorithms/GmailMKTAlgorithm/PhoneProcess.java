package SpringbootProject.algorithms.GmailMKTAlgorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import SpringbootProject.algorithms.IOAlgorithm.AlgorithmReaderExcel;
import SpringbootProject.entity.notSaving.ExcelObject;

public class PhoneProcess {
	
	public PhoneProcess() {
	}

	/*
	 * Lọc phone trùng nhau từ 2 data
	 * 2 cột đầu của 2 data này đều là số điện thoại.
	 * Xử lý thao tác chuyển cột đầu của 2 file bằng thủ công từ Excel rồi sau đó sử dụng hàm này để lọc
	 * */
//	public List<ExcelObject> deletePhoneDuplicate(List<ExcelObject> excelObjectsOrigin, List<ExcelObject> excelObjectsUse) {
//		for(ExcelObject excelObjectUse : excelObjectsUse) {
//			for(ExcelObject excelObjectOrigin : excelObjectsOrigin) {
//				if(excelObjectUse.getColumn1().replaceAll("[.,\\s]", "").equals(excelObjectOrigin.getColumn1().replaceAll("[.,\\s]", ""))) {
//					excelObjectsUse.remove(excelObjectUse);
//				}
//			}
//		}
//		return excelObjectsUse;
//	}
//	public List<ExcelObject> deletePhoneDuplicate(List<ExcelObject> excelObjectsOrigin, List<ExcelObject> excelObjectsUse) {
//	    // Sử dụng Iterator để duyệt và xóa phần tử một cách an toàn
//	    Iterator<ExcelObject> iterator = excelObjectsUse.iterator();
//	    while (iterator.hasNext()) {
//	        ExcelObject excelObjectUse = iterator.next();
//	        for (ExcelObject excelObjectOrigin : excelObjectsOrigin) {
//	            if (excelObjectUse.getColumn1().replaceAll("[.,\\s]", "").equals(excelObjectOrigin.getColumn1().replaceAll("[.,\\s]", ""))) {
//	                // Sử dụng iterator.remove() để xóa phần tử
//	                iterator.remove();
//	                break; // Sau khi xóa phần tử, thoát khỏi vòng lặp để tránh xóa nhiều lần
//	            }
//	        }
//	    }
//	    return excelObjectsUse;
//	}
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
	            iterator.remove();
	        }
	    }
	    return excelObjectsUse;
	}


}
