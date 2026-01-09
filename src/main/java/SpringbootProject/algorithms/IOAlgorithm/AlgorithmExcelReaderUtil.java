package SpringbootProject.algorithms.IOAlgorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat; // Import DecimalFormat
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.PersonProfileProcessFunction;
import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.PhoneProcess;
import SpringbootProject.entity.notSaving.ExcelObject; // Import ExcelObject

public class AlgorithmExcelReaderUtil {
	// Định dạng số để tránh phần thập phân và ký hiệu khoa học
    private static final DecimalFormat df = new DecimalFormat("0");
    
    private static final Logger logger = LoggerFactory.getLogger(AlgorithmExcelReaderUtil.class);
    private static final int MAX_COLUMNS_TO_PROCESS = 100;



    
    /**
     * Đầu vào là: File
     * Đọc file Excel từ đường dẫn (filePath) và trả về danh sách ExcelObject.
     * Phương thức này tạo một ExcelObject cho mỗi hàng dữ liệu (sau header).
     * @param filePath Đường dẫn đến file Excel.
     * @return Danh sách các đối tượng ExcelObject.
     */
    public List<ExcelObject> readExcelFile(String filePath) {
         return readExcelFileWithValidPhone(new File(filePath)); // Gọi lại phương thức trên để tránh lặp code
        
    }
    
    
    /**
     * Đầu vào là MultipartFile
     * Đọc file Excel từ đối tượng MultipartFile và trả về danh sách ExcelObject.
     * Mỗi hàng trong Excel (sau header) sẽ tương ứng với một ExcelObject.
     * Chỉ đọc dữ liệu, không thực hiện xử lý logic phức tạp.
     *
     * @param multipartFile Đối tượng MultipartFile chứa dữ liệu Excel cần đọc.
     * @return Danh sách các đối tượng ExcelObject.
     */
    public List<ExcelObject> readExcelFile(MultipartFile multipartFile) { // <-- Thay đổi parameter thành MultipartFile
        List<ExcelObject> excelObjects = new ArrayList<>();
        // Khởi tạo các đối tượng khác nếu cần (ví dụ PersonProfileProcessFunction)
        // PersonProfileProcessFunction personProfileProcessFunction = new PersonProfileProcessFunction();

        // --- Thay đổi kiểm tra đầu vào ---
        if (multipartFile == null || multipartFile.isEmpty()) {
            System.err.println("Lỗi: MultipartFile không tồn tại hoặc là null/rỗng.");
            return excelObjects; // Trả về danh sách rỗng
        }
        // ---------------------------------

        // --- Thay đổi cách lấy InputStream ---
        try (InputStream inputStream = multipartFile.getInputStream()) { // <-- Lấy InputStream từ MultipartFile
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên

            // Kiểm tra nếu sheet rỗng
            if (sheet == null || sheet.getLastRowNum() < 0) {
                 System.err.println("Cảnh báo: Sheet đầu tiên trong file Excel trống hoặc không tồn tại.");
                 return excelObjects;
            }
        // --------------------------------------

            // Đọc dòng tiêu đề (header) - Tùy chọn
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                System.err.println("Cảnh báo: Tệp Excel không có dòng tiêu đề (dòng 0).");
            }

            // Đọc dữ liệu từ các dòng tiếp theo, bắt đầu từ dòng thứ 1 (sau header)
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null || isRowEmpty(row)) { // Kiểm tra nếu hàng null hoặc trống
                    continue; // Bỏ qua hàng trống
                }

