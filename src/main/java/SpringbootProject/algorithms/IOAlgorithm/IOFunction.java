package SpringbootProject.algorithms.IOAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.PhoneProcess;
import SpringbootProject.entity.UserEntity;
import SpringbootProject.entity.notSaving.ExcelObject;

public class IOFunction {
	private static final String FOLDER_PATH = "D:/Desktop/My data/1.My Working/1.IVC/2.ICV-Digital/1.Develop/1.IVCDevelop/2.BackEnd/2.IVCBackEnd/AdminSystem/src/main/resources/static/image/IVC-Realtor-Image/Gmail-MKT-Image/";
	AlgorithmReaderExcelUserEntityList excelReader = new AlgorithmReaderExcelUserEntityList();
	AlgorithmExcelReaderUtil excelReaderUtil = new AlgorithmExcelReaderUtil();
	
	
//-----------------------------------------FUNCTION----------------------------------------------------------	
	
	/*
	 * READ EXCEL - MERGE FUNCTION
	 * Đọc file xử lý số điện thoại
	 * Bằng đường dẫn trực tiếp, ko cần lấy từ client
	 * */
	public List<ExcelObject> getDataFromExcelMergeFunction(MultipartFile file) {
//		AlgorithmReadDataFromExcelMerge algorithmReaderExcel = new AlgorithmReadDataFromExcelMerge();
		AlgorithmExcelReaderUtil excelReaderUtil = new AlgorithmExcelReaderUtil();
		
		List<ExcelObject> excelObjects = null;
		try {
			excelObjects = excelReaderUtil.processExcelFileAndMerge(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//check result
//       for (ExcelObject excelObject : excelObjects) {
//           System.out.println(">>"+excelObject);
//       }
		
		return excelObjects;
	}
	
	
	/*
	 * WRITTER
	 * MultipartFile method
	 * return a MultipartFile excel
	 * */
	public MultipartFile algorithmWitterMultipartFile(List<ExcelObject> excelObjectList) throws IOException {
		MultipartFile multipartFile = AlgorithmWritterExcel.writeToExcel(excelObjectList);
        
        return multipartFile;
	}
	
	
	
	
	/*
	 * Void method
	 * Save to local, need a path to save
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
	 * READ EXCEL - INTERGRATED FILTER FUNCTION
	 * Đọc file xử lý số điện thoại Bằng đường dẫn trực tiếp, ko cần lấy từ client
     * sử dụng filterAndValidatePhoneData trong phoneprocess để lọc sđt
     * sử dụng getDataFromExcelFilterFunctionWithValidPhone trong AlgorithmReadPhoneFromExcel để đọc file excel với sđt chuẩn
	 * */
	public Map<String, Object> getDataFromExcelFilterFunctionWithValidPhone(File tempFileOrigin,  File tempFileFilter) {
//		AlgorithmReadPhoneFromExcel algorithmReaderExcel = new AlgorithmReadPhoneFromExcel();
		AlgorithmExcelReaderUtil excelReaderUtil = new AlgorithmExcelReaderUtil();
		PhoneProcess phoneProcess = new PhoneProcess(); // Nên inject bằng @Autowired nếu PhoneProcess là Spring Bean

		//use function from iofunction
		List<ExcelObject> excelObjectListOrigin = excelReaderUtil.readExcelFileWithValidPhone(tempFileOrigin);
		List<ExcelObject> excelObjectListFilter = excelReaderUtil.readExcelFileWithValidPhone(tempFileFilter);
		
		PhoneProcess.PhoneProcessingResult result = phoneProcess.filterAndValidatePhoneData(excelObjectListOrigin, excelObjectListFilter);
		List<ExcelObject> filteredList =  result.getFilteredList();
		List<ExcelObject> errorList = result.getRemovedItems();
        String[] countStatus = result.getStatusMessages();
        
        
        Map<String, Object> excelResult = new HashMap<>();
        excelResult.put("filteredObjectList", filteredList);
        excelResult.put("errorObjectList", errorList);
        excelResult.put("countStatus", countStatus);
		//check result
//       for (ExcelObject excelObject : excelObjects) {
//           System.out.println(">>"+excelObject);
//       }
		
		return excelResult;
	}
	

	
	
	/*
	 * Đọc file excel đơn giản nói chung để lấy dữ liệu ánh xạ qua ExcelObject
	 * Bằng đường dẫn trực tiếp, ko cần lấy từ client
	 * */
	public List<ExcelObject> getDataFromExcelSimple(File file) {
		SimpleExcelReader algorithmReaderExcel = new SimpleExcelReader();
		String filePath = "D:\\Desktop\\Diary\\ExcelObject.xlsx";

		List<ExcelObject> excelObjects = algorithmReaderExcel.readFile(filePath);
		
		//check result
       for (ExcelObject excelObject : excelObjects) {
           System.out.println(">>"+excelObject);
       }
		
		return excelObjects;
	}
	
	
	
	/*
	 * Bằng file từ client
	 * */
	public static void getDataFromExcel(String filePath) {
//		AlgorithmReadPhoneFromExcel algorithmReaderExcel = new AlgorithmReadPhoneFromExcel();
		AlgorithmExcelReaderUtil excelReaderUtil = new AlgorithmExcelReaderUtil();		
		//use function from iofunction
		List<ExcelObject> excelObjects = excelReaderUtil.readExcelFile(filePath);
		
		//check result
       for (ExcelObject excelObject : excelObjects) {
           System.out.println(">>"+excelObject);
       }
	}
	
	
	/*
	 * Đọc File được lấy trực tiếp từ client
	 * */
	public List<ExcelObject> dataFromExcelFileAndSaveToProject(MultipartFile[] files) throws IllegalStateException, IOException {
//		AlgorithmReadPhoneFromExcel algorithmReaderExcel = new AlgorithmReadPhoneFromExcel();
		AlgorithmExcelReaderUtil excelReaderUtil = new AlgorithmExcelReaderUtil();		
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
            
            excelObjects = excelReaderUtil.readExcelFile(fullPath);
            
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