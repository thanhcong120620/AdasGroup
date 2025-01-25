package SpringbootProject.controller.CRMControlers.IVC_CRM_EmailMKT_Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import SpringbootProject.algorithms.GmailMKTAlgorithm.GmailProcess;
import SpringbootProject.algorithms.GmailMKTAlgorithm.NameProcess;
import SpringbootProject.entity.UserEntity;
import SpringbootProject.service.IUser;
import SpringbootProject.service.implement.form.FormDevelop;


@Controller
public class Controlers_IVC_CRM_EmailMKT_FormStandar {
	
	
	/*------------------------------------------Input - Processing------------------------------------*/
	
	GmailProcess gmailProcess = new GmailProcess();	
	NameProcess nameProcess = new NameProcess();
	
	
	@Autowired 
	 FormDevelop formDevelop;

	@Autowired
	private IUser userService;
	
	
	
	/*------------------------------------------CRM - -EMAIL - CONTROLLER------------------------------------*/	
	
	@GetMapping("/crm-email-formstandard-pannel-thymleaf")
	public String crmEmailPannel(Model model) {

		List<UserEntity> userResponse = userService.findAllUser();
		for(int i=0;i<userResponse.size();i++) {
			userResponse.get(i).toString();
		}
		model.addAttribute("userResponse", userResponse);

		return "/app/IVC-CRM/IVC-CRM-View/IVC-CRM-EmailMKT-ViewForm/IVC-CRM-EmailMKT-FormStandard";
//		return "/app/IVC-Digital-Gmail/old-email-pannel";
	}
	
	/*
	 * Send All
	 */
	@PostMapping("/send-all-gmailformstandard")
	public String sendMailAll(Model model, 
			@RequestParam(value = "subjectA", required = false) String subject,
			@RequestParam(value = "paragraph0", required = false) String paragraph0, 
			@RequestParam(value = "paragraph1", required = false) String paragraph1, 
			@RequestParam(value = "paragraph2", required = false) String paragraph2, 
			@RequestParam(value = "paragraph3", required = false) String paragraph3, 
			@RequestParam(value = "paragraph4", required = false) String paragraph4,    
			@RequestParam("images") MultipartFile[] files) 
	throws IOException {
		
		final long start = System.currentTimeMillis();
		List<UserEntity> userList = userService.findAllUser();
		String htmlPath= "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\templates\\app\\IVC-Digital-Gmail\\Form-Gmail\\Form-Gmail-Standard.html";
		
		//Xử lý hình ảnh để đưa vào mail
		List<String> imageList 	= gmailProcess.gmailImageListFromFile(files); 
		
		List<String> paragraphList = new ArrayList<String>();
		paragraphList.add(paragraph0);
		paragraphList.add(paragraph1);
		paragraphList.add(paragraph2);
		paragraphList.add(paragraph3);
		paragraphList.add(paragraph4);
		

		int n = userList.size();
		
		for (int i = 0; i < n ; i++) {
			final long startTime = System.currentTimeMillis();
			UserEntity user = userList.get(i);
			// Xử lý user mail
			GmailProcess processedMail = new GmailProcess();
			String mail = processedMail.deleteBlank(user.getGmail());
			Long id = user.getId();

			// Xử lý Name
			if (userList.get(i).getFullName().isEmpty() || userList.get(i).getFullName().equals("")) {
				//Xử lý các nội dung theo từng user
				List<String> paragraphListByUserFalse = nameProcess.nameInParagraphFalse(paragraphList);
				
				// Send mail
				try {
					formDevelop.sendMail(subject, mail, "Quý khách hàng", "anh/chị", "Anh/Chị", "fm", id,htmlPath, imageList, paragraphListByUserFalse);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				// Xử lý Name
				String headerName = nameProcess.NameHeader(user.getFullName(), user.getGenderUser());
				String normalName = nameProcess.NameUserN(user.getFullName(), user.getGenderUser());
				String caplockName = nameProcess.NameUserC(user.getFullName(), user.getGenderUser());
				String gender = nameProcess.GenderUser(user.getGenderUser());
				
				//Xử lý các nội dung theo từng user
				List<String> paragraphListByUserTrue = nameProcess.nameInParagraphTrue(paragraphList, headerName, normalName, caplockName,gender);

				// Send mail
				try {
					formDevelop.sendMail(subject, mail, headerName, normalName, caplockName, gender, id , htmlPath, imageList, paragraphListByUserTrue);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			final long endTime = System.currentTimeMillis();	
			System.out.println(">>> Đã gửi đến user có id: "+ user.getId()+" ---- Total time: " + ((endTime - startTime)/1000)+"(s)");			 
		}
		final long end = System.currentTimeMillis();	
		System.out.println(">>> ĐÃ GỬI HẾT, TỔNG THỜI GIAN: "+ ((end - start)/1000)+"(s)");			 
		return "redirect:/";
	}
	
}
