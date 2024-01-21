package SpringbootProject.algorithms;

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

}
