package SpringbootProject.controller.CRMControlers;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TestAxiosService {
	 public void logReceivedData(String subject, String paragraph0, String paragraph1, 
             String paragraph2, String paragraph3, String paragraph4, 
             MultipartFile[] files) {
System.out.println("=== Received Data ===");
System.out.println("Subject: " + subject);
System.out.println("Paragraph 0: " + paragraph0);
System.out.println("Paragraph 1: " + paragraph1);
System.out.println("Paragraph 2: " + paragraph2);
System.out.println("Paragraph 3: " + paragraph3);
System.out.println("Paragraph 4: " + paragraph4);

if (files != null) {
System.out.println("Number of Uploaded Files: " + files.length);
for (MultipartFile file : files) {
System.out.println("File Name: " + file.getOriginalFilename() + ", Size: " + file.getSize() + " bytes");
}
} else {
System.out.println("No files uploaded.");
}
System.out.println("=====================");
}

}