                // Tạo đối tượng ExcelObject trực tiếp từ dữ liệu ô
                ExcelObject excelObject = new ExcelObject(
                        getCellValueAsString(row.getCell(0)),
                        getCellValueAsString(row.getCell(1)),
                        getCellValueAsString(row.getCell(2)),
                        getCellValueAsString(row.getCell(3)),
                        getCellValueAsString(row.getCell(4)),
                        getCellValueAsString(row.getCell(5)),
                        getCellValueAsString(row.getCell(6)),
                        getCellValueAsString(row.getCell(7)),
                        getCellValueAsString(row.getCell(8)),
                        getCellValueAsString(row.getCell(9)),
                        getCellValueAsString(row.getCell(10)),
                        getCellValueAsString(row.getCell(11)),
                        getCellValueAsString(row.getCell(12)),
                        getCellValueAsString(row.getCell(13)),
                        getCellValueAsString(row.getCell(14)),
                        getCellValueAsString(row.getCell(15)),
                        getCellValueAsString(row.getCell(16)),
                        getCellValueAsString(row.getCell(17)),
                        getCellValueAsString(row.getCell(18)),
                        getCellValueAsString(row.getCell(19)),
                        getCellValueAsString(row.getCell(20)),
                        getCellValueAsString(row.getCell(21)),
                        getCellValueAsString(row.getCell(22)),
                        getCellValueAsString(row.getCell(23)),
                        getCellValueAsString(row.getCell(24)),
                        getCellValueAsString(row.getCell(25)),
                        getCellValueAsString(row.getCell(26)),
                        getCellValueAsString(row.getCell(27)),
                        getCellValueAsString(row.getCell(28)),
                        getCellValueAsString(row.getCell(29)),
                        getCellValueAsString(row.getCell(30)),
                        getCellValueAsString(row.getCell(31)),
                        getCellValueAsString(row.getCell(32)),
                        getCellValueAsString(row.getCell(33)),
                        getCellValueAsString(row.getCell(34)),
                        getCellValueAsString(row.getCell(35)),
                        getCellValueAsString(row.getCell(36)),
                        getCellValueAsString(row.getCell(37)),
                        getCellValueAsString(row.getCell(38)),
                        getCellValueAsString(row.getCell(39)),
                        getCellValueAsString(row.getCell(40)),
                        getCellValueAsString(row.getCell(41)),
                        getCellValueAsString(row.getCell(42)),
                        getCellValueAsString(row.getCell(43)),
                        getCellValueAsString(row.getCell(44)),
                        getCellValueAsString(row.getCell(45)),
                        getCellValueAsString(row.getCell(46)),
                        getCellValueAsString(row.getCell(47)),
                        getCellValueAsString(row.getCell(48)),
                        getCellValueAsString(row.getCell(49)),
                        getCellValueAsString(row.getCell(50)),
                        getCellValueAsString(row.getCell(51)),
                        getCellValueAsString(row.getCell(52)),
                        getCellValueAsString(row.getCell(53)),
                        getCellValueAsString(row.getCell(54)),
                        getCellValueAsString(row.getCell(55)),
                        getCellValueAsString(row.getCell(56)),
                        getCellValueAsString(row.getCell(57)),
                        getCellValueAsString(row.getCell(58)),
                        getCellValueAsString(row.getCell(59)),
                        getCellValueAsString(row.getCell(60)),
                        getCellValueAsString(row.getCell(61)),
                        getCellValueAsString(row.getCell(62)),
                        getCellValueAsString(row.getCell(63)),
                        getCellValueAsString(row.getCell(64)),
                        getCellValueAsString(row.getCell(65)),
                        getCellValueAsString(row.getCell(66)),
                        getCellValueAsString(row.getCell(67)),
                        getCellValueAsString(row.getCell(68)),
                        getCellValueAsString(row.getCell(69)),
                        getCellValueAsString(row.getCell(70)),
                        getCellValueAsString(row.getCell(71)),
                        getCellValueAsString(row.getCell(72)),
                        getCellValueAsString(row.getCell(73)),
                        getCellValueAsString(row.getCell(74)),
                        getCellValueAsString(row.getCell(75)),
                        getCellValueAsString(row.getCell(76)),
                        getCellValueAsString(row.getCell(77)),
                        getCellValueAsString(row.getCell(78)),
                        getCellValueAsString(row.getCell(79)),
                        getCellValueAsString(row.getCell(80)),
                        getCellValueAsString(row.getCell(81)),
                        getCellValueAsString(row.getCell(82)),
                        getCellValueAsString(row.getCell(83)),
                        getCellValueAsString(row.getCell(84)),
                        getCellValueAsString(row.getCell(85)),
                        getCellValueAsString(row.getCell(86)),
                        getCellValueAsString(row.getCell(87)),
                        getCellValueAsString(row.getCell(88)),
                        getCellValueAsString(row.getCell(89)),
                        getCellValueAsString(row.getCell(90)),
                        getCellValueAsString(row.getCell(91)),
                        getCellValueAsString(row.getCell(92)),
                        getCellValueAsString(row.getCell(93)),
                        getCellValueAsString(row.getCell(94)),
                        getCellValueAsString(row.getCell(95)),
                        getCellValueAsString(row.getCell(96)),
                        getCellValueAsString(row.getCell(97)),
                        getCellValueAsString(row.getCell(98)),
                        getCellValueAsString(row.getCell(99))
                );
                // Thêm đối tượng vào danh sách kết quả
                excelObjects.add(excelObject);
            }

        } catch (IOException e) {
            System.err.println("Lỗi IO khi đọc tệp Excel từ MultipartFile: " + e.getMessage());
            e.printStackTrace(); // In stack trace để debug
        } catch (Exception e) { // Bắt các lỗi khác như định dạng file không hợp lệ
            System.err.println("Lỗi không mong muốn khi đọc file Excel từ MultipartFile: " + e.getMessage());
            e.printStackTrace();
        }

        return excelObjects;
    }
    
    
    
