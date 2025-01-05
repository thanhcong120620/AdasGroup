package SpringbootProject.controller.TestDevelop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.exceptions.CsvValidationException;

import SpringbootProject.algorithms.TestDevelop.IOPerson;
import SpringbootProject.entity.Person;
import SpringbootProject.entity.UserEntity;


@Controller
public class AdminController {
//	private static final String COMMA_DELIMITER = ",";
	private static  List<UserEntity> userStatic;
	
	IOPerson ioperson = new IOPerson();
	
	@GetMapping("/DataFromCsv")
    public String uploadPage(Model model) {
		System.out.println(">>> userStatic: ");
//	    showUserList(userStatic);
	    List<UserEntity> userResponse = userStatic;
	    model.addAttribute("userResponse", userResponse);
		
        return "DataFromCsv";
    }

	
	
    @PostMapping("/upload")
    public String uploadCsv(@RequestParam("file") MultipartFile file, Model model) {
    	System.out.println("Admin Controller 1: ");
        List<Person> personList = new ArrayList<>();
        
       try {
    	   personList = IOPerson.ReadFileCsvUpload(file);
	} catch (CsvValidationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//		persons = ioperson.ReadFileCsvUpload(file);
       List<UserEntity> userList = convertPersonListToUserList(personList);

       System.out.println(">>> userList: ");
//       showUserList(userList);
	   
       userStatic = userList;
	   
        return "redirect:/DataFromCsv";
    }
	
    
 //-----------------------------------------------------------------------------------------------   
    /*
     * Convert person to User Entity
     * */
    public List<UserEntity> convertPersonListToUserList (List<Person> persons){
//    	ioperson.showConsole(persons);
    	List<UserEntity> users = new ArrayList<>();
	    for(int i=0;i<persons.size();i++) {
	    	UserEntity user = new UserEntity();
	    	user.setId(Long.valueOf(i+1));
	    	user.setFullName(persons.get(i).getFullName());
	    	user.setGenderUser(persons.get(i).getGender());
	    	user.setGmail(persons.get(i).getGmail());
	    	user.setStatus(persons.get(i).getNote());
	    	
	    	users.add(user);
	    }
    	return users;
    }
    
    
    /*
     * show User Entity
     * */
    public void showUserList (List<UserEntity> users){
	    for(int i=0;i<users.size();i++) {
	    	System.out.println("Id: "+users.get(i).getId());
	    	System.out.println("User Name: "+users.get(i).getFullName());
	    	System.out.println("Gender: "+users.get(i).getGenderUser());
	    	System.out.println("Gmail: "+users.get(i).getGmail());
	    	System.out.println("Status: "+users.get(i).getStatus());
	    	System.out.println("-----------------------------------");
	    }

    }
    
    
	
}
