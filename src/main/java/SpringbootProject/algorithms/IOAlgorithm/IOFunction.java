package SpringbootProject.algorithms.IOAlgorithm;

import java.util.List;
import SpringbootProject.entity.UserEntity;

public class IOFunction {
    
    
	/*
	 * Đọc dữ liệu của User Entity từ file excel.
	 * Cập nhật lại code theo các thuộc tính của user Entity.
	 * Lấy file từ client và lưu vào resource, sau đó lấy path của file đang nằm tại resource đó và tạo thành "filePath"
	 * */
    public List<UserEntity> userListFromExcel(String filePath) {
//    	String filePath = "D:\\Desktop\\Diary\\Users.xlsx";
        AlgorithmReaderExcelUserEntityList excelReader = new AlgorithmReaderExcelUserEntityList();
        List<UserEntity> users = excelReader.readExcelFile(filePath);
        
        return users;
    }
    
    

}