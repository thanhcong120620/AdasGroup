package SpringbootProject.algorithms.IOAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.PersonProfileProcessFunction;
import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.PhoneProcess;
import SpringbootProject.entity.UserEntity;
import SpringbootProject.entity.CRMEntity.DTP3FilterData;
import SpringbootProject.entity.notSaving.ExcelObject;

public class IOFunction {
	private static final String FOLDER_PATH = "D:/Desktop/My data/1.My Working/1.IVC/2.ICV-Digital/1.Develop/1.IVCDevelop/2.BackEnd/2.IVCBackEnd/AdminSystem/src/main/resources/static/image/IVC-Realtor-Image/Gmail-MKT-Image/";
	AlgorithmReaderExcelUserEntityList excelReader = new AlgorithmReaderExcelUserEntityList();
	AlgorithmExcelReaderUtil excelReaderUtil = new AlgorithmExcelReaderUtil();
	
	

//----------------------------------------EXCEL WITH ENTITY -FUNCTION----------------------------------------------------------		
	/*
	 * Get TP3 FILTER ENTITY from DTP3FilterData.xlx
	 * EXCEL OBJECT --> DTP3 FILTER ENTITY
	 * READ
	 * */
	public List<DTP3FilterData> getDtp3FilterEntityListFromExcel (MultipartFile file){
		List<DTP3FilterData> dtp3FilterDataList = new ArrayList<>();
		AlgorithmExcelReaderUtil excelReaderUtil = new AlgorithmExcelReaderUtil();
		MapperToExcelObject mapperToExcelObject = new MapperToExcelObject();
		
		List<ExcelObject> excelDataList = excelReaderUtil.readExcelFile(file);
		dtp3FilterDataList = mapperToExcelObject.ExcelObjectToDPT3FilterData(excelDataList);
		
		return dtp3FilterDataList;
	}
	
	/*
	 * Create DTP3FilterData.xlx from DTP3 FILTER ENTITY Full Data
	 * DTP3 FILTER ENTITY --> EXCEL OBJECT
	 * WRITE
	 * */
	public MultipartFile createExcelFromDtp3FilterFullData (List<DTP3FilterData> dtp3FilterDataList) throws IOException{
		List<ExcelObject> excelDataList= new ArrayList<>();
		MapperToExcelObject mapperToExcelObject = new MapperToExcelObject();
		
		excelDataList = mapperToExcelObject.DPT3FilterDataToExcelObject(dtp3FilterDataList);
		
		MultipartFile multipartFile = AlgorithmWritterExcel.writeToExcelHasHead(excelDataList, mapperToExcelObject.DPT3FilterDataExcelGetHead());
		
		return multipartFile;
	}
	
	/*
	 * Create DTP3FilterForm.xlx from DTP3 FILTER ENTITY
	 * DTP3 FILTER ENTITY --> EXCEL OBJECT
	 * WRTITE
	 * */
	public MultipartFile createlFromDtp3FilterExceForm () throws IOException{
//		List<ExcelObject> excelDataList= new ArrayList<>();
		MapperToExcelObject mapperToExcelObject = new MapperToExcelObject();
		
//		ExcelObject excelObject = mapperToExcelObject.DPT3FilterDataToExcelObjectGetHead();
//		String [] excelHeadArr = mapperToExcelObject.DPT3FilterDataExcelGetHead();
		
//		excelDataList.add(excelObject);
		
		MultipartFile multipartFile = AlgorithmWritterExcel.writeToExcelHasHead(null, mapperToExcelObject.DPT3FilterDataExcelGetHead());
		
		return multipartFile;
	}
	
//----------------------------------------OLD -FUNCTION----------------------------------------------------------	
	
	
	
	/*
	 * READ EXCEL - MERGE FUNCTION
	 * Bằng đường dẫn trực tiếp, ko cần lấy từ client
	 * */
	public List<List<ExcelObject>> getDataFromExcelConditionFilterColumnn(MultipartFile file, int indexColumn, List<String> stringInValidDataList) {
//		AlgorithmReadDataFromExcelMerge algorithmReaderExcel = new AlgorithmReadDataFromExcelMerge();
		AlgorithmExcelReaderUtil excelReaderUtil = new AlgorithmExcelReaderUtil();
	
		//Khởi tạo List Object từ File
		List<ExcelObject> originDataList = excelReaderUtil.readExcelFile(file);
		 //Khởi tạo invalidDataList rỗng để chứa các phần tử không hợp lệ
        List<ExcelObject> invalidDataList = new ArrayList<>();
		 //Khởi tạo validDataList rỗng để chứa các phần tử hợp lệ
        List<ExcelObject> validDataList = new ArrayList<>();

		//Chuyển dữ liệu cần tách sang validDataList.
		for(int i=0; i<originDataList.size(); i++) {
			boolean valid = true;
			for(int j=0; j<stringInValidDataList.size(); j++) {
				//Kiểm tra mỗi record có column chứa dự liệu trùng với list dữ liệu ko hợp lệ hay ko
				if(stringInValidDataList.get(j).equals(originDataList.get(i).getColumnByIndex(indexColumn))) {
					valid = false;
				}				
			}
			//Nếu ko hợp lệ thì add vào list invalid
			if(!valid) {
				invalidDataList.add(originDataList.get(i));
			}				
			//Nếu ko có dữ liệu ko hợp lệ thì add vào list hợp lệ.
			if(valid) {
				validDataList.add(originDataList.get(i));
			}
		}
		
	
		//check result
//		System.out.println("invalidDataList: ");
//		displayObject(invalidDataList);
//		
//		System.out.println("validDataList: ");
//		displayObject(validDataList);
		
		List<List<ExcelObject>> result = new ArrayList<>();
		result.add(validDataList);
		result.add(invalidDataList);
		
		return result;
	}	
	
	
	
	
	
	
	
