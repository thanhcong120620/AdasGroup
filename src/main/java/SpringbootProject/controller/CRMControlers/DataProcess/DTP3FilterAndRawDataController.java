package SpringbootProject.controller.CRMControlers.DataProcess;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import FileUtil.ContactUtils;
import FileUtil.ContactUtils.ContactResult;
import FileUtil.StringProcess;
import SpringbootProject.algorithms.IOAlgorithm.IOFunction;
import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.GenderProcess;
import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.NameProcess;
import SpringbootProject.dto.dataProcess.DTP3SearchRequest;
import SpringbootProject.entity.CRMEntity.DTP3FilterData;
import SpringbootProject.entity.enums.DataType;
import SpringbootProject.entity.enums.Gender;
import SpringbootProject.entity.enums.NextAction;
import SpringbootProject.entity.enums.Salutation;
import SpringbootProject.service.IDTP3FilterDataEntity;

@Controller
public class DTP3FilterAndRawDataController {
	
//========================================== STATIC DECLARE =====================================	
	public static List<String> DTP3FilterControllerErrorList = new ArrayList<>();
	public static List<String> DTP3FilterControllerMessagesList = new ArrayList<>();
    private static List<DTP3FilterData> dtp3FilterDataStaticList = null;
	
    // Logger để ghi lại thông tin và lỗi
    private static final Logger logger = LoggerFactory.getLogger(DTP3FilterAndRawDataController.class);

  
//========================================== CONTROLLER INIT =====================================
    
	@Autowired
	private IDTP3FilterDataEntity Dtp3FilterDataServices;
	

	 /*
     * TRUY CẬP VÀO THYMLEAF
     * Sau này tạo thêm những phần hiển thị theo bộ lọc
     * */
    @GetMapping("/dtp3-filter-data-view")
    public String index(Model model) {
       
        return "redirect:/data-dtp3-filter-and-raw-pannel";
    }
	

 //========================================== FUNCTION ON UI =====================================
    
    @GetMapping("/data-dtp3-filter-and-raw-pannel")
    public String showFilterPage(
            @ModelAttribute("searchRequest") DTP3SearchRequest request,
            @PageableDefault(size = 20) Pageable pageable, // Mặc định 10 bản ghi/trang
            Model model) {
		
    	logger.info(">>> CONTROLLER: Tiếp nhận yêu cầu lọc. Page: {}", pageable.getPageNumber());
        Page<DTP3FilterData> page = Dtp3FilterDataServices.filterData(request, pageable); 
//        List<DTP3FilterData> DTP3FilterDataList = Dtp3FilterDataServices.getFilterListOnly(request, pageable);//lấy theo phân trang
        List<DTP3FilterData> DTP3FilterDataList = Dtp3FilterDataServices.getAllMatchesWithoutPagination(request); // lấy full


        List<String> resultFollowList = new ArrayList<>();
        List<String> accountFollowList = new ArrayList<>();
        List<String> consultDiaryList = new ArrayList<>();
        for(DTP3FilterData dtpFilterData : DTP3FilterDataList) {
        	resultFollowList.add(dtpFilterData.getResultFollow());
        	accountFollowList.add(dtpFilterData.getAccountFollow());
        	consultDiaryList.add(dtpFilterData.getConsultDiary());
        }
        resultFollowList = StringProcess.removeDuplicates(resultFollowList);
        accountFollowList = StringProcess.removeDuplicates(accountFollowList);
        consultDiaryList = StringProcess.removeDuplicates(consultDiaryList);
        
        
        dtp3FilterDataStaticList = DTP3FilterDataList;
        
        // Logic tính toán 5 trang hiển thị
        int current = page.getNumber(); // Trang hiện tại (0-indexed)
        int total = page.getTotalPages();
        
        int start = Math.max(0, current - 2); // Hiển thị 2 trang phía trước
        int end = Math.min(start + 4, total - 1); // Đảm bảo tổng cộng khoảng 5 trang
        
        // Điều chỉnh lại nếu ở những trang cuối (để luôn đủ 5 nút nếu có thể)
        if (end - start < 4) {
            start = Math.max(0, end - 4);
        }
		
        Map<String, List<String>> messageAndError = countValueOfFieldDtp3Filter();
        List<String> dataReportMessage = messageAndError.get("messageCountValueOfFieldDtp3");
        if(dataReportMessage!=null) {
        	DTP3FilterControllerMessagesList.add("");
        	DTP3FilterControllerMessagesList.add("\n\n--------------------Time at: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))+" --------------------"); 
        }
        for(String countData : dataReportMessage) {
        	DTP3FilterControllerMessagesList.add(countData);
        }

        List<String> dataReportError = messageAndError.get("errorCountValueOfFieldDtp3");
        if(dataReportError!=null) {
        	DTP3FilterControllerErrorList.add("\n\n--------------------Time at: "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))+" --------------------");
        }
        for(String countData : dataReportError) {
        	DTP3FilterControllerErrorList.add(countData);
        }
        
        model.addAttribute("page", page);
        model.addAttribute("startPage", start);
        model.addAttribute("endPage", end);

