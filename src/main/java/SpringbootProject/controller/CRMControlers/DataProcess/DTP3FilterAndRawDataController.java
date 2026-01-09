package SpringbootProject.controller.CRMControlers.DataProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import SpringbootProject.algorithms.IOAlgorithm.IOFunction;
import SpringbootProject.entity.UserEntity;
import SpringbootProject.entity.CRMEntity.DTP1CRMEntity;
import SpringbootProject.entity.CRMEntity.DTP3FilterData;
import SpringbootProject.entity.enums.DataType;
import SpringbootProject.entity.notSaving.ExcelObject;
import SpringbootProject.service.IDTP1CRMEntity;
import SpringbootProject.service.IDTP3FilterDataEntity;

@Controller
public class DTP3FilterAndRawDataController {
	
	public static List<String> DTP3FilterControllerMessagesList = new ArrayList<>();

	
    // Logger để ghi lại thông tin và lỗi
    private static final Logger logger = LoggerFactory.getLogger(DataExcelProcessController.class);
    
//    private static MultipartFile dtp3FilterFormExcelFileResponse;
	
	@Autowired
	private IDTP3FilterDataEntity Dtp3FilterDataServices;
	 /*
     * TRUY CẬP VÀO THYMLEAF
     * Sau này tạo thêm những phần hiển thị theo bộ lọc
     * */
    @GetMapping("/data-dtp3-filter-and-raw-pannel")
    public String index(Model model) {
        // Có thể thêm logic xóa file cũ hoặc reset trạng thái ở đây nếu cần
        // Ví dụ: excelFileResponse = null; excelFileError = null;
    	List<DTP3FilterData> DTP3FilterDataList = Dtp3FilterDataServices.findAllDtp3FilterData();
    	
//    	for (DTP3FilterData DTP3Filter : DTP1CRMList) {
//    		System.out.println(">>> "+DTP3Filter.toString());
//    	}
    	model.addAttribute("DTP3FilterDataList", DTP3FilterDataList);
    	model.addAttribute("DTP3FilterControllerMessagesList", DTP3FilterControllerMessagesList);
        return "app/IVC-CRM/IVC-CRM-View/IVC-CRM-DataProcess/DTP3Filter&RawData";
    }

 //=========================================THAO TÁC với I/O ===========================================================

    
    /*
     * POSTING ACTION - UPLOAD LẤY DỮ LIỆU CẦN UPDATE TỪ EXCEL.
     * Các trường dữ liệu ở database sẽ được cập mới theo dữ liệu tương ứng ở excel
     * */    
    @PostMapping("/uploadAndUpdateDtp3FilterOldDataByPhone")
    public String handleFileUploadAndUpdateDtp3FilterOldDataByPhone (@RequestParam("excelFileUpdateDtp3FilterOldDataByPhone") MultipartFile file, // Tên khớp với input file
            RedirectAttributes redirectAttributes, Model model) { // Bỏ throws nếu xử lý exception bên trong
		
        DTP3FilterControllerMessagesList.clear();
        DTP3FilterControllerMessagesList.add("Các số điện thoại không tồn tại ở kho dữ liệu: \n");
        
        // --- Đọc file và lấy thông tin ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
        List<DTP3FilterData> excelObjectInputList = ioFunction.getDtp3FilterEntityListFromExcel(file);
        
        //Update data to database
        for(DTP3FilterData dTP3FilterData : excelObjectInputList) {
        	String phoneUpdate = Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dTP3FilterData);
        	if(!phoneUpdate.contentEquals("Cập nhật thành công")) {
        		DTP3FilterControllerMessagesList.add(phoneUpdate.concat("; "));
        	}
        }
        
