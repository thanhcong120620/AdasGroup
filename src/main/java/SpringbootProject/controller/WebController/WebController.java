package SpringbootProject.controller.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebController {
	
	
	
	/*------------------------------------------WEB CONTROLLER------------------------------------*/
	
	@GetMapping("/")
	public String home(Model model) {
		System.out.println("web controller");
//		return "redirect:/crm-emailMKT-pannel-thymleaf";
		 return "redirect:/data-dtp3-filter-and-raw-pannel";
//		 return "app/IVC-CRM/IVC-CRM-View/IVC-CRM-DataProcess/DTP3FilterData";
	}
	
	@GetMapping("/index")
	public String index(Model model) {

		return "index-total";
	}

	
}
