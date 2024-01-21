package SpringbootProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import SpringbootProject.algorithms.GmailProcess;
import SpringbootProject.algorithms.NameProcess;
import SpringbootProject.entity.UserEntity;
import SpringbootProject.service.IUser;
import SpringbootProject.service.implement.SunCosmo.SunCosmoCampT12M1;

@Controller
public class MailController {

	
	 @Autowired 
	 SunCosmoCampT12M1 suncosmo;


	@Autowired
	private IUser userService;

	/*
	 * Display all data
	 */
	@GetMapping("/mail-home")
	public String home(Model model) {

		List<UserEntity> userResponse = userService.findAllUser();
		model.addAttribute("userResponse", userResponse);

		return "trang-chu";
	}

	/*
	 * Send Draft
	 */
	@PostMapping("/send-draft")
	public String sendDraft(Model model, @RequestParam(value = "subjectD", required = false) String subject,
			@RequestParam(value = "mailCheck", required = false) String mailCheck) {

		System.out.println("Subject: " + subject);
		System.out.println("Mail: " + mailCheck);
		GmailProcess processedMail = new GmailProcess();
		String[] mailArray = processedMail.splitMail(mailCheck);
		for (int i = 0; i < mailArray.length; i++) {
			String mail = mailArray[i];
			suncosmo.sendMail(subject, mail, "Ms. Quỳnh", "chị Quỳnh", "Chị Quỳnh", "chị", 1L);
		}

		return "redirect:/mail-home";
	}

	/*
	 * Send All
	 */
	@PostMapping("/send-all")
	public String sendAll(Model model, @RequestParam(value = "subjectA", required = false) String subject) {
		System.out.println("Subject: " + subject);
		final long start = System.currentTimeMillis();
		List<UserEntity> userList = userService.findAllUser();
		
		int n = userList.size();
		
		for (int i = 0; i < n ; i++) {
//			System.out.println("1");
			final long startTime = System.currentTimeMillis();
			UserEntity user = userList.get(i);
			// Xử lý user mail
			GmailProcess processedMail = new GmailProcess();
			String mail = processedMail.deleteBlank(user.getGmail());
			Long id = user.getId();
			// Xử lý user name
			if (userList.get(i).getFullName().isEmpty() || userList.get(i).getFullName().equals("")) {
//				System.out.println("2");
				// Send mail
				suncosmo.sendMail(subject, mail, "Quý khách hàng", "anh/chị", "Anh/Chị", "fm", id);

			} else {
//				System.out.println("3");
				NameProcess nameProcess = new NameProcess();
				String headerName = nameProcess.NameHeader(user.getFullName(), user.getGenderUser());
				String normalName = nameProcess.NameUserN(user.getFullName(), user.getGenderUser());
				String caplockName = nameProcess.NameUserC(user.getFullName(), user.getGenderUser());
				String gender = nameProcess.GenderUser(user.getGenderUser());

//				System.out.println("4");
				// Send mail
				suncosmo.sendMail(subject, mail, headerName, normalName, caplockName, gender, id);
			}
			final long endTime = System.currentTimeMillis();	
			System.out.println(">>> Đã gửi đến user có id: "+ user.getId()+" ---- Total time: " + ((endTime - startTime)/1000)+"(s)");			 
		}
		final long end = System.currentTimeMillis();	
		System.out.println(">>> ĐÃ GỬI HẾT, TỔNG THỜI GIAN: "+ ((end - start)/1000)+"(s)");			 
		return "redirect:/mail-home";
	}

	/*------------------------------------User Controller------------------------------------*/

	/*
	 * Send update result
	 */
	@PutMapping("/update-result")
	public String updateResult(@RequestBody List<UserEntity> userRequestList) {
		for (int i = 0; i < userRequestList.size(); i++) {
			UserEntity userRequest = userRequestList.get(i);
			UserEntity updateUser = userService.save(userRequest);
			System.out.println(updateUser.toString());
		}

		return "redirect:/mail-home";
	}

	@GetMapping("/get-onedata")
	public UserEntity getOneData(@RequestBody Long id) {
		UserEntity userResponse = userService.findById(id);
		return userResponse;
	}

	@GetMapping("/get-alldata")
	public String getAll() {
		List<UserEntity> userList = userService.findAllUser();
		for (int i = 0; i < userList.size(); i++) {
			UserEntity user = userList.get(i);
			// Xử lý user name
			if (userList.get(i).getFullName().isEmpty() || userList.get(i).getFullName().equals("")) {
				// Send mail
				System.out.println("User " + i + ": " + userList.get(i).toString());
			} else {
				NameProcess nameProcess = new NameProcess();
				String headerName = nameProcess.NameHeader(user.getFullName(), user.getGenderUser());
				String normalName = nameProcess.NameUserN(user.getFullName(), user.getGenderUser());
				String caplockName = nameProcess.NameUserC(user.getFullName(), user.getGenderUser());
				String gender = nameProcess.GenderUser(user.getGenderUser());

				// Send mail
				System.out.println("User " + i + ": " + userList.get(i).toString());
			}

		}

		return "redirect:/mail-home";
	}

}
