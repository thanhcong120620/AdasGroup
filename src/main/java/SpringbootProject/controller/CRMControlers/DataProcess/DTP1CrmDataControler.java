package SpringbootProject.controller.CRMControlers.DataProcess;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import SpringbootProject.entity.CRMEntity.DTP1CRMEntity;
import SpringbootProject.service.IDTP1CRMEntity;

@Controller
public class DTP1CrmDataControler {

	@Autowired
	private IDTP1CRMEntity Dtp1CrmServices;
	
	/*
     * TRUY CẬP VÀO THYMLEAF
     * */
    @GetMapping("/data-dtp1-crm-data")
    public String index(Model model) {
        // Có thể thêm logic xóa file cũ hoặc reset trạng thái ở đây nếu cần
        // Ví dụ: excelFileResponse = null; excelFileError = null;
    	List<DTP1CRMEntity> DTP1CRMList = Dtp1CrmServices.findAllDtp1CRMEntity();
    	
    	for (DTP1CRMEntity DTP1CRM : DTP1CRMList) {

    		System.out.println(">>> "+DTP1CRM.toString());
    		

    	}
    	
    	
        return "app/IVC-CRM/IVC-CRM-View/IVC-CRM-DataProcess/DTP3Filter&RawData";
    }
    
}
