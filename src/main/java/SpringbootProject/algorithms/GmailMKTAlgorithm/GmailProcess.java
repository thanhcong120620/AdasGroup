package SpringbootProject.algorithms.GmailMKTAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class GmailProcess {
	// Đường dẫn thư mục để bạn lưu tệp
    private static final String FOLDER_PATH = "D:/Desktop/My data/1.My Working/1.IVC/2.ICV-Digital/1.Develop/1.IVCDevelop/2.BackEnd/2.IVCBackEnd/AdminSystem/src/main/resources/static/image/IVC-Realtor-Image/Gmail-MKT-Image/";
	
	public GmailProcess() {}
	
	
	
	/*
	 * Name process
	 * */
	public String nameProcess(String mailName) {
		return "???";
	}
	
	
	
	/*
	 * Mail have blank
	 * Being used
	 * */
	public String deleteBlank(String mailInput) {
		return mailInput.replaceAll(" ", "");
	}
	
	
	/*
	 * Many mail in mail record
	 * Being used
	 * */
	public String[] splitMail(String mailInput) {
		String[] mailContainer = mailInput.split(";");
		String[] mailOutput = new String[mailContainer.length];
		for(int i=0; i<mailContainer.length;i++) {
			String mailElement = deleteBlank(mailContainer[i]);
			mailOutput[i] = mailElement;
		}
		return mailOutput;
	}
	
		/*
		 * Process Image in Mail content
		 * Being used
		 * */
	public List<String> gmailImageListFromFile (MultipartFile[] files) throws IOException {
		// Danh sách lưu trữ các đường dẫn tệp đã chọn
        List<String> imagePaths = new ArrayList<>();
        // Danh sách mới lưu trữ các đường dẫn đã thay đổi dấu "/" thành "\\"
        List<String> modifiedPaths = new ArrayList<>();
        // Danh sách lưu các đường dẫn cắt từ "image/" trở đi
        List<String> relativePaths = new ArrayList<>();

        // Lặp qua các tệp người dùng đã chọn
        for (MultipartFile file : files) {
            // Lấy tên tệp
            String fileName = file.getOriginalFilename();
            // Tạo đường dẫn đầy đủ tới tệp hình ảnh
            String fullPath = FOLDER_PATH + fileName;

            // Kiểm tra nếu thư mục chưa tồn tại thì tạo mới
            File dir = new File(FOLDER_PATH);
            if (!dir.exists()) {
                dir.mkdirs();  // Tạo các thư mục con nếu chưa có
            }

            // Lưu tệp vào thư mục đã chỉ định
            file.transferTo(new File(fullPath));

            // Thêm đường dẫn đầy đủ vào danh sách, sau đó lưu vào mục image của static rồi lấy đường dẫn để gửi mail
            imagePaths.add(fullPath);

            // Thay dấu "/" thành "\\", dùng để gửi mail
            String modifiedPath = fullPath.replace("/", "\\");
            modifiedPaths.add(modifiedPath); // Thêm vào danh sách modifiedPaths

            // Lấy phần đường dẫn từ "image/" trở đi, dùng để hiển thị trên thymleaf
            int startIdx = fullPath.indexOf("image/");
            if (startIdx != -1) {
                // Cắt phần từ "image/" trở đi
                String relativePath = fullPath.substring(startIdx);
                relativePaths.add(relativePath); // Thêm vào danh sách relativePaths
            }
        }

        // In ra console các đường dẫn mới để kiểm tra
//        System.out.println("Original Paths:");
//        for (String path : imagePaths) {
//            System.out.println(path);
//        }
//
//        System.out.println("Modified Paths (with '\\'):");
//        for (String modifiedPath : modifiedPaths) {
//            System.out.println(modifiedPath);
//        }
//
//        System.out.println("Relative Paths (from 'image/' onward):");
//        for (String relativePath : relativePaths) {
//            System.out.println(relativePath);
//        }
        
        return modifiedPaths;
		
	}
	
	
//------------------------------------------------------------------------------------------------------------------------------	
	/*
	 * Process Image in Mail content
	 * It's not be had used
	 * */
	public List<String> gmailImageList (){
		List<String> imageList	= new ArrayList<>();
		
		String AdsImage0 = "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\miaPoster1.png";
		imageList.add(AdsImage0);
		
//		String logo = "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\symphony_logo.png";
//		imageList.add(logo);
//		String image1 = "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\overview.jpg";
//		imageList.add(image1);
//		String image2 = "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\position.jpg";
//		imageList.add(image2);
//		String image3 = "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\amentities1.jpg";
//		imageList.add(image3);
//		String image4 = "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\sale-policy.jpg";
//		imageList.add(image4);
//		String image5 = "D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\amentities2.jpg";
//		imageList.add(image5);
		
	
	return imageList;
	}
	
	

}
