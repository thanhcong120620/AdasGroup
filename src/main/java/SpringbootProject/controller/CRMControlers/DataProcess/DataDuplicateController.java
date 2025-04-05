package SpringbootProject.controller.CRMControlers.DataProcess;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger; // Import Logger
import org.slf4j.LoggerFactory; // Import LoggerFactory
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import FileUtil.FileUtil; // Đảm bảo class này tồn tại và đúng vị trí
import SpringbootProject.algorithms.GmailMKTAlgorithm.PhoneProcess;
import SpringbootProject.algorithms.IOAlgorithm.IOFunction; // Đảm bảo class này tồn tại và đúng vị trí
import SpringbootProject.entity.notSaving.ExcelObject; // Đảm bảo class này tồn tại và đúng vị trí


@Controller
//@RequestMapping("/download") // Có thể bỏ comment nếu cần prefix chung
public class DataDuplicateController {

    // Logger để ghi lại thông tin và lỗi
    private static final Logger logger = LoggerFactory.getLogger(DataDuplicateController.class);

    // --- CẢNH BÁO: Sử dụng biến static cho state giữa các request là KHÔNG THREAD-SAFE ---
    // Trong môi trường sản xuất với nhiều người dùng đồng thời, các biến này sẽ bị ghi đè
    // dẫn đến người dùng có thể download nhầm file của người khác hoặc gặp lỗi.
    // Cần xem xét các giải pháp khác như lưu vào session, database, hoặc thư mục tạm với tên duy nhất.
    private static MultipartFile excelFileResponse;
    private static MultipartFile excelFileError;
    // --- KẾT THÚC CẢNH BÁO ---


    @GetMapping("/data-process")
    public String index(Model model) {
        // Có thể thêm logic xóa file cũ hoặc reset trạng thái ở đây nếu cần
        // Ví dụ: excelFileResponse = null; excelFileError = null;
        return "app/IVC-CRM/IVC-CRM-View/IVC-CRM-DataProcess/DataProcess";
    }


