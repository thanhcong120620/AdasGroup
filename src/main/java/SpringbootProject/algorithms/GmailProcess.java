package SpringbootProject.algorithms;

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

}