        model.addAttribute("allDataTypes", DataType.getAllLabels());
        model.addAttribute("allGenders", Gender.getAllLabels());
        model.addAttribute("allSalutations", Salutation.getAllLabels());
        model.addAttribute("allNextActions", NextAction.getAllLabels());
        model.addAttribute("allResultFollows", resultFollowList);
        model.addAttribute("allAccountFollows", accountFollowList);
        model.addAttribute("allConsultDiaries", consultDiaryList);
        
//        model.addAttribute("DTP3FilterDataList", DTP3FilterDataList);
        model.addAttribute("DTP3FilterDataList", DTP3FilterDataList);
    	model.addAttribute("DTP3FilterControllerMessagesList", DTP3FilterControllerMessagesList);
    	model.addAttribute("DTP3FilterControllerErrorList", DTP3FilterControllerErrorList);
    	
        return "app/IVC-CRM/IVC-CRM-View/IVC-CRM-DataProcess/DTP3FilterData";
    }
    
    @GetMapping("/list")
    public String listData(
            @ModelAttribute("searchRequest") DTP3SearchRequest searchRequest,
            @PageableDefault(size = 20, sort = "id") Pageable pageable,
            Model model) {
        
        Page<DTP3FilterData> pageResult = Dtp3FilterDataServices.filterData(searchRequest, pageable);
        
        model.addAttribute("page", pageResult);
        model.addAttribute("dataTypes", DataType.getAllLabels());
        model.addAttribute("genders", Gender.getAllLabels());
        model.addAttribute("nextActions", NextAction.getAllLabels());
        
        return "app/IVC-CRM/IVC-CRM-View/IVC-CRM-DataProcess/DTP3FilterData";
    }
	
    /*
     * Cập nhật các giá trị ko phải là enum
     * Các field: data-type, next-action.
     * Client trả về dạng giá trị chuỗi, chuyển qua value của enum và lưu
     * Lấy value và field cần update từ thymleaf
     * Trả về DTP3SearchRequest
     * 
     * */
	@GetMapping("/update-dtp3-data-by-one-enum-field")
	public String updateDtp3DataByOneEnumField(
			@ModelAttribute("searchRequest") DTP3SearchRequest request,
	        @RequestParam (value = "enumField", required = true) String enumField,
//	        @RequestParam(value = "enumValue", required = true) String enumValue,
	        Model model) {
		
		List<DTP3FilterData> DTP3FilterDataListRequest = dtp3FilterDataStaticList;
		List<DTP3FilterData> DTP3FilterDataListRessponse = new ArrayList<DTP3FilterData>();
		List<String> updatedPhoneList = new ArrayList<String>();
		
		for(DTP3FilterData dtp3FilterData : DTP3FilterDataListRequest) {
			
			switch (enumField) {
            case "data-type":
            	dtp3FilterData.setDataType(DataType.fromLabel(request.getDataType()));
            	Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dtp3FilterData);
            	updatedPhoneList.add(dtp3FilterData.getPhoneNumber1());
                break;
            case "next-action":
            	dtp3FilterData.setNextAction(NextAction.fromLabel(request.getNextAction()));
    			Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dtp3FilterData);
            	updatedPhoneList.add(dtp3FilterData.getPhoneNumber1());
//    			DTP3FilterControllerMessagesList.add("Đã cập nhật: "+fieldToUpdate+" với "+valueToUpdate);
//    			DTP3FilterControllerMessagesList.add(Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dtp3FilterData));
                break;
            case "date-follow":
//            	System.out.println("Run data-type: "+dtp3FilterData.getPhoneNumber1() + enumField);
//            	System.out.println("Set: "+request.getDateFollow());
            	dtp3FilterData.setNextFollowDate(request.getDateFollow());
    			Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dtp3FilterData);
            	updatedPhoneList.add(dtp3FilterData.getPhoneNumber1());