    @PostMapping("/uploadAndFilter")
    public String handleFileUpload(@RequestParam("excelFile1") MultipartFile file1,
                                   @RequestParam("excelFile2") MultipartFile file2,
                                   RedirectAttributes redirectAttributes, Model model) { // Bỏ throws nếu xử lý exception bên trong

        // --- Khởi tạo các đối tượng cần thiết ---
        IOFunction ioFunction = new IOFunction(); // Nên inject bằng @Autowired nếu IOFunction là Spring Bean
        PhoneProcess phoneProcess = new PhoneProcess(); // Nên inject bằng @Autowired nếu PhoneProcess là Spring Bean

        File tempFileOrigin = null;
        File tempFileFilter = null;
        List<ExcelObject> excelObjectListOrigin = null;
        List<ExcelObject> excelObjectListFilter = null;
        List<ExcelObject> filteredList = null; // Đổi tên biến để rõ ràng hơn
        List<ExcelObject> errorList = null;    // Đổi tên biến để rõ ràng hơn
        String[] countStatus = null;

        try {
            // --- Chuyển đổi MultipartFile thành File tạm ---
            // Lưu ý: FileUtil.convertMultipartFileToFile cần xử lý việc tạo file tạm an toàn và xóa sau khi dùng
            tempFileOrigin = FileUtil.convertMultipartFileToFile(file1);
            tempFileFilter = FileUtil.convertMultipartFileToFile(file2);

            // --- Đọc dữ liệu từ file Excel ---
            excelObjectListOrigin = ioFunction.getDataFromExcel(tempFileOrigin);
            excelObjectListFilter = ioFunction.getDataFromExcel(tempFileFilter);
//            for(ExcelObject excelObject : excelObjectListOrigin) {
//            	System.out.println(excelObject.getColumn1());
//            }
//            
            
            // --- Thực hiện lọc và xác thực bằng PhoneProcess (phiên bản mới) ---
            PhoneProcess.PhoneProcessingResult result = phoneProcess.filterAndValidatePhoneData(excelObjectListOrigin, excelObjectListFilter);

            // Lấy kết quả từ đối tượng result
            filteredList = result.getFilteredList();
            errorList = result.getRemovedItems();
            countStatus = result.getStatusMessages();

            // --- Ghi kết quả ra MultipartFile (lưu vào biến static - CẨN THẬN THREAD SAFETY) ---
            excelFileResponse = ioFunction.algorithmWitterMultipartFile(filteredList);
            excelFileError = ioFunction.algorithmWitterMultipartFile(errorList);

            // --- Thêm thông báo vào Model để hiển thị trên view ---
            if (countStatus != null && countStatus.length == 3) {
                model.addAttribute("statusMessage", countStatus[0]); // Trùng với gốc
                model.addAttribute("errorMessage", countStatus[1]);  // Trùng trong file lọc
                model.addAttribute("notValidMessage", countStatus[2]); // Không hợp lệ/rỗng
            } else {
                // Xử lý trường hợp countStatus không hợp lệ (ví dụ, ghi log)
                 logger.error("Mảng countStatus trả về từ PhoneProcess không hợp lệ.");
                 model.addAttribute("globalError", "Có lỗi xảy ra trong quá trình xử lý thống kê.");
            }
             model.addAttribute("processComplete", true); // Thêm cờ báo hiệu xử lý thành công


        } catch (IOException e) {
            logger.error("Lỗi IO trong quá trình xử lý file: {}", e.getMessage(), e);
            // redirectAttributes.addFlashAttribute("errorMessage", "Lỗi đọc hoặc ghi file: " + e.getMessage()); // Dùng RedirectAttributes nếu chuyển hướng
            model.addAttribute("globalError", "Lỗi đọc hoặc ghi file: " + e.getMessage()); // Dùng Model nếu trả về cùng view
             model.addAttribute("processComplete", false); // Thêm cờ báo hiệu xử lý thất bại
            // return "redirect:/data-process"; // Chuyển hướng về trang upload nếu lỗi
        } catch (IllegalStateException e) {
             logger.error("Lỗi trạng thái không hợp lệ (thường liên quan đến file): {}", e.getMessage(), e);
             model.addAttribute("globalError", "Lỗi xử lý file: " + e.getMessage());
             model.addAttribute("processComplete", false);
        } catch (Exception e) {
            // Bắt các lỗi khác không mong muốn
            logger.error("Lỗi không mong muốn xảy ra: {}", e.getMessage(), e);
            model.addAttribute("globalError", "Có lỗi hệ thống xảy ra trong quá trình xử lý.");
            model.addAttribute("processComplete", false);
        } finally {
            // --- Dọn dẹp file tạm (RẤT QUAN TRỌNG) ---
            if (tempFileOrigin != null && tempFileOrigin.exists()) {
                if (!tempFileOrigin.delete()) {
                     logger.warn("Không thể xóa file tạm: {}", tempFileOrigin.getAbsolutePath());
                }
            }
            if (tempFileFilter != null && tempFileFilter.exists()) {
                 if (!tempFileFilter.delete()) {
                     logger.warn("Không thể xóa file tạm: {}", tempFileFilter.getAbsolutePath());
                 }
            }
        }

        // Luôn trả về view xử lý, hiển thị kết quả hoặc thông báo lỗi qua Model
        return "app/IVC-CRM/IVC-CRM-View/IVC-CRM-DataProcess/DataProcess";
    }


    // Phương thức tải xuống file Excel 1
    @GetMapping("/excel-response")
    public ResponseEntity<ByteArrayResource> downloadExcelFile1() throws IOException {
        // --- CẢNH BÁO: Phụ thuộc vào biến static excelFileResponse ---
        if (excelFileResponse == null) {
             logger.warn("Yêu cầu tải file response nhưng excelFileResponse là null.");
             // Có thể trả về lỗi 404 hoặc thông báo khác
             return ResponseEntity.notFound().build(); // Hoặc trả về trang lỗi
        }

        ByteArrayResource resource = new ByteArrayResource(excelFileResponse.getBytes());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=excel_file-da-loc.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(excelFileResponse.getSize())
                .body(resource);
    }

    // Phương thức tải xuống file Excel 2
    @GetMapping("/excel-error")
    public ResponseEntity<ByteArrayResource> downloadExcel2(Model model) throws IOException {
         // --- CẢNH BÁO: Phụ thuộc vào biến static excelFileError ---
         if (excelFileError == null) {
             logger.warn("Yêu cầu tải file error nhưng excelFileError là null.");
             return ResponseEntity.notFound().build();
         }

        ByteArrayResource resource = new ByteArrayResource(excelFileError.getBytes());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=excelFileError.xlsx") // Đổi tên file nếu muốn
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(excelFileError.getSize())
                .body(resource);
    }
}