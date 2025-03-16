package FileUtil;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileUtil {

    // Phương thức chuyển MultipartFile thành File tạm thời
    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // Tạo file tạm thời để lưu nội dung của MultipartFile
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + multipartFile.getOriginalFilename());
        
        // Ghi dữ liệu từ MultipartFile vào file tạm thời
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }
        return file;
    }
}
