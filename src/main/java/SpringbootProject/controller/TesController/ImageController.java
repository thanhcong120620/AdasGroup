package SpringbootProject.controller.TesController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ImageController {

    // Đường dẫn thư mục để bạn lưu tệp
    private static final String FOLDER_PATH = "D:/Desktop/My data/1.My Working/1.IVC/2.ICV-Digital/1.Develop/1.IVCDevelop/2.BackEnd/2.IVCBackEnd/AdminSystem/src/main/resources/static/image/IVC-Realtor-Image/Gmail-MKT-Image/";

    // Trang để hiển thị form upload
    @GetMapping("/uploadTest")
    public String showUploadForm() {
        return "TestDevelop/uploadForm";
    }

    // Xử lý form khi người dùng upload nhiều hình ảnh
    @PostMapping("/uploadTest")
    public String handleFileUpload(@RequestParam("images") MultipartFile[] files, Model model) throws IOException {
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

            // Thêm đường dẫn đầy đủ vào danh sách
            imagePaths.add(fullPath);

            // Thay dấu "/" thành "\\"
            String modifiedPath = fullPath.replace("/", "\\");
            modifiedPaths.add(modifiedPath); // Thêm vào danh sách modifiedPaths

            // Lấy phần đường dẫn từ "image/" trở đi
            int startIdx = fullPath.indexOf("image/");
            if (startIdx != -1) {
                // Cắt phần từ "image/" trở đi
                String relativePath = fullPath.substring(startIdx);
                relativePaths.add(relativePath); // Thêm vào danh sách relativePaths
            }
        }

        // In ra console các đường dẫn mới để kiểm tra
        System.out.println("Original Paths:");
        for (String path : imagePaths) {
            System.out.println(path);
        }

        System.out.println("Modified Paths (with '\\'):");
        for (String modifiedPath : modifiedPaths) {
            System.out.println(modifiedPath);
        }

        System.out.println("Relative Paths (from 'image/' onward):");
        for (String relativePath : relativePaths) {
            System.out.println(relativePath);
        }

        // Gửi danh sách các đường dẫn hình ảnh đã thay đổi cho view
        model.addAttribute("modifiedPaths", modifiedPaths);
        model.addAttribute("imagePaths", relativePaths);

        return "TestDevelop/uploadResult";
    }
}