//	----------------------------------------Main Reading Function--------------------------------------------- 
    
    /**
     * ĐỌC VÀ XỬ LÝ Các sheet excel bị trùng dữ liệu
     * Đọc file Excel từ đối tượng File và trả về danh sách ExcelObject.
     * @param file Đối tượng File Excel cần đọc.
     * @return Danh sách các đối tượng ExcelObject.
     */
    public List<ExcelObject> processExcelFileAndMerge(MultipartFile file) throws IOException {
        // Validation cơ bản (Controller cũng nên làm)
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File không được để trống");
        }
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".xlsx")) {
             throw new IllegalArgumentException("Chỉ hỗ trợ định dạng file .xlsx");
        }

        List<ExcelObject> allExcelData = new ArrayList<>();
        InputStream inputStream = file.getInputStream();

        // Logic xử lý Excel giống hệt như trong Service trước đây
        try (InputStream is = inputStream; Workbook workbook = new XSSFWorkbook(is)) {
            DataFormatter dataFormatter = new DataFormatter();

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                logger.info("(Algorithm) Đang xử lý sheet: {}", sheet.getSheetName());

                try {
                    int headerRowIndex = findHeaderRowIndex(sheet);
                    if (headerRowIndex == -1) {
                        logger.warn("(Algorithm) Không tìm thấy header trong sheet '{}'. Bỏ qua.", sheet.getSheetName());
                        continue;
                    }
                    Row headerRow = sheet.getRow(headerRowIndex);
                    if (headerRow == null) continue; // Bỏ qua nếu dòng header null

                    Map<Integer, String> headerMap = getHeaderMap(headerRow, dataFormatter);
                    if (headerMap.isEmpty()) {
                         logger.warn("(Algorithm) Header rỗng trong sheet '{}'. Bỏ qua.", sheet.getSheetName());
                         continue;
                    }

                    List<Integer> columnIndices = new ArrayList<>(headerMap.keySet());
                     logger.debug("(Algorithm) Sheet '{}' headers: {}", sheet.getSheetName(), headerMap);


                    for (int r = headerRowIndex + 1; r <= sheet.getLastRowNum(); r++) {
                        Row currentRow = sheet.getRow(r);
                        if (currentRow == null) continue;

                        ExcelObject excelObject = new ExcelObject();
                        boolean rowHasData = false;
                        int objectFieldIndex = 1;

                        for (Integer currentColumnIndex : columnIndices) {
                            if (objectFieldIndex > MAX_COLUMNS_TO_PROCESS) break;
                            String cellValue = getCellValueAsString(currentRow, currentColumnIndex, dataFormatter);
                            setExcelObjectColumn(excelObject, objectFieldIndex, cellValue);
                            if (cellValue != null && !cellValue.isEmpty()) rowHasData = true;
                            objectFieldIndex++;
                        }
                        if (rowHasData) allExcelData.add(excelObject);
                    }
                } catch (Exception e) {
                    logger.error("(Algorithm) Lỗi xử lý sheet '{}': {}", sheet.getSheetName(), e.getMessage(), e);
                }
            }
        }

        logger.info("(Algorithm) Xử lý file '{}' hoàn tất. Trích xuất {} bản ghi.", filename, allExcelData.size());
        return allExcelData;
    }

    
    
    /**
     * ĐỌC VÀ XỬ LÝ SỐ ĐIỆN THOẠI
     * Đọc file Excel từ đối tượng File và trả về danh sách ExcelObject.
     * Xử lý trường hợp một ô có thể chứa nhiều số điện thoại.
     * Tạo một ExcelObject cho mỗi số điện thoại tìm thấy trong ô đầu tiên của hàng,
     * sao chép dữ liệu từ các ô còn lại của hàng đó.
     * @param file Đối tượng File Excel cần đọc.
     * @return Danh sách các đối tượng ExcelObject.
     */
    public List<ExcelObject> readExcelFileWithValidPhone(File file) {
        List<ExcelObject> excelObjects = new ArrayList<>();
        // Khởi tạo PhoneProcess để xử lý chuỗi số điện thoại
//      PhoneProcess phoneProcess = new PhoneProcess();
        PersonProfileProcessFunction personProfileProcessFunction = new PersonProfileProcessFunction();

        if (file == null || !file.exists()) {
            System.err.println("Lỗi: File không tồn tại hoặc là null.");
            return excelObjects; // Trả về danh sách rỗng
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên

            // Kiểm tra nếu sheet rỗng
            if (sheet.getLastRowNum() < 0) {
                 System.err.println("Cảnh báo: Sheet đầu tiên trong file Excel trống.");
                 return excelObjects;
            }

            // Đọc dòng tiêu đề (header) - Tùy chọn, có thể bỏ qua nếu không cần dùng headers
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                // Có thể ném lỗi hoặc chỉ ghi log cảnh báo tùy yêu cầu
                System.err.println("Cảnh báo: Tệp Excel không có dòng tiêu đề (dòng 0).");
            }

            // Đọc dữ liệu từ các dòng tiếp theo, bắt đầu từ dòng thứ 1 (sau header)
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null || isRowEmpty(row)) { // Kiểm tra nếu hàng null hoặc trống
                    continue; // Bỏ qua hàng trống
                }

                // Lấy giá trị từ ô đầu tiên (cột 0) và xử lý để lấy danh sách SĐT
                String rawPhoneData = getCellValueAsString(row.getCell(0));
                List<String> phoneFromExcelList = personProfileProcessFunction.phoneProcessExtractAndValidateVietnameseNumbers(rawPhoneData);
                
                // Nếu ô không chứa số điện thoại nào hợp lệ sau khi xử lý, bỏ qua hàng này
                if (phoneFromExcelList.isEmpty()) {
                    System.out.println("Cảnh báo: Hàng " + (rowIndex + 1) + " không chứa số điện thoại hợp lệ trong cột đầu tiên. Dữ liệu gốc: '" + rawPhoneData + "'");
                    continue;
                }

                // Với mỗi số điện thoại tìm được trong ô đầu tiên
                for (String phoneFromExcel : phoneFromExcelList) {
                    // Tạo đối tượng ExcelObject với số điện thoại đã xử lý
                    // và dữ liệu từ các ô còn lại của cùng một hàng
                    ExcelObject excelObject = new ExcelObject(
                            phoneFromExcel, // Số điện thoại đã xử lý
                            getCellValueAsString(row.getCell(1)),
                            getCellValueAsString(row.getCell(2)),
                            getCellValueAsString(row.getCell(3)),
                            getCellValueAsString(row.getCell(4)),
                            getCellValueAsString(row.getCell(5)),
                            getCellValueAsString(row.getCell(6)),
                            getCellValueAsString(row.getCell(7)),
                            getCellValueAsString(row.getCell(8)),
                            getCellValueAsString(row.getCell(9)),
                            getCellValueAsString(row.getCell(10)),
                            getCellValueAsString(row.getCell(11)),
                            getCellValueAsString(row.getCell(12)),
                            getCellValueAsString(row.getCell(13)),
                            getCellValueAsString(row.getCell(14)),
                            getCellValueAsString(row.getCell(15)),
                            getCellValueAsString(row.getCell(16)),
                            getCellValueAsString(row.getCell(17)),
                            getCellValueAsString(row.getCell(18)),
                            getCellValueAsString(row.getCell(19)),
                            getCellValueAsString(row.getCell(20)),
                            getCellValueAsString(row.getCell(21)),
                            getCellValueAsString(row.getCell(22)),
                            getCellValueAsString(row.getCell(23)),
                            getCellValueAsString(row.getCell(24)),
                            getCellValueAsString(row.getCell(25)),
                            getCellValueAsString(row.getCell(26)),
                            getCellValueAsString(row.getCell(27)),
                            getCellValueAsString(row.getCell(28)),
                            getCellValueAsString(row.getCell(29)),
                            getCellValueAsString(row.getCell(30)),
                            getCellValueAsString(row.getCell(31)),
                            getCellValueAsString(row.getCell(32)),
                            getCellValueAsString(row.getCell(33)),
                            getCellValueAsString(row.getCell(34)),
                            getCellValueAsString(row.getCell(35)),
                            getCellValueAsString(row.getCell(36)),
                            getCellValueAsString(row.getCell(37)),
                            getCellValueAsString(row.getCell(38)),
                            getCellValueAsString(row.getCell(39)),
                            getCellValueAsString(row.getCell(40)),
                            getCellValueAsString(row.getCell(41)),
                            getCellValueAsString(row.getCell(42)),
                            getCellValueAsString(row.getCell(43)),
                            getCellValueAsString(row.getCell(44)),
                            getCellValueAsString(row.getCell(45)),
                            getCellValueAsString(row.getCell(46)),
                            getCellValueAsString(row.getCell(47)),
                            getCellValueAsString(row.getCell(48)),
                            getCellValueAsString(row.getCell(49)),
                            getCellValueAsString(row.getCell(50)),
                            getCellValueAsString(row.getCell(51)),
                            getCellValueAsString(row.getCell(52)),
                            getCellValueAsString(row.getCell(53)),
                            getCellValueAsString(row.getCell(54)),
                            getCellValueAsString(row.getCell(55)),
                            getCellValueAsString(row.getCell(56)),
                            getCellValueAsString(row.getCell(57)),
                            getCellValueAsString(row.getCell(58)),
                            getCellValueAsString(row.getCell(59)),
                            getCellValueAsString(row.getCell(60)),
                            getCellValueAsString(row.getCell(61)),
                            getCellValueAsString(row.getCell(62)),
                            getCellValueAsString(row.getCell(63)),
                            getCellValueAsString(row.getCell(64)),
                            getCellValueAsString(row.getCell(65)),
                            getCellValueAsString(row.getCell(66)),
                            getCellValueAsString(row.getCell(67)),
                            getCellValueAsString(row.getCell(68)),
                            getCellValueAsString(row.getCell(69)),
                            getCellValueAsString(row.getCell(70)),
                            getCellValueAsString(row.getCell(71)),
                            getCellValueAsString(row.getCell(72)),
                            getCellValueAsString(row.getCell(73)),
                            getCellValueAsString(row.getCell(74)),
                            getCellValueAsString(row.getCell(75)),
                            getCellValueAsString(row.getCell(76)),
                            getCellValueAsString(row.getCell(77)),
                            getCellValueAsString(row.getCell(78)),
                            getCellValueAsString(row.getCell(79)),
                            getCellValueAsString(row.getCell(80)),
                            getCellValueAsString(row.getCell(81)),
                            getCellValueAsString(row.getCell(82)),
                            getCellValueAsString(row.getCell(83)),
                            getCellValueAsString(row.getCell(84)),
                            getCellValueAsString(row.getCell(85)),
                            getCellValueAsString(row.getCell(86)),
                            getCellValueAsString(row.getCell(87)),
                            getCellValueAsString(row.getCell(88)),
                            getCellValueAsString(row.getCell(89)),
                            getCellValueAsString(row.getCell(90)),
                            getCellValueAsString(row.getCell(91)),
                            getCellValueAsString(row.getCell(92)),
                            getCellValueAsString(row.getCell(93)),
                            getCellValueAsString(row.getCell(94)),
                            getCellValueAsString(row.getCell(95)),
                            getCellValueAsString(row.getCell(96)),
                            getCellValueAsString(row.getCell(97)),
                            getCellValueAsString(row.getCell(98)),
                            getCellValueAsString(row.getCell(99))
                    );
                    // Thêm đối tượng vào danh sách kết quả
                    excelObjects.add(excelObject);
                }
            }

        } catch (IOException e) {
            System.err.println("Lỗi IO khi đọc tệp Excel: " + e.getMessage());
            e.printStackTrace(); // In stack trace để debug
        } catch (IllegalStateException e) {
            // Bắt lỗi cụ thể hơn nếu cần, ví dụ lỗi định dạng file
            System.err.println("Lỗi trạng thái khi xử lý file Excel: " + e.getMessage());
             e.printStackTrace();
        } catch (Exception e) {
            // Bắt các lỗi không mong muốn khác
            System.err.println("Lỗi không mong muốn khi đọc file Excel: " + e.getMessage());
            e.printStackTrace();
        }

        return excelObjects;
    }


    
