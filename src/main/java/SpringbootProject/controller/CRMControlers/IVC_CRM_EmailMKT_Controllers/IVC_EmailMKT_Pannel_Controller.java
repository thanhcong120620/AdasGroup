package SpringbootProject.controller.CRMControlers.IVC_CRM_EmailMKT_Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import SpringbootProject.algorithms.IOAlgorithm.IOFunction;
import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.GmailProcess;
import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.NameProcess;
import SpringbootProject.entity.UserEntity;
import SpringbootProject.service.IUser;
import SpringbootProject.service.implement.form.FormDevelop;

//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:5173") // Cho phép truy cập từ frontend (Vue.js)
@Controller
public class IVC_EmailMKT_Pannel_Controller {
/*------------------------------------------Input - Processing------------------------------------*/
	
	GmailProcess gmailProcess = new GmailProcess();	
	NameProcess nameProcess = new NameProcess();
	
	
	@Autowired 
	 FormDevelop formDevelop;

	@Autowired
	private IUser userService;
	
	
	
	/*------------------------------------------CRM - -EMAIL - CONTROLLER------------------------------------*/	
	
	
	/*
	 * Chạy trên thymleaf
	 * Upload danh sách UserEntity bằng file Excel từ client
	 * */
	@PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile[] files) throws IllegalStateException, IOException {
		System.out.println("@PostMapping(\"/upload\")");
		
        IOFunction ioFunction = new IOFunction();
        List<UserEntity> users = ioFunction.userListFromExcelFile(files);
        
        for(UserEntity user : users) {
        	userService.userCreateUpdate(user);
        }
		
		return "redirect:/crm-emailMKT-pannel-thymleaf";
	}
	
	/*
	 * Chạy trên thymleaf
	 * Xoá danh sách UserEntity cũ bằng lệnh đơn giản từ client
	 * */
	@GetMapping("/deleteOldData")
    public String deleteOldData() throws IllegalStateException, IOException {
		List<UserEntity> userResponse = userService.findAllUser();
		List<Long> userIds = new ArrayList<>();
		for(int i=0; i<userResponse.size(); i++) {
			userIds.add(userResponse.get(i).getId());
		}
		for(Long id:userIds) {
			userService.deleteUser(id);
		};
		
		return "redirect:/crm-emailMKT-pannel-thymleaf";
	}
	
	
	
	/*
	 * Old - Use for Thymleaf
	 * */
	@GetMapping("/crm-emailMKT-pannel-thymleaf")
	public String crmEmailPannelThymleaf(Model model) {
		System.out.println("oke");
		List<UserEntity> userResponse = userService.findAllUser();
		for(int i=0;i<userResponse.size();i++) {
			userResponse.get(i).toString();
		}
		model.addAttribute("userResponse", userResponse);

//		return "redirect:/crm-emailMKT-pannel";
		return "app/IVC-Digital-Gmail/old-email-pannel";
	}
	
	/*
	 * Use for Vuejs3
	 * */
	@GetMapping("/crm-emailMKT-pannel-vue")
	public Map<String, Object> crmEmailPannel(Model model) {
		List<UserEntity> userResponse = userService.findAllUser();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Data received successfully");
        response.put("userResponse", Map.of("userCustomer", userResponse));

        return response;
	}
}
