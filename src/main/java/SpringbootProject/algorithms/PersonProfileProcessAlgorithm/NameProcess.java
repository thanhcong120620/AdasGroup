package SpringbootProject.algorithms.PersonProfileProcessAlgorithm;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NameProcess {
	public NameProcess() {
	}
	
	
    

	
//=======================================================================================================	

	
	private static final String WORD_SEPARATOR = " ";
    private static final String[] ONLY_FIRST_NAMES_EXIT = {"Mai", "Hồng", "Kim", "Đạt", "Đức", "Ngọc", "Bích", "Xuân",
            "Tùng", "Thái", "Hạnh", "Công", "Loan", "Nhung", "Oanh", "Hoàng", "Minh", "Tuyết", "Liên", "Trang", "Phúc",
            "Phát", "Long", "Sơn"};

    // Tạo mảng ONLY_FIRST_NAMES_EXIT không dấu để so sánh
    private static final String[] ONLY_FIRST_NAMES_EXIT_NO_ACCENT;

    static {
        ONLY_FIRST_NAMES_EXIT_NO_ACCENT = new String[ONLY_FIRST_NAMES_EXIT.length];
        for (int i = 0; i < ONLY_FIRST_NAMES_EXIT.length; i++) {
            ONLY_FIRST_NAMES_EXIT_NO_ACCENT[i] = removeAccent(ONLY_FIRST_NAMES_EXIT[i]).toLowerCase();
        }
    }

    // Tạo mảng ONLY_FIRST_NAMES_EXIT không dấu để so sánh
    static String[] extractFirstNameAndMidFirstName(String fullName, String nickname) {
    	String convertFullname = removeAccent(fullName).toLowerCase();
        String convertNickName = removeAccent(nickname).toLowerCase();
        String[] arrayFullNamePartsOrigin = fullName.split(WORD_SEPARATOR);
        String[] arrayNickNamePartsOrigin = nickname.split(WORD_SEPARATOR);
        String[] arrayConvertFullNameParts = convertFullname.split(WORD_SEPARATOR);
        String[] arrayConvertNickNameParts = convertNickName.split(WORD_SEPARATOR);
        
        //Khởi tạo firstName từ Full Name
        	String firstNameAtFullnameConvert  = arrayConvertFullNameParts[arrayConvertFullNameParts.length-1];
        	String firstNameAtFullnameOrigin  = arrayFullNamePartsOrigin[arrayFullNamePartsOrigin.length-1];
        	String midNameAtFullnameOrigin  = convertToLowerCaseAndCapitalizeFirstLetter(arrayFullNamePartsOrigin[arrayFullNamePartsOrigin.length-2]);

        // Mảng để lưu trữ kết quả: {firstName, midNameAndFirstName, originalFirstName}
        String[] names = {convertToLowerCaseAndCapitalizeFirstLetter(firstNameAtFullnameOrigin), "ko xác định", ">>> ERROR",""}; // Giá trị mặc định
        	
            //Bước 1: Tìm tên khớp trong static Array
            for (int iarrstatic = 0; iarrstatic < ONLY_FIRST_NAMES_EXIT_NO_ACCENT.length; iarrstatic++) {
                if (firstNameAtFullnameConvert.equals(ONLY_FIRST_NAMES_EXIT_NO_ACCENT[iarrstatic])) {
                	names[0] = ONLY_FIRST_NAMES_EXIT[iarrstatic]; // Lấy tên có dấu tương ứng
                	names[1] = midNameAtFullnameOrigin + " " + ONLY_FIRST_NAMES_EXIT[iarrstatic];
                    names[2] = "Tên được tìm thấy từ static Array";
                    names[3] = names[0] = ONLY_FIRST_NAMES_EXIT[iarrstatic]; 
                	return names;
                }
            }
            
            //Bước 2: Ko tìm thấy firstname từ static Array thì qua kiểm tra với nickname

            	// Duyệt qua các cụm từ trong mảng nickname đã convert
            	for (int i = 0; i < arrayConvertNickNameParts.length; i++) {
            		String nicknamePart = arrayConvertNickNameParts[i];

            		//So sánh với firstName của convertFullname
            		if (nicknamePart.equals(firstNameAtFullnameConvert)) {
            			if(capitalizeFirstLetter(arrayNickNamePartsOrigin[i]).equals("Anh")) {
               	            for (int iarrstatic = 0; iarrstatic < ONLY_FIRST_NAMES_EXIT_NO_ACCENT.length; iarrstatic++) {
            	                if (removeAccent(midNameAtFullnameOrigin).toLowerCase().equals(ONLY_FIRST_NAMES_EXIT_NO_ACCENT[iarrstatic])) {
            	                	midNameAtFullnameOrigin = ONLY_FIRST_NAMES_EXIT[iarrstatic]; // Lấy tên có dấu tương ứng
            	                }
            	            }
            				names[0] = midNameAtFullnameOrigin + " " +capitalizeFirstLetter(arrayNickNamePartsOrigin[i]);
                            names[1] = "Tên \"Anh\" đặc biệt, đã lấy tên lót";
                            names[3] = midNameAtFullnameOrigin + " " +capitalizeFirstLetter(arrayNickNamePartsOrigin[i]);
            			} else {
            				names[0] = capitalizeFirstLetter(arrayNickNamePartsOrigin[i]);
                            names[1] = midNameAtFullnameOrigin + " " + capitalizeFirstLetter(arrayNickNamePartsOrigin[i]);
            				names[3] = capitalizeFirstLetter(arrayNickNamePartsOrigin[i]);
            			}
            			
            			// Trường hợp: Trùng với firstName của convertFullname thì kiểm tra tính hợp lệ
                        if (!capitalizeFirstLetter(arrayNickNamePartsOrigin[i]).equals(capitalizeFirstLetter(removeAccent(nicknamePart)))) {
                            // Tên có dấu --> Hợp lệ, giữ nguyên
                            names[2] = "Tên có dấu từ nick name !";
                            return names;
                        } else {
                        	// Tên ko có dấu and warning
                            names[2] = ">>> Waring: Tên không có dấu từ nick name !";
                            names[3] = "";
                            return names;
                        }
                  
            		}
        }

        return names;

}

    /*
     * convert name to lower case and Capitalize first letter
     * */
    static String convertToLowerCaseAndCapitalizeFirstLetter(String nickname) {
    	return capitalizeFirstLetter(nickname.toLowerCase());
    }
    
    
    /*
     * convert name to no accent name
     * */
     static String removeAccent(String s) {
        String temp = java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < temp.length(); i++) {
            char c = temp.charAt(i);
            if (c < 128) {
                sb.append(c);
            } else if (c=='Đ') {
            	sb.append('D');
            } else if (c=='đ') {
            	sb.append('d');
            } 
        }
        return sb.toString();
    }

    /*
     * "công" --> "Công"
     * */
    private static String capitalizeFirstLetter(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        String firstChar = name.substring(0, 1).toUpperCase();
        String rest = name.substring(1).toLowerCase();
        return firstChar + rest;
    }

	