//    			DTP3FilterControllerMessagesList.add("Đã cập nhật: "+fieldToUpdate+" với "+valueToUpdate);
//    			DTP3FilterControllerMessagesList.add(Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dtp3FilterData));
                break;
            default:
            	DTP3FilterControllerErrorList.add("Cập nhật thất bại: "+enumField);
        }
			
			
		}
		
		
		for(String updatedPhone : updatedPhoneList) {
			List<DTP3FilterData> updatedPhoneListFromDB = Dtp3FilterDataServices.findAllByPhoneNumber1(updatedPhone);
			for(DTP3FilterData dtp3FilterData : updatedPhoneListFromDB) {
				DTP3FilterDataListRessponse.add(dtp3FilterData);
			}
			DTP3FilterControllerMessagesList.add(updatedPhone);
		}
		
		//ko hiển thị ngược lại data đang lọc, mà hiển thị kết quả những phone sau khi đã đc lọc
		model.addAttribute("DTP3FilterDataList", DTP3FilterDataListRessponse);
    	model.addAttribute("DTP3FilterControllerMessagesList", DTP3FilterControllerMessagesList);
    	model.addAttribute("DTP3FilterControllerMessagesList", DTP3FilterControllerErrorList);
    	return "redirect:/data-dtp3-filter-and-raw-pannel";
	}
    
    
    /*
     * Cập nhật các giá trị ko phải là enum
     * Các field: resultFollow, accountFollow, zaloName, fullName1, consultDiary
     * Lấy value và field cần update từ thymleaf
     * Trả về DTP3SearchRequest
     * 
     * */
	@GetMapping("/update-dtp3-data-by-one-field")
	public String updateDtp3DataByOneField(
	        @RequestParam (value = "valueToUpdate", required = true) String valueToUpdate,
	        @RequestParam(value = "fieldToUpdate", required = true) String fieldToUpdate,
	        Model model) {

		List<DTP3FilterData> DTP3FilterDataListRequest = dtp3FilterDataStaticList;
		List<DTP3FilterData> DTP3FilterDataListRessponse = new ArrayList<DTP3FilterData>();
		List<String> updatedPhoneList = new ArrayList<String>();
		
		for(DTP3FilterData dtp3FilterData : DTP3FilterDataListRequest) {
			
			switch (fieldToUpdate) {
            case "resultFollow":
            	dtp3FilterData.setResultFollow(StringProcess.mergeUnique(dtp3FilterData.getResultFollow(),valueToUpdate));
            	Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dtp3FilterData);
            	updatedPhoneList.add(dtp3FilterData.getPhoneNumber1());
                break;
            case "accountFollow":
            	dtp3FilterData.setAccountFollow(valueToUpdate);
    			Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dtp3FilterData);
            	updatedPhoneList.add(dtp3FilterData.getPhoneNumber1());
//    			DTP3FilterControllerMessagesList.add("Đã cập nhật: "+fieldToUpdate+" với "+valueToUpdate);
//    			DTP3FilterControllerMessagesList.add(Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dtp3FilterData));
                break;
            case "zaloName":
            	dtp3FilterData.setZaloName(valueToUpdate);
            	updatedPhoneList.add(dtp3FilterData.getPhoneNumber1());
//    			DTP3FilterControllerMessagesList.add("Đã cập nhật: "+fieldToUpdate+" với "+valueToUpdate);
//    			DTP3FilterControllerMessagesList.add(Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dtp3FilterData));
                break;
            case "fullName1":
            	dtp3FilterData.setFullName1(valueToUpdate);
            	Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dtp3FilterData);
            	updatedPhoneList.add(dtp3FilterData.getPhoneNumber1());
//    			DTP3FilterControllerMessagesList.add("Đã cập nhật: "+fieldToUpdate+" với "+valueToUpdate);
//    			DTP3FilterControllerMessagesList.add(Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dtp3FilterData));
                break;
            case "consultDiary":
            	dtp3FilterData.setConsultDiary(StringProcess.mergeUnique(dtp3FilterData.getConsultDiary(),valueToUpdate));
            	Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dtp3FilterData);
            	updatedPhoneList.add(dtp3FilterData.getPhoneNumber1());
//    			DTP3FilterControllerMessagesList.add("Đã cập nhật: "+fieldToUpdate+" với "+valueToUpdate);
//    			DTP3FilterControllerMessagesList.add(Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dtp3FilterData));
                break;
            default:
            	DTP3FilterControllerMessagesList.add("Cập nhật thất bại: "+fieldToUpdate+" và "+valueToUpdate);
        }
			
			
		}
		
		DTP3FilterControllerMessagesList.add("Danh sách các sđt được cập nhật: "); 
		
		for(String updatedPhone : updatedPhoneList) {
			List<DTP3FilterData> updatedPhoneListFromDB = Dtp3FilterDataServices.findAllByPhoneNumber1(updatedPhone);
			for(DTP3FilterData dtp3FilterData : updatedPhoneListFromDB) {
				DTP3FilterDataListRessponse.add(dtp3FilterData);
			}
			DTP3FilterControllerMessagesList.add(updatedPhone);
		}
		
		
	   
		//ko hiển thị ngược lại data đang lọc, mà hiển thị kết quả những phone sau khi đã đc lọc
//		model.addAttribute("DTP3FilterDataList", DTP3FilterDataListRessponse);
    	model.addAttribute("DTP3FilterControllerMessagesList", DTP3FilterControllerMessagesList);
    	model.addAttribute("DTP3FilterControllerMessagesList", DTP3FilterControllerErrorList);
    	return "redirect:/data-dtp3-filter-and-raw-pannel";
	}

	
