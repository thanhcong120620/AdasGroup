package SpringbootProject.controller.CRMControlers.DataProcess;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import SpringbootProject.entity.UserEntity;
import SpringbootProject.entity.CRMEntity.DTP1CRMEntity;
import SpringbootProject.entity.CRMEntity.DTP3FilterData;
import SpringbootProject.entity.enums.DataType;
import SpringbootProject.service.IDTP1CRMEntity;
import SpringbootProject.service.IDTP3FilterDataEntity;

@Controller
public class DTP3FilterAndRawDataController {
	
	@Autowired
	private IDTP3FilterDataEntity Dtp3FilterDataServices;
	 /*
     * TRUY CẬP VÀO THYMLEAF
     * */
    @GetMapping("/data-dtp3-filter-and-raw-pannel")
    public String index(Model model) {
        // Có thể thêm logic xóa file cũ hoặc reset trạng thái ở đây nếu cần
        // Ví dụ: excelFileResponse = null; excelFileError = null;
    	List<DTP3FilterData> DTP1CRMList = Dtp3FilterDataServices.findAllDtp3FilterData();
    	
//    	for (DTP3FilterData DTP3Filter : DTP1CRMList) {
//    		System.out.println(">>> "+DTP3Filter.toString());
//    	}
    	model.addAttribute("DTP1CRMListResponse", DTP1CRMList);
        return "app/IVC-CRM/IVC-CRM-View/IVC-CRM-DataProcess/DTP3Filter&RawData";
    }

 //=========================================THAO TÁC với I/O =========================================================== 
    
    
    
    
    
    
    
    
    
    
    
    

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
	 * Chạy trên vue
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
	
	
	/*
	 * Chạy trên vue
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