//back up code:
/*	private static final String WORD_SEPARATOR = " ";
    private static final String[] ONLY_FIRST_NAMES_EXIT = {"Mai", "Hồng", "Kim", "Đạt", "Đức", "Ngọc", "Bích", "Xuân", 
    		"Tùng", "Thái", "Hạnh", "Tú", "Công", "Loan", "Nhung", "Oanh","Hoàng", "Minh", "Tuyết", "Liên", "Trang", "Phúc", 
    		"Phát", "Long", "Sơn"}; 
    

	
//=======================================================================================================	

	static String[] extractFirstNameAndMidFirstName (String fullName, String nickname) {
	    String convertFullname = convertVietnameseNameToEnglishName(fullName);
	    String convertNickName = convertVietnameseNameToEnglishName(nickname);
	    String[] fullNameParts = fullName.split(WORD_SEPARATOR);
	    String[] nickNameParts = nickname.split(WORD_SEPARATOR);
	    String[] convertFullNameParts = convertFullname.split(WORD_SEPARATOR);
	    String[] convertNickNameParts = convertNickName.split(WORD_SEPARATOR);

	    // Mảng để lưu trữ kết quả: {firstName, midNameAndFirstName, originalFirstName}
	    String[] names = {"ko xác định", "mid-name-và-first name-ko-xác-định", null}; // Giá trị mặc định

	    // Tập hợp các tên tiếng Việt không dấu phổ biến (có thể mở rộng)
	    

	    // Duyệt qua các cụm từ trong mảng nickname đã convert
	    for (int i = 0; i < convertNickNameParts.length; i++) {
	        String nicknamePart = convertNickNameParts[i];

	        // So sánh với các cụm từ trong mảng fullname đã convert
	        for (int j = 0; j < convertFullNameParts.length; j++) {
	            String fullnamePart = convertFullNameParts[j];

	            if (nicknamePart.equals(fullnamePart) && j == convertFullNameParts.length - 1) {
	                // Tìm thấy sự trùng khớp và trùng với firstname
	                names[0] = capitalizeFirstLetter(nickNameParts[i]); // Lấy firstname từ nickname
	                names[1] = capitalizeFirstLetter(fullNameParts[convertFullNameParts.length - 2]) + " " + capitalizeFirstLetter(nickNameParts[i]);

	                // Kiểm tra xem firstname có thuộc tập hợp tên không dấu hay không
	                boolean isCommonNoAccentName = Arrays.asList(ONLY_FIRST_NAMES_EXIT).contains(convertNickNameParts[i]);
	                if (isCommonNoAccentName) {
	                    // Nếu không thuộc tập hợp, lưu giá trị ban đầu và đặt thành "ko xác định"
	                    names[2] = names[0];
	                    names[0] = "ko xác định";
	                }

	                return names; // Tìm thấy và xử lý xong, trả về luôn
	            }
	        }
	    }
	    return names;
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

		//"công" --> "Công"
		private static String capitalizeFirstLetter (String name) {
			if (name == null || name.isEmpty()) {
		        return name;
		    }
		    String firstChar = name.substring(0, 1).toUpperCase();
		    String rest = name.substring(1).toLowerCase();
		    return firstChar + rest;
		}
	
	*/
	
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
