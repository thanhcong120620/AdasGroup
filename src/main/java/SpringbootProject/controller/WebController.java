package SpringbootProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebController {
	
	
	
	/*------------------------------------------WEB CONTROLLER------------------------------------*/
	
	@GetMapping("/")
	public String home(Model model) {

		return "redirect:/index";
	}
	
	@GetMapping("/index")
	public String index(Model model) {

		return "index-total";
	}

	
}