	/*
	 * READ EXCEL - MERGE FUNCTION
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
	
	
	@SuppressWarnings("unchecked")
	/*
	 * READ EXCEL - FILTER FUNCTION - REMOVE PHONE DUPLICATE IN FILE
	 * Đọc file xử lý số điện thoại Bằng đường dẫn trực tiếp, ko cần lấy từ client
     * sử dụng filterAndValidatePhoneData trong phoneprocess để lọc sđt
     * sử dụng getDataFromExcelFilterFunctionWithValidPhone trong AlgorithmExcelReaderUtil để đọc file excel với sđt chuẩn
	 * */
	public Map<String, Object> getDataFromExcelFilterFunctionWithValidPhoneSingle(File tempFileFilter) {
		AlgorithmExcelReaderUtil excelReaderUtil = new AlgorithmExcelReaderUtil();

		//use function from iofunction
		List<ExcelObject> excelObjectListFilter = excelReaderUtil.readExcelFileWithValidPhone(tempFileFilter);
		PersonProfileProcessFunction personProfileProcessFunction = new PersonProfileProcessFunction();
		Map<String, Object> result = personProfileProcessFunction.phoneProcessFilterInternalDuplicatesOnly(excelObjectListFilter);
		
		
		 
		List<ExcelObject> filteredList = (List<ExcelObject>) result.get("filteredList");
		List<ExcelObject> errorList = (List<ExcelObject>) result.get("errorList");
		String[] countStatus = (String[]) result.get("countStatus");
        
        Map<String, Object> excelResult = new HashMap<>();
        excelResult.put("filteredObjectList", filteredList);
        excelResult.put("errorObjectList", errorList);
        excelResult.put("countStatus", countStatus);
		
		return excelResult;
	}
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	/*
	 * READ EXCEL - INTERGRATED FILTER FUNCTION
	 * Đọc file xử lý số điện thoại Bằng đường dẫn trực tiếp, ko cần lấy từ client
     * sử dụng filterAndValidatePhoneData trong phoneprocess để lọc sđt
     * sử dụng getDataFromExcelFilterFunctionWithValidPhone trong AlgorithmReadPhoneFromExcel để đọc file excel với sđt chuẩn
	 * */
	public Map<String, Object> getDataFromExcelFilterFunctionWithValidPhone(File tempFileOrigin,  File tempFileFilter) {
//		AlgorithmReadPhoneFromExcel algorithmReaderExcel = new AlgorithmReadPhoneFromExcel();
		AlgorithmExcelReaderUtil excelReaderUtil = new AlgorithmExcelReaderUtil();

		//use function from iofunction
		List<ExcelObject> excelObjectListOrigin = excelReaderUtil.readExcelFileWithValidPhone(tempFileOrigin);
		List<ExcelObject> excelObjectListFilter = excelReaderUtil.readExcelFileWithValidPhone(tempFileFilter);
		
        System.out.println("tempFileOrigin: "+tempFileOrigin);
        System.out.println("tempFileFilter: "+tempFileFilter);
        
//        for (ExcelObject object : excelObjectListOrigin) {
//        	System.out.println("excelObjectListOrigin: " + object.toString());
//        }
        

		
		PersonProfileProcessFunction personProfileProcessFunction = new PersonProfileProcessFunction();
		Map<String, Object> result = personProfileProcessFunction.phoneProcessFilterAndValidatePhoneDataIntergrated(excelObjectListOrigin,excelObjectListFilter);
		
		
		List<ExcelObject> filteredList = (List<ExcelObject>) result.get("filteredList");
		List<ExcelObject> errorList = (List<ExcelObject>) result.get("errorList");
		String[] countStatus = (String[]) result.get("countStatus");
        
        
        Map<String, Object> excelResult = new HashMap<>();
        excelResult.put("filteredObjectList", filteredList);
        excelResult.put("errorObjectList", errorList);
        excelResult.put("countStatus", countStatus);
		
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
		
//		//check result
//       for (ExcelObject excelObject : excelObjects) {
//           System.out.println(">>"+excelObject);
//       }
		
		return excelObjects;
	}
	
	/*
	 * Bằng file từ client với đầu vào là String Path
	 * */
	public static void getDataFromExcelWithPath(String filePath) {
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
	 * Bằng file từ client với đầu vào là MultipartFile file
	 * */
	public List<ExcelObject> getDataFromExcelWithMultipartFile(MultipartFile file) {
//		AlgorithmReadPhoneFromExcel algorithmReaderExcel = new AlgorithmReadPhoneFromExcel();
		AlgorithmExcelReaderUtil excelReaderUtil = new AlgorithmExcelReaderUtil();		
		//use function from iofunction
		List<ExcelObject> excelObjects = excelReaderUtil.readExcelFile(file);
		
		//check result
//       for (ExcelObject excelObject : excelObjects) {
//           System.out.println(">>"+excelObject);
//       }
       
       return excelObjects;
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
    
    
//=================================HELPER==================================
    
    
    public void displayObject (List<ExcelObject> input) {
      for (ExcelObject output : input) {
      System.out.println(">>"+output);
      }
    }

}