//========================================= AKABIZ FUNCTION ===========================================================	
	
	/*
	 * Chạy kết bạn
     * Akabiz Campaign Data	
     * Phương thức tải xuống file Excel 
     * Phương thức nàyko lấy salutation để set dữ liệu
     * */
    @GetMapping("/getAkabizCampaignData-no-Salutation")
    public ResponseEntity<ByteArrayResource> downloadExcelFileAkabizCampaignDataNoSalutation() throws IOException {
    	
    	String nameFile = "Akabiz-Campaign-Data-no-salutation.xlsx";
    	
    	
    	// --- Đọc file và lấy thông tin ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
//        List<DTP3FilterData> dTP3FilterData = Dtp3FilterDataServices.findAllDtp3FilterData();
        List<DTP3FilterData> dTP3FilterData = dtp3FilterDataStaticList;
        
    	MultipartFile akabizCampaignDataFileResponse = ioFunction.createAkabizExcelFromDtp3FilterDataNoSalutation(dTP3FilterData);
    	
        
        // --- Ghi kết quả ra MultipartFile (lưu vào biến static - CẨN THẬN THREAD SAFETY) ---
        try {
        	akabizCampaignDataFileResponse = ioFunction.createAkabizExcelFromDtp3FilterDataNoSalutation(dTP3FilterData);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // --- CẢNH BÁO: Phụ thuộc vào biến static excelFileResponse ---
        if (akabizCampaignDataFileResponse == null) {
             logger.warn("Yêu cầu tải file response nhưng excelFileResponse là null.");
             // Có thể trả về lỗi 404 hoặc thông báo khác
             return ResponseEntity.notFound().build(); // Hoặc trả về trang lỗi
        }

        ByteArrayResource resource = new ByteArrayResource(akabizCampaignDataFileResponse.getBytes());

        String headerValues = "attachment; filename=".concat(nameFile);        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(akabizCampaignDataFileResponse.getSize())
                .body(resource);
    }
	
	
	
	/*
	 * Chạy tin nhắn
     * Akabiz Campaign Data	
     * Phương thức tải xuống file Excel 
     * Phương thức này sẽ lấy cả salutation để set dữ liệu
     * */
    @GetMapping("/getAkabizCampaignData")
    public ResponseEntity<ByteArrayResource> downloadExcelFileAkabizCampaignData() throws IOException {
    	
    	String nameFile = "Akabiz-Campaign-Data.xlsx";
    	
    	
    	// --- Đọc file và lấy thông tin ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
//        List<DTP3FilterData> dTP3FilterData = Dtp3FilterDataServices.findAllDtp3FilterData();
        List<DTP3FilterData> dTP3FilterData = dtp3FilterDataStaticList;
        
    	MultipartFile akabizCampaignDataFileResponse = ioFunction.createAkabizExcelFromDtp3FilterDataHasSalutation(dTP3FilterData);
    	
        
        // --- Ghi kết quả ra MultipartFile (lưu vào biến static - CẨN THẬN THREAD SAFETY) ---
        try {
        	akabizCampaignDataFileResponse = ioFunction.createAkabizExcelFromDtp3FilterDataHasSalutation(dTP3FilterData);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // --- CẢNH BÁO: Phụ thuộc vào biến static excelFileResponse ---
        if (akabizCampaignDataFileResponse == null) {
             logger.warn("Yêu cầu tải file response nhưng excelFileResponse là null.");
             // Có thể trả về lỗi 404 hoặc thông báo khác
             return ResponseEntity.notFound().build(); // Hoặc trả về trang lỗi
        }

        ByteArrayResource resource = new ByteArrayResource(akabizCampaignDataFileResponse.getBytes());

        String headerValues = "attachment; filename=".concat(nameFile);        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(akabizCampaignDataFileResponse.getSize())
                .body(resource);
    }
    
	/*
	 * Akabiz Campaign Result	
     * Lấy kết quả chiến dịch --> update vào data
     * Phương thức upload file Excel 
     * Phương thức này bao gồm lấy kết quả chạy tin nhắn và cả kết bạn, update thông tin khách hàng và result follow
     * */
    @PostMapping("/uploadAndUpdateAkabizResultToDTP3")
    public String handleFileUploadAndUpdateAkabizResultToDTP3 (@RequestParam("excelFileUpdateAkabizResultToDTP3") MultipartFile file, // Tên khớp với input file
            RedirectAttributes redirectAttributes, Model model) { // Bỏ throws nếu xử lý exception bên trong
		
        DTP3FilterControllerMessagesList.clear();
        DTP3FilterControllerMessagesList.add("Các số điện thoại không tồn tại ở kho dữ liệu: \n");
        
        // --- Đọc file và lấy thông tin ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
        List<DTP3FilterData> excelObjectInputList = ioFunction.getDtp3FilterEntityListFromAkabizExcel(file);

        
        //Update data to database
        for(DTP3FilterData dTP3FilterData : excelObjectInputList) {
//        	System.out.println(dTP3FilterData.toString());
        	String phoneUpdate = Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dTP3FilterData);
        	if (phoneUpdate == null) {
        		DTP3FilterControllerMessagesList.add("Dữ liệu để cập nhật không tồn tại: "+dTP3FilterData.getPhoneNumber1());
        	}
        }
        
        return "redirect:/data-dtp3-filter-and-raw-pannel";
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
        NameProcess nameProcess = new NameProcess();
//        GenderProcess genderProcess = new GenderProcess();

        for(int i =0; i<excelObjectInputList.size(); i++) {
            //Tạo mới Last Name nếu chưa có
            if(!excelObjectInputList.get(i).getFullName1().isEmpty() && !excelObjectInputList.get(i).getZaloName().isEmpty()) {
            	if(excelObjectInputList.get(i).getLastName()==null||excelObjectInputList.get(i).getLastName().isEmpty() || excelObjectInputList.get(i).getLastName().isBlank()) {
            		excelObjectInputList.get(i).setLastName(nameProcess.getLastName(excelObjectInputList.get(i).getFullName1(), excelObjectInputList.get(i).getZaloName()));
            	}
            }
            
            //Tạo mới Gender nếu chưa có
            if(excelObjectInputList.get(i).getGender().equals(Gender.UNDEFINED) && !excelObjectInputList.get(i).getFullName1().isEmpty()) {
            	excelObjectInputList.get(i).setGender(GenderProcess.detectGenderFromFullName(excelObjectInputList.get(i).getFullName1()));
            }
            if(excelObjectInputList.get(i).getGender().equals(Gender.UNDEFINED) && excelObjectInputList.get(i).getLastName()!=null) {
            	excelObjectInputList.get(i).setGender(GenderProcess.detectGenderFromFullName(excelObjectInputList.get(i).getLastName()));
            }
            
          //Tạo mới Salutation nếu chưa có
            excelObjectInputList.get(i).setSalutation(GenderProcess.detectSalutationFromGender(excelObjectInputList.get(i).getGender(),excelObjectInputList.get(i).getDateOfBirth()));

            //Tạo mới Phone 1, gmail, address nếu chưa có.
//            if(excelObjectInputList.get(i).getMixContacts()!=null) {
//            	ContactResult result = ContactUtils.parseSingleMixContact(excelObjectInputList.get(i).getMixContacts());
//            	if(result.phones() !=null && excelObjectInputList.get(i).getPhoneNumber1()==null ) {
//            		excelObjectInputList.get(i).setPhoneNumber1(result.phones().get(0));
//            	}
//            	if(result.phones().get(1) !=null && excelObjectInputList.get(i).getPhoneNumber2()==null ) {
//            		excelObjectInputList.get(i).setPhoneNumber2(result.phones().get(1));
//            	}
//            	if(result.emails() !=null && excelObjectInputList.get(i).getGmail()==null) {
//            		excelObjectInputList.get(i).setGmail(result.emails().get(0));
//            	}
//            	if(result.addresses() !=null && excelObjectInputList.get(i).getAddress()==null) {
//            		excelObjectInputList.get(i).setAddress(result.addresses().get(0));
//            	}
//            	
//            	excelObjectInputList.get(i).setMixContacts("gotten - "+excelObjectInputList.get(i).getMixContacts());
//            }
            
            
        }
        
        //Update data to database
        for(DTP3FilterData dTP3FilterData : excelObjectInputList) {
        	String phoneUpdate = Dtp3FilterDataServices.dataDTP3FilterUpdateOldDataByPhone(dTP3FilterData);
        	if (phoneUpdate == null) {
        		DTP3FilterControllerMessagesList.add("Dữ liệu để cập nhật không tồn tại: "+dTP3FilterData.getPhoneNumber1());
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
        int countDataPhoneNull = 0;
        //Save new data to database
        for(DTP3FilterData dTP3FilterData : excelObjectInputList) {
        	if(dTP3FilterData.getPhoneNumber1() !=null ) {
        		System.out.println("Phone: "+ dTP3FilterData.getPhoneNumber1());
        		int deletedCount = Dtp3FilterDataServices.deleteDTP3FilterDataByPhone1(dTP3FilterData.getPhoneNumber1());
            	System.out.println(">>> deletedCount: "+deletedCount);
            	countData = countData + deletedCount;
        	} else {
        		countDataPhoneNull = countDataPhoneNull+1;
        	}
        	
        
        }
        
        DTP3FilterControllerMessagesList.add("Tổng có: "+ countData+" bị xóa");
        DTP3FilterControllerMessagesList.add("Tổng có: "+ countDataPhoneNull+" chưa bị xóa vì ko tồn tại.");
        
        
        return "redirect:/data-dtp3-filter-and-raw-pannel";
    }    
    
    /*
     * POSTING ACTION - UPLOAD LẤY DỮ LIỆU TỪ EXCEL ĐỂ CREATE.
     * */
    @PostMapping("/uploadAndCreateDtp3FilterData")
    public String handleFileUploadAndUpdateDtp3FilterData (@RequestParam("excelFileCreateNewDtp3FilterData") MultipartFile file, // Tên khớp với input file
            RedirectAttributes redirectAttributes, Model model) { // Bỏ throws nếu xử lý exception bên trong
		
		
        // --- Đọc file và lấy thông tin ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
        List<DTP3FilterData> excelObjectInputList = ioFunction.getDtp3FilterEntityListFromExcel(file);
        NameProcess nameProcess = new NameProcess();
        for(int i =0; i<excelObjectInputList.size(); i++) {
            //Tạo mới Last Name nếu chưa có
            if((excelObjectInputList.get(i).getFullName1()!=null && excelObjectInputList.get(i).getZaloName()!=null)) {
//          if((!excelObjectInputList.get(i).getFullName1().isEmpty() && !excelObjectInputList.get(i).getZaloName().isEmpty()) {
            	if(excelObjectInputList.get(i).getLastName()==null||excelObjectInputList.get(i).getLastName().isEmpty() || excelObjectInputList.get(i).getLastName().isBlank()) {
            		excelObjectInputList.get(i).setLastName(nameProcess.getLastName(excelObjectInputList.get(i).getFullName1(), excelObjectInputList.get(i).getZaloName()));
            	}
            }
            
            //Tạo mới Gender nếu chưa có
            if(excelObjectInputList.get(i).getGender().equals(Gender.UNDEFINED) && excelObjectInputList.get(i).getFullName1()!=null) {
//            if(excelObjectInputList.get(i).getGender().equals(Gender.UNDEFINED) && !excelObjectInputList.get(i).getFullName1().isEmpty()) {	
            	excelObjectInputList.get(i).setGender(GenderProcess.detectGenderFromFullName(excelObjectInputList.get(i).getFullName1()));
            }
            if(excelObjectInputList.get(i).getGender().equals(Gender.UNDEFINED) && excelObjectInputList.get(i).getLastName()!=null) {
            	excelObjectInputList.get(i).setGender(GenderProcess.detectGenderFromFullName(excelObjectInputList.get(i).getLastName()));
            }
            
            //Tạo mới Salutation nếu chưa có
            excelObjectInputList.get(i).setSalutation(GenderProcess.detectSalutationFromGender(excelObjectInputList.get(i).getGender(),excelObjectInputList.get(i).getDateOfBirth()));

            
            //Tạo mới Phone 1, gmail, address nếu chưa có.
            if(excelObjectInputList.get(i).getMixContacts()!=null) {
            	System.out.println("excelObjectInputList.get(i).getMixContacts(): "+ excelObjectInputList.get(i).getMixContacts());
            	ContactResult result = ContactUtils.parseSingleMixContact(excelObjectInputList.get(i).getMixContacts());
            	if(result.phones().size()>0 && excelObjectInputList.get(i).getPhoneNumber1()==null ) {
            		excelObjectInputList.get(i).setPhoneNumber1(result.phones().get(0));
            	}
            	if(result.phones().size() >=2 && excelObjectInputList.get(i).getPhoneNumber2()==null ) {
            		excelObjectInputList.get(i).setPhoneNumber2(result.phones().get(1));
            	}
            	if(result.emails().size()>0 && excelObjectInputList.get(i).getGmail()==null) {
            		excelObjectInputList.get(i).setGmail(result.emails().get(0));
            	}
            	if(result.addresses().size()>0 && excelObjectInputList.get(i).getAddress()==null) {
            		excelObjectInputList.get(i).setAddress(result.addresses().get(0));
            	}
            	
            	excelObjectInputList.get(i).setMixContacts("gotten - "+excelObjectInputList.get(i).getMixContacts());
            }
            
            
        }
        
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
    	
    	String nameFile = "Danh-sach-sdt-trung-lap.xlsx";
    	
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

        String headerValues = "attachment; filename=".concat(nameFile);        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
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
    	
    	String nameFile = "Form-DTP3Filter-Data.xlsx";
    	
    	// --- Đọc file và lấy thông tin ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
        DTP3FilterData dTP3FilterData = new DTP3FilterData(); //tạo enity để biết lấy form của DTP3FilterData
        
    	MultipartFile dtp3FilterFormExcelFileResponse = ioFunction.createlFromDtp3FilterExceForm(dTP3FilterData);
    	
        
        // --- Ghi kết quả ra MultipartFile (lưu vào biến static - CẨN THẬN THREAD SAFETY) ---
        try {
        	dtp3FilterFormExcelFileResponse = ioFunction.createlFromDtp3FilterExceForm(dTP3FilterData);

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

        String headerValues = "attachment; filename=\"" + nameFile + "\"";
//        String headerValues = "attachment; filename=".concat(nameFile);        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
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
    	String nameFile = "Full-data-DTP3.xlsx";
    	
    	List<DTP3FilterData> dtp3FilterDataResponse = Dtp3FilterDataServices.findAllDtp3FilterData();
    	// --- Đọc file và lấy thông tin ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
    	
    	MultipartFile dtp3FilterFulDataExcelFileResponse = ioFunction.createExcelFromDtp3FilterFullData(dtp3FilterDataResponse);;
    	
        
        // --- Ghi kết quả ra MultipartFile (lưu vào biến static - CẨN THẬN THREAD SAFETY) ---
        try {
        	dtp3FilterFulDataExcelFileResponse = ioFunction.createExcelFromDtp3FilterFullData(dtp3FilterDataResponse);

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
       
        String headerValues = "attachment; filename=\"" + nameFile + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(dtp3FilterFulDataExcelFileResponse.getSize())
                .body(resource);
    }
    
    /*
     * DTP3FILTER EXCEL Filtered Data 	
     * Phương thức tải xuống file Excel - Các entity đã Filter
     * */
    @GetMapping("/getFilteredDtp3DataExcelFile")
    public ResponseEntity<ByteArrayResource> downloadExcelFileFilterdDTP3Data() throws IOException {
    	String nameFile = "Filtered Data.xlsx";
//    	List<DTP3FilterData> dtp3FilterDataResponse = Dtp3FilterDataServices.findAllDtp3FilterData();
    	
    	// --- Đọc file và lấy thông tin ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
    	
        if(dtp3FilterDataStaticList !=null) {
        	MultipartFile FilteredDtp3DataExcelFileResponse = ioFunction.createExcelFromDtp3FilterFullData(dtp3FilterDataStaticList);;
        	
            
            // --- Ghi kết quả ra MultipartFile (lưu vào biến static - CẨN THẬN THREAD SAFETY) ---
            try {
            	FilteredDtp3DataExcelFileResponse = ioFunction.createExcelFromDtp3FilterFullData(dtp3FilterDataStaticList);

    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            // --- CẢNH BÁO: Phụ thuộc vào biến static excelFileResponse ---
            if (FilteredDtp3DataExcelFileResponse == null) {
                 logger.warn("Yêu cầu tải file response nhưng excelFileResponse là null.");
                 // Có thể trả về lỗi 404 hoặc thông báo khác
                 return ResponseEntity.notFound().build(); // Hoặc trả về trang lỗi
            }

            ByteArrayResource resource = new ByteArrayResource(FilteredDtp3DataExcelFileResponse.getBytes());
            
            String headerValues = "attachment; filename=".concat(nameFile);        
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .contentLength(FilteredDtp3DataExcelFileResponse.getSize())
                    .body(resource);
        } 
        
        List<DTP3FilterData> dtp3FilterDataResponse = Dtp3FilterDataServices.findAllDtp3FilterData();
    	// --- Đọc file và lấy thông tin ---
    	
    	MultipartFile dtp3FilterFulDataExcelFileResponse = ioFunction.createExcelFromDtp3FilterFullData(dtp3FilterDataResponse);;
    	
        
        // --- Ghi kết quả ra MultipartFile (lưu vào biến static - CẨN THẬN THREAD SAFETY) ---
        try {
        	dtp3FilterFulDataExcelFileResponse = ioFunction.createExcelFromDtp3FilterFullData(dtp3FilterDataResponse);

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
       
        String headerValues = "attachment; ".concat(nameFile);        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
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
    	 List<DTP3FilterData> dtp3FilterDataResponse = Dtp3FilterDataServices.findByDataType(dateType);
    	
//    	for (DTP3FilterData DTP3Filter : DTP1CRMList) {
//
//    		System.out.println(">>> "+DTP3Filter.toString());
//    		
//    	}
    	model.addAttribute("DTP1CRMListResponse", dtp3FilterDataResponse);
        return "app/IVC-CRM/IVC-CRM-View/IVC-CRM-DataProcess/DTP3FilterData";
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
	
	/*
	 * Lấy danh sách các racord có cùng sđt là phong1Input
	 * Dùng khi bạn muốn xóa một tài nguyên hoặc đối tượng khỏi hệ thống.
	 * */
	@GetMapping("/find-list-by-one-phoneNumber1")
	public ResponseEntity<String> findAllDTP3FilterDataByOnePhone1(@RequestBody String phoneNumber1s) {
		
		 List<DTP3FilterData> dtp3FilterDataResponse = Dtp3FilterDataServices.findAllByPhoneNumber1(phoneNumber1s);
		 for(DTP3FilterData dtp3FilterData : dtp3FilterDataResponse) {
			 System.out.println(dtp3FilterData.toString());			 
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


  //==============================================Helper=====================================================================
	
	private Map<String,List<String>> countValueOfFieldDtp3Filter (){
		Map<String,List<String>> countValueOfFieldDtp3FilterMap = new HashMap<>();
	List<String> messageCountValueOfFieldDtp3 = new ArrayList<>();
	List<String> errorCountValueOfFieldDtp3 = new ArrayList<>();
	
//	Count data Type: Raw, Filtered, CRM
	Map<String, Object> filterDataTypeRaw = new HashMap<>();
	filterDataTypeRaw.put("dataType", DataType.RAW_DATA);
	messageCountValueOfFieldDtp3.add("Tổng SL data raw: "+Dtp3FilterDataServices.countFlexible(filterDataTypeRaw));
	Map<String, Object> filterDataTypeFiltered = new HashMap<>();
	filterDataTypeFiltered.put("dataType", DataType.FILTERED_DATA);
	messageCountValueOfFieldDtp3.add("Tổng SL data Filtered: "+Dtp3FilterDataServices.countFlexible(filterDataTypeFiltered));

	
//	Count Phone: Phone duplicate, Phone null.
	if(Dtp3FilterDataServices.findAllByPhoneDuplicate().size()>0) {
		errorCountValueOfFieldDtp3.add("Tổng SL data có sđt trùng lặp: "+Dtp3FilterDataServices.findAllByPhoneDuplicate().size());
	}
	Map<String, Object> filterPhoneNull = new HashMap<>();
	filterPhoneNull.put("phoneNumber1", null);
//	filterPhoneNull.put("phoneNumber1", "Chưa có sđt !");//xóa sau
	long countPhoneNull = Dtp3FilterDataServices.countFlexible(filterPhoneNull);
	if(!(countPhoneNull==0)) {
		errorCountValueOfFieldDtp3.add("Tổng SL data ko có sđt: "+countPhoneNull);
	}
	
//	Count raw LastName: Null.
	Map<String, Object> filterRawDataAndLastNameNull = new HashMap<>();
	filterRawDataAndLastNameNull.put("dataType", DataType.RAW_DATA);
	filterRawDataAndLastNameNull.put("lastName", null); //Sửa thành null
	long countRawDataAndLastNameNull = Dtp3FilterDataServices.countFlexible(filterRawDataAndLastNameNull);
	if(!(countRawDataAndLastNameNull==0)) {
		errorCountValueOfFieldDtp3.add("Tổng SL raw data ko có tên: "+countRawDataAndLastNameNull);
	}
	
//	Count raw Salutation undefined.
	Map<String, Object> filterRawDataAndSalutationUndefined = new HashMap<>();
	filterRawDataAndSalutationUndefined.put("dataType", DataType.RAW_DATA);
	filterRawDataAndSalutationUndefined.put("salutation", Salutation.UNDEFINED);
	long countRawDataAndSalutationUndefined = Dtp3FilterDataServices.countFlexible(filterRawDataAndSalutationUndefined);
	if(!(countRawDataAndSalutationUndefined==0)) {
		errorCountValueOfFieldDtp3.add("Tổng SL raw data giới tính ko xác định : "+countRawDataAndSalutationUndefined);
	}
	
//	Count raw result Follow null.
	Map<String, Object> filterRawDataAndResultFollowNull = new HashMap<>();
	filterRawDataAndResultFollowNull.put("dataType", DataType.RAW_DATA);
	filterRawDataAndResultFollowNull.put("resultFollow", null); //Sửa thành null
	long countRawDataAndResultFollowNull = Dtp3FilterDataServices.countFlexible(filterRawDataAndResultFollowNull);
	if(!(countRawDataAndResultFollowNull==0)) {
		errorCountValueOfFieldDtp3.add("Tổng SL raw data chưa từng tương tác : "+countRawDataAndResultFollowNull);
	}
	
	
//	Count raw Filtered has resultFollow as: kết bạn, thành công, ko tồn tại, thất bại.
	Map<String, Object> filterRawDataAndResultFollowKB = new HashMap<>();
	filterRawDataAndResultFollowKB.put("dataType", DataType.RAW_DATA);
	filterRawDataAndResultFollowKB.put("resultFollow", "Kết bạn");
	long countRawDataAndResultFollowKB = Dtp3FilterDataServices.countFlexible(filterRawDataAndResultFollowKB);
	if(!(countRawDataAndResultFollowKB==0)) {
		errorCountValueOfFieldDtp3.add("Tổng SL raw data đã kết bạn : "+countRawDataAndResultFollowKB);
	}
	
	Map<String, Object> filterRawDataAndResultFollowTC = new HashMap<>();
	filterRawDataAndResultFollowTC.put("dataType", DataType.RAW_DATA);
	filterRawDataAndResultFollowTC.put("resultFollow", "Chặn tin nhắn");
	long countRawDataAndResultFollowTC = Dtp3FilterDataServices.countFlexible(filterRawDataAndResultFollowTC);
	if(!(countRawDataAndResultFollowTC==0)) {
		errorCountValueOfFieldDtp3.add("Tổng SL raw data chặn tin nhắn : "+countRawDataAndResultFollowTC);
	}
	
	Map<String, Object> filterRawDataAndResultFollowTB = new HashMap<>();
	filterRawDataAndResultFollowTB.put("dataType", DataType.RAW_DATA);
	filterRawDataAndResultFollowTB.put("resultFollow", "Thất bại");
	long countRawDataAndResultFollowTB = Dtp3FilterDataServices.countFlexible(filterRawDataAndResultFollowTB);
	if(!(countRawDataAndResultFollowTB==0)) {
		errorCountValueOfFieldDtp3.add("Tổng SL raw data thất bại : "+countRawDataAndResultFollowTB);
	}
	
	Map<String, Object> filterFilteredDataAndNoZalo = new HashMap<>();
	filterFilteredDataAndNoZalo.put("dataType", DataType.FILTERED_DATA);
	filterFilteredDataAndNoZalo.put("zaloName", null);
	long countFilteredDataAndNoZalo = Dtp3FilterDataServices.countFlexible(filterFilteredDataAndNoZalo);
	if(!(countFilteredDataAndNoZalo==0)) {
		errorCountValueOfFieldDtp3.add("Tổng dữ liệu ko có Zalo : "+countFilteredDataAndNoZalo);
	}
	
//	Count CRM chặn tin nhắn
	Map<String, Object> filterCrmDataAndResultFollowZaloBlock = new HashMap<>();
	filterCrmDataAndResultFollowZaloBlock.put("dataType", DataType.CRM_DATA);
	filterCrmDataAndResultFollowZaloBlock.put("resultFollow", "kết bạn");
	long countCrmDataAndResultFollowZaloBlock = Dtp3FilterDataServices.countFlexible(filterCrmDataAndResultFollowZaloBlock);
	if(!(countCrmDataAndResultFollowZaloBlock==0)) {
		errorCountValueOfFieldDtp3.add("Tổng SL CRM data chặn tin nhắn: "+countCrmDataAndResultFollowZaloBlock);
	}
	
	
	countValueOfFieldDtp3FilterMap.put("messageCountValueOfFieldDtp3", messageCountValueOfFieldDtp3);
	countValueOfFieldDtp3FilterMap.put("errorCountValueOfFieldDtp3", errorCountValueOfFieldDtp3);
	return countValueOfFieldDtp3FilterMap;
}	
	
	/*
	 * HELPER
	 * Set Map<String, Object> to count at database
	 * Input: String <entity field>; String <value>
	 * Return long
	 * */

	
}