        return "redirect:/data-dtp3-filter-and-raw-pannel";
    }    
    
    
    
    
    /*
     * POSTING ACTION - UPLOAD LẤY DỮ LIỆU TỪ EXCEL CẦN XÓA.
     * */
    @PostMapping("/uploadAndDeleteDtp3FilterDataByPhone")
    public String handleFileUploadAndDeleteDtp3FilterDataByPhone (@RequestParam("excelFileDeleteDtp3FilterDataByPhone") MultipartFile file, // Tên khớp với input file
            RedirectAttributes redirectAttributes, Model model) { // Bỏ throws nếu xử lý exception bên trong
		
    	DTP3FilterControllerMessagesList.clear();
        // --- Đọc file và lấy thông tin ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
        List<DTP3FilterData> excelObjectInputList = ioFunction.getDtp3FilterEntityListFromExcel(file);
        int countData = 0;
        //Save new data to database
        for(DTP3FilterData dTP3FilterData : excelObjectInputList) {
        	int deletedCount = Dtp3FilterDataServices.deleteDTP3FilterDataByPhone1(dTP3FilterData.getPhoneNumber1());
        	System.out.println(">>> deletedCount: "+deletedCount);
        countData = countData + deletedCount;
        }
        
        DTP3FilterControllerMessagesList.add("Tổng có: "+ countData+" bị xóa");
        
        return "redirect:/data-dtp3-filter-and-raw-pannel";
    }    
    
    /*
     * POSTING ACTION - UPLOAD LẤY DỮ LIỆU TỪ EXCEL ĐỂ UPDATE/CREATE.
     * */
    @PostMapping("/uploadAndUpdateDtp3FilterData")
    public String handleFileUploadAndUpdateDtp3FilterData (@RequestParam("excelFileCreateNewDtp3FilterData") MultipartFile file, // Tên khớp với input file
            RedirectAttributes redirectAttributes, Model model) { // Bỏ throws nếu xử lý exception bên trong
		
		
        // --- Đọc file và lấy thông tin ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
        List<DTP3FilterData> excelObjectInputList = ioFunction.getDtp3FilterEntityListFromExcel(file);
        
        //Save new data to database
        for(DTP3FilterData dTP3FilterData : excelObjectInputList) {
        	Dtp3FilterDataServices.dataDTP3FilterCreaterAndUpdate(dTP3FilterData);
        }
        
        return "redirect:/data-dtp3-filter-and-raw-pannel";
    }    
    
    
    /*
     * Update thêm các dữ liệu ko hợp lệ
     * DTP3FILTER EXCEL: Duplicate phone  	
     * Phương thức tải xuống file Excel 
     * */
    @GetMapping("/getDtp3FilterDuplicatePhoneExcelFile")
    public ResponseEntity<ByteArrayResource> downloadExcelFileDTP3FilterDataDuplicatePhone() throws IOException {
    	// --- Đọc file và lấy thông tin ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
        
        List<DTP3FilterData> dtp3FilterDataList = Dtp3FilterDataServices.findAllByPhoneDuplicate();
			System.out.println("Run controler - size: "+dtp3FilterDataList.size());
    	MultipartFile dtp3FilterDuplicatePhoneResponse = ioFunction.createExcelFromDtp3FilterFullData(dtp3FilterDataList);
    	
        
        // --- Ghi kết quả ra MultipartFile (lưu vào biến static - CẨN THẬN THREAD SAFETY) ---
        try {
        	dtp3FilterDuplicatePhoneResponse = ioFunction.createExcelFromDtp3FilterFullData(dtp3FilterDataList);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // --- CẢNH BÁO: Phụ thuộc vào biến static excelFileResponse ---
        if (dtp3FilterDuplicatePhoneResponse == null) {
             logger.warn("Yêu cầu tải file response nhưng excelFileResponse là null.");
             // Có thể trả về lỗi 404 hoặc thông báo khác
             return ResponseEntity.notFound().build(); // Hoặc trả về trang lỗi
        }

        ByteArrayResource resource = new ByteArrayResource(dtp3FilterDuplicatePhoneResponse.getBytes());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Danh sach du lieu trung so dien thoai.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(dtp3FilterDuplicatePhoneResponse.getSize())
                .body(resource);
    }
    
    
    /*
     * DTP3FILTER EXCEL FORM 	
     * Phương thức tải xuống file Excel 
     * */
    @GetMapping("/getDtp3FilterFormExcelFile")
    public ResponseEntity<ByteArrayResource> downloadExcelFileDTP3FilterDataForm() throws IOException {
    	// --- Đọc file và lấy thông tin ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
    	
    	MultipartFile dtp3FilterFormExcelFileResponse = ioFunction.createlFromDtp3FilterExceForm();;
    	
        
        // --- Ghi kết quả ra MultipartFile (lưu vào biến static - CẨN THẬN THREAD SAFETY) ---
        try {
        	dtp3FilterFormExcelFileResponse = ioFunction.createlFromDtp3FilterExceForm();
        	System.out.println("3");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // --- CẢNH BÁO: Phụ thuộc vào biến static excelFileResponse ---
        if (dtp3FilterFormExcelFileResponse == null) {
             logger.warn("Yêu cầu tải file response nhưng excelFileResponse là null.");
             // Có thể trả về lỗi 404 hoặc thông báo khác
             return ResponseEntity.notFound().build(); // Hoặc trả về trang lỗi
        }

        ByteArrayResource resource = new ByteArrayResource(dtp3FilterFormExcelFileResponse.getBytes());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dtp3-form.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(dtp3FilterFormExcelFileResponse.getSize())
                .body(resource);
    }
   
    
    
    /*
     * DTP3FILTER EXCEL Full Data 	
     * Phương thức tải xuống file Excel 
     * */
    @GetMapping("/getDtp3FilterFullDataExcelFile")
    public ResponseEntity<ByteArrayResource> downloadExcelFileDTP3FilterFullData() throws IOException {
    	
    	List<DTP3FilterData> dtp3FilterDataResponse = Dtp3FilterDataServices.findAllDtp3FilterData();
    	// --- Đọc file và lấy thông tin ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
    	
    	MultipartFile dtp3FilterFulDataExcelFileResponse = ioFunction.createExcelFromDtp3FilterFullData(dtp3FilterDataResponse);;
    	
        
        // --- Ghi kết quả ra MultipartFile (lưu vào biến static - CẨN THẬN THREAD SAFETY) ---
        try {
        	dtp3FilterFulDataExcelFileResponse = ioFunction.createExcelFromDtp3FilterFullData(dtp3FilterDataResponse);
        	System.out.println("3");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // --- CẢNH BÁO: Phụ thuộc vào biến static excelFileResponse ---
        if (dtp3FilterFulDataExcelFileResponse == null) {
             logger.warn("Yêu cầu tải file response nhưng excelFileResponse là null.");
             // Có thể trả về lỗi 404 hoặc thông báo khác
             return ResponseEntity.notFound().build(); // Hoặc trả về trang lỗi
        }

        ByteArrayResource resource = new ByteArrayResource(dtp3FilterFulDataExcelFileResponse.getBytes());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dtp3-full-data.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(dtp3FilterFulDataExcelFileResponse.getSize())
                .body(resource);
    }
    
    
    
    
    
    
    

 //=========================================THAO TÁC SORT & FIND CƠ BÁN =========================================================== 
    
    /*
     * Lấy dữ liệu theo data type
     * */
    @GetMapping("/getTypeData")
    public String getDataByTypeData(Model model, 
			@RequestParam(value = "dateType", required = false) String dataTypeRequest) {
    	
    	DataType dateType = DataType.valueOf(dataTypeRequest);

        // Ví dụ: excelFileResponse = null; excelFileError = null;
    	List<DTP3FilterData> DTP1CRMList = Dtp3FilterDataServices.findByDataType(dateType);
    	
    	for (DTP3FilterData DTP3Filter : DTP1CRMList) {

    		System.out.println(">>> "+DTP3Filter.toString());
    		
    	}
    	model.addAttribute("DTP1CRMListResponse", DTP1CRMList);
        return "app/IVC-CRM/IVC-CRM-View/IVC-CRM-DataProcess/DTP3Filter&RawData";
    }
	
	/*
	 * Find By 1 Phone
	 * Dùng khi bạn muốn xóa một tài nguyên hoặc đối tượng khỏi hệ thống.
	 * */
	@GetMapping("/crud-find-by-phone-dtp3-filter-and-raw")
	public ResponseEntity<String> findDTP3FilterDataByPhone(@RequestBody String phoneNumber1) {
		DTP3FilterData dtp3FilterData = Dtp3FilterDataServices.findByphoneNumber1(phoneNumber1);
		System.out.println("Phone: "+ dtp3FilterData + "from controller");
		return ResponseEntity.ok("Find DTP3FilterData with Phone: " + dtp3FilterData);
	}
	
	/*
	 * Find By nhiều Phone
	 * Dùng khi bạn muốn xóa một tài nguyên hoặc đối tượng khỏi hệ thống.
	 * */
	@GetMapping("/crud-find-by-phones-dtp3-filter-and-raw")
	public ResponseEntity<String> findDTP3FilterDataByPhones(@RequestBody String[] phoneNumber1s) {

		 List<DTP3FilterData> dtp3FilterDataResponse = new ArrayList<DTP3FilterData>();
		for(int i = 0; i < phoneNumber1s.length; i++) {
			DTP3FilterData dtp3FilterData = Dtp3FilterDataServices.findByphoneNumber1(phoneNumber1s[i]);
			dtp3FilterDataResponse.add(dtp3FilterData);
		}
		 
		return ResponseEntity.ok("Find DTP3FilterData with Phone: " + dtp3FilterDataResponse);
	}
    
    
//=========================================CRUD CƠ BÁN ===========================================================    
    
    /*
	 * Create & Updata
	 * Dùng khi bạn muốn cập nhật tài nguyên hiện có trên server. 
	 * PUT thường thay thế toàn bộ tài nguyên (hoặc cập nhật tất cả thông tin của đối tượng).
	 * */
	@PutMapping("/crud-update-dtp3-filter-and-raw")
	public ResponseEntity<String> updateDTP3FilterData(@RequestBody DTP3FilterData dtp3FilterDataClient) {
		Dtp3FilterDataServices.dataDTP3FilterCreaterAndUpdate(dtp3FilterDataClient);
		DTP3FilterData dtp3FilterData = Dtp3FilterDataServices.findById(dtp3FilterDataClient.getId());
		
		return ResponseEntity.ok("Đã cập nhật dtp3FilterData: " + dtp3FilterData);
	}
	
	
	
	/*
	 * Chạy trên vue
	 * Dùng khi bạn muốn tạo mới một đối tượng hoặc tài nguyên trên server (như thêm mới một người dùng, một bài viết
	 * */
	@PostMapping("/crud-create-dtp3-filter-and-raw")
	public ResponseEntity<String> createDTP3FilterData(@RequestBody DTP3FilterData dtp3FilterDataClient) {
		Dtp3FilterDataServices.dataDTP3FilterCreaterAndUpdate(dtp3FilterDataClient);
		DTP3FilterData dtp3FilterData = Dtp3FilterDataServices.findById(dtp3FilterDataClient.getId());
	
		return ResponseEntity.ok("Đã tạo mới user: " + dtp3FilterData);
	}
	
	
	/*Get list full data
	 * Dùng khi bạn muốn trả về dữ liệu từ server, ví dụ như khi truy vấn dữ liệu từ cơ sở dữ liệu hoặc khi lấy thông tin.
	 * */
	@GetMapping("/crud-show-dtp3-filter-and-raw")
	public ResponseEntity<List<DTP3FilterData>> showDTP3FilterDataList() {
	    List<DTP3FilterData> dtp3FilterDataResponse = Dtp3FilterDataServices.findAllDtp3FilterData();
	    return ResponseEntity.ok(dtp3FilterDataResponse); // Trả về danh sách người dùng dưới dạng JSON
	}
	
	
	/*
	 * Chạy trên vue
	 * Dùng khi bạn muốn xóa một tài nguyên hoặc đối tượng khỏi hệ thống.
	 * */
	@DeleteMapping("/crud-delete-dtp3-filter-and-raw")
	public ResponseEntity<String> deleteDTP3FilterData(@RequestBody Long dtp3FilterDataId) {

		Dtp3FilterDataServices.deleteDTP3FilterData(dtp3FilterDataId);
		System.out.println("Id: "+ dtp3FilterDataId + "from controller");

		

		return ResponseEntity.ok("Deleted DTP3FilterData with ID: " + dtp3FilterDataId);
	}
	
	
	/*
	 * Find By 1 Id
	 * Dùng khi bạn muốn xóa một tài nguyên hoặc đối tượng khỏi hệ thống.
	 * */
	@GetMapping("/crud-find-by-id-dtp3-filter-and-raw")
	public ResponseEntity<String> findDTP3FilterDataById(@RequestBody Long Id) {
		DTP3FilterData dtp3FilterData = Dtp3FilterDataServices.findById(Id);

		return ResponseEntity.ok("Find DTP3FilterData with ID: " + dtp3FilterData);
	}
	
	
//----------------------------------------------------------------------------------------------------------------


  //===================================================================================================================
}
