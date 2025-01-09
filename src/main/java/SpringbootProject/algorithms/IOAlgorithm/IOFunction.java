package SpringbootProject.algorithms.IOAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import SpringbootProject.entity.UserEntity;

public class IOFunction {
	private static final String FOLDER_PATH = "D:/Desktop/My data/1.My Working/1.IVC/2.ICV-Digital/1.Develop/1.IVCDevelop/2.BackEnd/2.IVCBackEnd/AdminSystem/src/main/resources/static/image/IVC-Realtor-Image/Gmail-MKT-Image/";
	AlgorithmReaderExcelUserEntityList excelReader = new AlgorithmReaderExcelUserEntityList();
	/*
	 * Đọc dữ liệu của User Entity từ file excel với Parameter đường dẫn của file
	 * Cập nhật lại code theo các thuộc tính của user Entity.
	 * Lấy file từ client và lưu vào resource, sau đó lấy path của file đang nằm tại resource đó và tạo thành "filePath"
	 * */
    public List<UserEntity> userListFromExcelFilePath(String filePath) {
//    	String filePath = "D:\\Desktop\\Diary\\Users.xlsx";
        
        List<UserEntity> users = excelReader.readExcelFile(filePath);
        
        return users;
    }
    
    
	/*
	 * Đọc dữ liệu của User Entity từ file excel với Parameter File
	 * Cập nhật lại code theo các thuộc tính của user Entity.
	 * Lấy file từ client và lưu vào resource, sau đó lấy path của file đang nằm tại resource đó và tạo thành "filePath"
	 * */
    public List<UserEntity> userListFromExcelFile(MultipartFile[] files) throws IllegalStateException, IOException {
    	List<UserEntity> users = new ArrayList<UserEntity>();
    	
    	// Lặp qua các tệp người dùng đã chọn
        for (MultipartFile file : files) {
        	// Lấy tên tệp
            String fileName = file.getOriginalFilename();
            // Tạo đường dẫn đầy đủ tới tệp hình ảnh
            String fullPath = FOLDER_PATH + fileName;
            // Kiểm tra nếu thư mục chưa tồn tại thì tạo mới
            File dir = new File(FOLDER_PATH);
            if (!dir.exists()) {
                dir.mkdirs();  // Tạo các thư mục con nếu chưa có
            }
            
            // Lưu tệp vào thư mục đã chỉ định
            file.transferTo(new File(fullPath));           
            
            users = excelReader.readExcelFile(fullPath);
            
            break;
        }
    	
        
        return users;
    }
    

}