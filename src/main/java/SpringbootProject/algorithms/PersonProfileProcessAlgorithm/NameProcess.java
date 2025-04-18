package SpringbootProject.algorithms.PersonProfileProcessAlgorithm;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class NameProcess {
    private static final boolean TO_UPPER_CASE = true;
    private static final String WORD_SEPARATOR = " ";
    private static final String UNKNOWN_FIRST_NAME = "";
    
	public NameProcess() {
	}
	
//=======================================================================================================	

	
	static String extractFirstName (String fullName, String nickname) {
		String convertFullname = convertVietnameseNameToEnglishName(fullName);
		String convertNickName = convertVietnameseNameToEnglishName(nickname);
		String[] fullNameParts = fullName.split(WORD_SEPARATOR);
		String[] nickNameParts = nickname.split(WORD_SEPARATOR);
		String[] convertFullNameParts = convertFullname.split(WORD_SEPARATOR);
		String[] convertNickNameParts = convertNickName.split(WORD_SEPARATOR);
		
		// Duyệt qua các cụm từ trong mảng nickname đã convert
	    for (int i = 0; i < convertNickNameParts.length; i++) {
	        String nicknamePart = convertNickNameParts[i];

	        // So sánh với các cụm từ trong mảng fullname đã convert
	        for (int j = 0; j < convertFullNameParts.length; j++) {
	            String fullnamePart = convertFullNameParts[j];

	            if (nicknamePart.equals(fullnamePart)) {
	                // Tìm thấy sự trùng khớp
	                String position;
	                if (j == 0) {
	                    position = "lastname";
	                } else if (j == convertFullNameParts.length - 1) {
	                    position = "firstname";
	                } else {
	                    position = "midname";
	                }
	                System.out.println("Nickname part '" + nicknamePart + "' matches fullname part '" + fullnamePart + "' at position: " + position);
	            }
	        }

	        //Nếu không tìm thấy bất kì cụm từ nào thì in ra
	        boolean foundMatch = false;
	        for (int j = 0; j < convertFullNameParts.length; j++) {
	            if (nicknamePart.equals(convertFullNameParts[j])) {
	                foundMatch = true;
	                break;
	            }
	        }
	        if(!foundMatch){
	            System.out.println("Nickname part '" + nicknamePart + "' không xác định");
	        }
	    }

		
		return null;
	}
	
	//convert Vietnamese name to english name
	static String convertVietnameseNameToEnglishName(String nickname) {
	    // 1. Loại bỏ dấu khỏi nickname
	    String normalizedNickname = removeAccent(nickname);

	    // 2. Chuyển đổi thành chữ thường
	    normalizedNickname = normalizedNickname.toLowerCase();

	    return normalizedNickname;
	}

	private static String removeAccent(String s) {
        String temp = java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < temp.length(); i++) {
            char c = temp.charAt(i);
            if (c < 128) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

	
//==================================================================================================================	
	
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
