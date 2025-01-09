package SpringbootProject.controller.TesController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // Cho phép truy cập từ frontend (Vue.js)
public class TestAxiosController {
	@Autowired
    private TestAxiosService mailService;

    @PostMapping("/send-all-gmailformstandard")
    public Map<String, Object> sendMailAll(
            @RequestParam(value = "subjectA", required = false) String subject,
            @RequestParam(value = "paragraph0", required = false) String paragraph0,
            @RequestParam(value = "paragraph1", required = false) String paragraph1,
            @RequestParam(value = "paragraph2", required = false) String paragraph2,
            @RequestParam(value = "paragraph3", required = false) String paragraph3,
            @RequestParam(value = "paragraph4", required = false) String paragraph4,
            @RequestParam("images") MultipartFile[] files
    ) throws IOException {
        // Gọi service để log dữ liệu ra console
        mailService.logReceivedData(subject, paragraph0, paragraph1, paragraph2, paragraph3, paragraph4, files);

        // Tạo phản hồi trả về client
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Data received successfully");
        response.put("received", Map.of(
                "subjectA", subject,
                "paragraph0", paragraph0,
                "paragraph1", paragraph1,
                "paragraph2", paragraph2,
                "paragraph3", paragraph3,
                "paragraph4", paragraph4,
                "numberOfFiles", files.length
        ));

        return response;
    }

}
