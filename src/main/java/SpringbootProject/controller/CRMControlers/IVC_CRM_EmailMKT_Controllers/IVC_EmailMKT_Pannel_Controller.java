package SpringbootProject.controller.CRMControlers.IVC_CRM_EmailMKT_Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import SpringbootProject.algorithms.GmailProcess;
import SpringbootProject.algorithms.NameProcess;
import SpringbootProject.entity.UserEntity;
import SpringbootProject.service.IUser;
import SpringbootProject.service.implement.form.FormDevelop;

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
	
	@GetMapping("/crm-emailMKT-pannel")
	public String crmEmailPannel(Model model) {
		System.out.println("oke");
		List<UserEntity> userResponse = userService.findAllUser();
		for(int i=0;i<userResponse.size();i++) {
			userResponse.get(i).toString();
		}
		model.addAttribute("userResponse", userResponse);

		return "/app/IVC-CRM/IVC-CRM-View/IVC-CRM-EmailMKT";
	}
}
