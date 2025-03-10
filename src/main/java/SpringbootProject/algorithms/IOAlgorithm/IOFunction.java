package SpringbootProject.algorithms.IOAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import SpringbootProject.entity.UserEntity;
import SpringbootProject.entity.notSaving.ExcelObject;

public class IOFunction {
	private static final String FOLDER_PATH = "D:/Desktop/My data/1.My Working/1.IVC/2.ICV-Digital/1.Develop/1.IVCDevelop/2.BackEnd/2.IVCBackEnd/AdminSystem/src/main/resources/static/image/IVC-Realtor-Image/Gmail-MKT-Image/";
	AlgorithmReaderExcelUserEntityList excelReader = new AlgorithmReaderExcelUserEntityList();
	
	
	
//-----------------------------------------FUNCTION----------------------------------------------------------	
	
	
	/*
	 * 
	 * */
	public static void algorithmWitter(String filePathSaving, List<ExcelObject> excelObjectList) {
		
        try {
        	AlgorithmWritterExcel.writeToExcel(excelObjectList, filePathSaving);
            System.out.println("File Excel đã được ghi thành công!");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	/*
	 * Bằng đường dẫn trực tiếp, ko cần lấy từ client
	 * */
	public static void getDataFromExcel(File file) {
		AlgorithmReaderExcel algorithmReaderExcel = new AlgorithmReaderExcel();
//		String filePath = "D:\\Desktop\\Diary\\ExcelObject.xlsx";
		
		//use function from iofunction
		List<ExcelObject> excelObjects = algorithmReaderExcel.readExcelFile(file);
		
		//check result
       for (ExcelObject excelObject : excelObjects) {
           System.out.println(">>"+excelObject);
       }
	}
	
	
	
	
	
	/*
	 * Bằng đường dẫn trực tiếp, ko cần lấy từ client
	 * */
	public static void getDataFromExcel(String filePath) {
		AlgorithmReaderExcel algorithmReaderExcel = new AlgorithmReaderExcel();
//		String filePath = "D:\\Desktop\\Diary\\ExcelObject.xlsx";
		
		//use function from iofunction
		List<ExcelObject> excelObjects = algorithmReaderExcel.readExcelFile(filePath);
		
		//check result
       for (ExcelObject excelObject : excelObjects) {
           System.out.println(">>"+excelObject);
       }
	}
	
	
	/*
	 * Đọc File được lấy trực tiếp từ client
	 * */
	public List<ExcelObject> dataFromExcelFileAndSaveToProject(MultipartFile[] files) throws IllegalStateException, IOException {
		AlgorithmReaderExcel algorithmReaderExcel = new AlgorithmReaderExcel();
    	List<ExcelObject> excelObjects = new ArrayList<ExcelObject>();
    	
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
            
            excelObjects = algorithmReaderExcel.readExcelFile(fullPath);
            
            break;
        }
    	
        
        return excelObjects;
    }
	
	
	//-----------------------------------------FUNCTION of Entity User---------------------------------------------------	
	
	/*
	 * Đọc dữ liệu của User Entity từ file excel với Parameter đường dẫn của file ko phải lấy file từ client
	 * Cập nhật lại code theo các thuộc tính của user Entity.
	 * Lấy file từ client và lưu vào resource, sau đó lấy path của file đang nằm tại resource đó và tạo thành "filePath"
	 * */
    public List<UserEntity> userListFromExcelFilePath(String filePath) {
//    	String filePath = "D:\\Desktop\\Diary\\Users.xlsx";
        
        List<UserEntity> users = excelReader.readExcelFile(filePath);
        
        return users;
    }
    
    
	/*
	 * Chỉ sử dụng cho User Entity, User Entity này sẽ được lưu data vào database
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