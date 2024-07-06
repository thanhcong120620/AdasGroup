package SpringbootProject.algorithms;

import java.util.ArrayList;
import java.util.List;

public class GmailProcess {
	
	public GmailProcess() {}
	
	public String deleteBlank(String mailInput) {
		return mailInput.replaceAll(" ", "");
	}
	
	public String[] splitMail(String mailInput) {
		String[] mailContainer = mailInput.split(";");
		String[] mailOutput = new String[mailContainer.length];
		for(int i=0; i<mailContainer.length;i++) {
			String mailElement = deleteBlank(mailContainer[i]);
			mailOutput[i] = mailElement;
		}
		return mailOutput;
	}
	
	public List<String> gmailImageList (){
		List<String> imageList	= new ArrayList<>();
		
		String logo = "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\symphony_logo.png";
		imageList.add(logo);
		String image1 = "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\overview.jpg";
		imageList.add(image1);
		String image2 = "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\position.jpg";
		imageList.add(image2);
		String image3 = "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\amentities1.jpg";
		imageList.add(image3);
		String image4 = "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\sale-policy.jpg";
		imageList.add(image4);
		String image5 = "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\amentities2.jpg";
		imageList.add(image5);
		
	
	return imageList;
	}
	
	

}
