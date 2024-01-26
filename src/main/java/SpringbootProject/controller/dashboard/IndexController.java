package SpringbootProject.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	
	/*------------------------------------------INDEX CONTROLLER------------------------------------*/
	
	@GetMapping("/trang-chu")
	public String HomePage() {

//		return "test";
//		return "dashboard/test-dashboard";
		return "dashboard/index";
	}
	
	
}
