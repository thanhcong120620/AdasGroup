package SpringbootProject.controller.CRMControlers.IVC_CRM_EmailMKT_Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringbootProject.algorithms.GmailProcess;
import SpringbootProject.algorithms.NameProcess;
import SpringbootProject.entity.UserEntity;
import SpringbootProject.service.IUser;
import SpringbootProject.service.implement.form.FormDevelop;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // Cho phép truy cập từ frontend (Vue.js)
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
	 * Old - Use for Thymleaf
	 * */
//	@GetMapping("/crm-emailMKT-pannel")
//	public String crmEmailPannel(Model model) {
//		System.out.println("oke");
//		List<UserEntity> userResponse = userService.findAllUser();
//		for(int i=0;i<userResponse.size();i++) {
//			userResponse.get(i).toString();
//		}
//		model.addAttribute("userResponse", userResponse);
//
//		return "redirect:/crm-emailMKT-pannel";
//	}
	
	/*
	 * Use for Vuejs3
	 * */
	@GetMapping("/crm-emailMKT-pannel")
	public Map<String, Object> crmEmailPannel(Model model) {
		List<UserEntity> userResponse = userService.findAllUser();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Data received successfully");
        response.put("userResponse", Map.of("userCustomer", userResponse));

        return response;
	}
}
