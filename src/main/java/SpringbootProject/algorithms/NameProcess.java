package SpringbootProject.algorithms;

import java.util.ArrayList;
import java.util.List;

public class NameProcess {
	public NameProcess() {
	}

	public String Name(String nameInput) {
		String nameOutput;
		String s1 = new String();
		nameInput = nameInput.trim();// Loai bo hai dau cach dau va cuoi Ten
		int k;
		for (k = nameInput.length() - 1; k >= 0; k--) {
			s1 = nameInput.substring(k, k + 1);
			if (s1.equals(" "))
				break;
		}
		nameOutput = nameInput.substring(k + 1);
//        System.out.println("Ten: "+ nameInput.substring(k+1));
		int i;
		for (i = 0; i <= nameInput.length(); i++) {
			s1 = nameInput.substring(i, i + 1);
			if (s1.equals(" "))
				break;
		}
//        System.out.println("Ho: "+ nameInput.substring(0,i));
		int j = 0;

		if (j > i && j < k) {
			s1 = nameInput.substring(j, j + 1);
		}
//        System.out.println("Ten Dem: "+nameInput.substring(i+1,k));

		return nameOutput;
	}

	public String NameHeader(String nameInput, String gender) {
		String nameOutput = Name(nameInput);
		if (gender.equals("f")) {
			nameOutput = "Ms. " + nameOutput;
		} else if (gender.equals("m")) {
			nameOutput = "Mr. " + nameOutput;
		} else {
			nameOutput = "Mr/Ms. " + nameOutput;
		}
		return nameOutput;
	}
	
	public String NameUserN(String nameInput, String gender) {
		String nameOutput = Name(nameInput);
		if (gender.equals("f")) {
			nameOutput = "chị " + nameOutput;
		} else if (gender.equals("m")) {
			nameOutput = "anh " + nameOutput;
		} else {
			nameOutput = "anh/chị " + nameOutput;
		}
		return nameOutput;
	}
	
	public String NameUserC(String nameInput, String gender) {
		String nameOutput = Name(nameInput);
		if (gender.equals("f")) {
			nameOutput = "Chị " + nameOutput;
		} else if (gender.equals("m")) {
			nameOutput = "Anh " + nameOutput;
		} else {
			nameOutput = "Anh/Chị " + nameOutput;
		}
		return nameOutput;
	}
	
	public String GenderUser(String gender) {
		String genderUser = "";
		if (gender.equals("f")) {
			genderUser = "chị";
		} else if (gender.equals("m")) {
			genderUser = "anh";
		} else {
			genderUser = "anh/chị";
		}
		return genderUser;
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	public List<String> nameInParagraphTrue (List<String> paragraphList, String headerName,String normalName,String caplockName,String gender){
		List<String> nameInParagraph = new ArrayList<String>();
		System.out.println("Input: "+headerName+normalName+caplockName+gender);
		
		for(int i=0;i<paragraphList.size();i++) {
			String para_i = paragraphList.get(i);
			
			para_i = para_i.replace("{{headerName}}",headerName);
			para_i = para_i.replace("{{caplockName}}",caplockName);
			para_i = para_i.replace("{{normalName}}",normalName);
			para_i = para_i.replace("{{gender}}",gender);
			
			nameInParagraph.add(para_i);
		}
		
		return nameInParagraph;
	}
	
	public List<String> nameInParagraphFalse (List<String> paragraphList){
		List<String> nameInParagraph = new ArrayList<String>();
		
		for(int i=0;i<paragraphList.size();i++) {
			String para_i = paragraphList.get(i);
			para_i = para_i.replace("{{headerName}}","Quý khách hàng");
			para_i = para_i.replace("{{caplockName}}","Anh/Chị");
			para_i = para_i.replace("{{normalName}}","anh/chị");
			para_i = para_i.replace("{{gender}}","anh/chị");
			
			nameInParagraph.add(para_i);

		}
		
		return nameInParagraph;
	}

}