//===========================================HELPER FUNCTION - KO SỬA===========================================================       
    
    
//================logic nội bộ phụ trợ - không thể thay đổi=============================================    
    
    /**
     * Kiểm tra xem một dòng có hoàn toàn trống hay không.
     * @param row Dòng cần kiểm tra.
     * @return true nếu dòng trống, false nếu có ít nhất một ô có dữ liệu.
     */
    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        // Duyệt qua các ô trong khoảng từ ô đầu tiên đến ô cuối cùng có dữ liệu
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL); // Lấy ô, nếu thiếu thì trả về null
            if (cell != null && !getCellValueAsString(cell).trim().isEmpty()) {
                 // Nếu tìm thấy một ô không null và không trống sau khi trim, dòng không trống
                return false;
            }
        }
        // Nếu duyệt hết mà không tìm thấy ô nào có dữ liệu, dòng là trống
        return true;
    }


    /**
     * Lấy giá trị của ô dưới dạng Chuỗi, xử lý các kiểu dữ liệu phổ biến.
     * @param cell Ô cần lấy giá trị.
     * @return Giá trị của ô dưới dạng Chuỗi, hoặc chuỗi rỗng nếu ô null hoặc không xử lý được.
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim(); // Loại bỏ khoảng trắng thừa
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Xử lý ngày tháng nếu cần định dạng cụ thể
                    // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    // return sdf.format(cell.getDateCellValue());
                    return cell.getDateCellValue().toString();
                } else {
                    // Sử dụng DecimalFormat để tránh ".0" và "E" notation
                    return df.format(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                // Cố gắng lấy giá trị tính toán của công thức
                try {
                    // Thử lấy giá trị chuỗi trước (nếu công thức trả về chuỗi)
                    return cell.getStringCellValue().trim();
                } catch (IllegalStateException e) {
                    // Nếu lỗi (thường là do công thức trả về số), lấy giá trị số
                    try {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            return cell.getDateCellValue().toString();
                        } else {
                            return df.format(cell.getNumericCellValue());
                        }
                    } catch (IllegalStateException e2) {
                         // Nếu vẫn lỗi (ví dụ công thức lỗi #N/A), trả về chuỗi rỗng hoặc thông báo lỗi
                         System.err.println("Không thể đánh giá công thức tại ô: " + cell.getAddress().formatAsString() + " Lỗi: " + e2.getMessage());
                         return ""; // Hoặc cell.getErrorCellValue() nếu muốn mã lỗi
                    }
                }
            case BLANK: // Xử lý ô trống rõ ràng
                return "";
            default: // Các kiểu khác (ERROR, _NONE)
                 System.err.println("Kiểu ô không được xử lý: " + cell.getCellType() + " tại " + cell.getAddress().formatAsString());
                return "";
        }
    }
    
 
    // --- Các phương thức helper giữ nguyên như trong Service ---
    private int findHeaderRowIndex(Sheet sheet) {
        DataFormatter dataFormatter = new DataFormatter();
        final int MIN_HEADERS = 2;
        final int MAX_ROWS_TO_CHECK = 10;
        for (int rowIndex = 0; rowIndex <= Math.min(MAX_ROWS_TO_CHECK, sheet.getLastRowNum()); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) continue;
            int nonEmptyCellCount = 0;
            short lastCellNum = row.getLastCellNum();
            for (int cellIdx = row.getFirstCellNum(); cellIdx >= 0 && cellIdx < lastCellNum && cellIdx < MAX_COLUMNS_TO_PROCESS + 5 ; cellIdx++) {
                Cell cell = row.getCell(cellIdx);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    String cellValue = dataFormatter.formatCellValue(cell).trim();
                    if (!cellValue.isEmpty()) nonEmptyCellCount++;
                }
            }
            if (nonEmptyCellCount >= MIN_HEADERS) return rowIndex;
        }
        return -1;
    }

    private Map<Integer, String> getHeaderMap(Row headerRow, DataFormatter dataFormatter) {
        Map<Integer, String> headerMap = new LinkedHashMap<>();
        short firstCellNum = headerRow.getFirstCellNum();
        short lastCellNum = headerRow.getLastCellNum();
        if (firstCellNum < 0) return headerMap;
        int headerCount = 0;
        for (int colIdx = firstCellNum; colIdx < lastCellNum && headerCount < MAX_COLUMNS_TO_PROCESS + 5 ; colIdx++) {
            Cell cell = headerRow.getCell(colIdx);
            String headerText = "";
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                headerText = dataFormatter.formatCellValue(cell).trim();
            }
            if(!headerText.isEmpty()){
                headerMap.put(colIdx, headerText);
                headerCount++;
            }
        }
        return headerMap;
    }

    private String getCellValueAsString(Row row, int columnIndex, DataFormatter dataFormatter) {
        if (row == null) return null;
        Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null || cell.getCellType() == CellType.BLANK) return null;
        String value = dataFormatter.formatCellValue(cell).trim();
        return value.isEmpty() ? null : value;
    }

    private void setExcelObjectColumn(ExcelObject excelObject, int fieldIndex, String value) {
        switch (fieldIndex) {
            case 1: excelObject.setColumn1(value); break;
            case 2: excelObject.setColumn2(value); break;
            case 3: excelObject.setColumn3(value); break;
            case 4: excelObject.setColumn4(value); break;
            case 5: excelObject.setColumn5(value); break;
            case 6: excelObject.setColumn6(value); break;
            case 7: excelObject.setColumn7(value); break;
            case 8: excelObject.setColumn8(value); break;
            case 9: excelObject.setColumn9(value); break;
            case 10: excelObject.setColumn10(value); break;
            case 11: excelObject.setColumn11(value); break;
            case 12: excelObject.setColumn12(value); break;
            case 13: excelObject.setColumn13(value); break;
            case 14: excelObject.setColumn14(value); break;
            case 15: excelObject.setColumn15(value); break;
            case 16: excelObject.setColumn16(value); break;
            case 17: excelObject.setColumn17(value); break;
            case 18: excelObject.setColumn18(value); break;
            case 19: excelObject.setColumn19(value); break;
            case 20: excelObject.setColumn20(value); break;
            case 21: excelObject.setColumn21(value); break;
            case 22: excelObject.setColumn22(value); break;
            case 23: excelObject.setColumn23(value); break;
            case 24: excelObject.setColumn24(value); break;
            case 25: excelObject.setColumn25(value); break;
            case 26: excelObject.setColumn26(value); break;
            case 27: excelObject.setColumn27(value); break;
            case 28: excelObject.setColumn28(value); break;
            case 29: excelObject.setColumn29(value); break;
            case 30: excelObject.setColumn30(value); break;
            case 31: excelObject.setColumn31(value); break;
            case 32: excelObject.setColumn32(value); break;
            case 33: excelObject.setColumn33(value); break;
            case 34: excelObject.setColumn34(value); break;
            case 35: excelObject.setColumn35(value); break;
            case 36: excelObject.setColumn36(value); break;
            case 37: excelObject.setColumn37(value); break;
            case 38: excelObject.setColumn38(value); break;
            case 39: excelObject.setColumn39(value); break;
            case 40: excelObject.setColumn40(value); break;
            case 41: excelObject.setColumn41(value); break;
            case 42: excelObject.setColumn42(value); break;
            case 43: excelObject.setColumn43(value); break;
            case 44: excelObject.setColumn44(value); break;
            case 45: excelObject.setColumn45(value); break;
            case 46: excelObject.setColumn46(value); break;
            case 47: excelObject.setColumn47(value); break;
            case 48: excelObject.setColumn48(value); break;
            case 49: excelObject.setColumn49(value); break;
            case 50: excelObject.setColumn50(value); break;
            case 51: excelObject.setColumn51(value); break;
            case 52: excelObject.setColumn52(value); break;
            case 53: excelObject.setColumn53(value); break;
            case 54: excelObject.setColumn54(value); break;
            case 55: excelObject.setColumn55(value); break;
            case 56: excelObject.setColumn56(value); break;
            case 57: excelObject.setColumn57(value); break;
            case 58: excelObject.setColumn58(value); break;
            case 59: excelObject.setColumn59(value); break;
            case 60: excelObject.setColumn60(value); break;
            case 61: excelObject.setColumn61(value); break;
            case 62: excelObject.setColumn62(value); break;
            case 63: excelObject.setColumn63(value); break;
            case 64: excelObject.setColumn64(value); break;
            case 65: excelObject.setColumn65(value); break;
            case 66: excelObject.setColumn66(value); break;
            case 67: excelObject.setColumn67(value); break;
            case 68: excelObject.setColumn68(value); break;
            case 69: excelObject.setColumn69(value); break;
            case 70: excelObject.setColumn70(value); break;
            case 71: excelObject.setColumn71(value); break;
            case 72: excelObject.setColumn72(value); break;
            case 73: excelObject.setColumn73(value); break;
            case 74: excelObject.setColumn74(value); break;
            case 75: excelObject.setColumn75(value); break;
            case 76: excelObject.setColumn76(value); break;
            case 77: excelObject.setColumn77(value); break;
            case 78: excelObject.setColumn78(value); break;
            case 79: excelObject.setColumn79(value); break;
            case 80: excelObject.setColumn80(value); break;
            case 81: excelObject.setColumn81(value); break;
            case 82: excelObject.setColumn82(value); break;
            case 83: excelObject.setColumn83(value); break;
            case 84: excelObject.setColumn84(value); break;
            case 85: excelObject.setColumn85(value); break;
            case 86: excelObject.setColumn86(value); break;
            case 87: excelObject.setColumn87(value); break;
            case 88: excelObject.setColumn88(value); break;
            case 89: excelObject.setColumn89(value); break;
            case 90: excelObject.setColumn90(value); break;
            case 91: excelObject.setColumn91(value); break;
            case 92: excelObject.setColumn92(value); break;
            case 93: excelObject.setColumn93(value); break;
            case 94: excelObject.setColumn94(value); break;
            case 95: excelObject.setColumn95(value); break;
            case 96: excelObject.setColumn96(value); break;
            case 97: excelObject.setColumn97(value); break;
            case 98: excelObject.setColumn98(value); break;
            case 99: excelObject.setColumn99(value); break;
            case 100: excelObject.setColumn100(value); break;
            default: break;
        }
    }
}
