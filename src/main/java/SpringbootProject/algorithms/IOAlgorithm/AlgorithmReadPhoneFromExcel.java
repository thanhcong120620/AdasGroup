package SpringbootProject.algorithms.IOAlgorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat; // Import DecimalFormat
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
// Bỏ import @SpringBootApplication vì không cần thiết
// import org.springframework.boot.autoconfigure.SpringBootApplication;

import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.PhoneProcess;
import SpringbootProject.entity.notSaving.ExcelObject;

// Bỏ @SpringBootApplication
public class AlgorithmReadPhoneFromExcel {

    // Định dạng số để tránh phần thập phân và ký hiệu khoa học
    private static final DecimalFormat df = new DecimalFormat("0");

    //	----------------------------------------Reading---------------------------------------------

    /**
     * Đọc file Excel từ đối tượng File và trả về danh sách ExcelObject.
     * Xử lý trường hợp một ô có thể chứa nhiều số điện thoại.
     * Tạo một ExcelObject cho mỗi số điện thoại tìm thấy trong ô đầu tiên của hàng,
     * sao chép dữ liệu từ các ô còn lại của hàng đó.
     * @param file Đối tượng File Excel cần đọc.
     * @return Danh sách các đối tượng ExcelObject.
     */
    public List<ExcelObject> readExcelFile(File file) {
        List<ExcelObject> excelObjects = new ArrayList<>();
        // Khởi tạo PhoneProcess để xử lý chuỗi số điện thoại
        PhoneProcess phoneProcess = new PhoneProcess();

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
                List<String> phoneFromExcelList = phoneProcess.extractAndValidateVietnameseNumbers(rawPhoneData);

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
                            getCellValueAsString(row.getCell(19))
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

    /**
     * Đọc file Excel từ đường dẫn (filePath) và trả về danh sách ExcelObject.
     * Phương thức này tạo một ExcelObject cho mỗi hàng dữ liệu (sau header).
     * @param filePath Đường dẫn đến file Excel.
     * @return Danh sách các đối tượng ExcelObject.
     */
    public List<ExcelObject> readExcelFile(String filePath) {
         return readExcelFile(new File(filePath)); // Gọi lại phương thức trên để tránh lặp code
         /*
         // Hoặc giữ nguyên logic cũ nếu muốn 2 phương thức hoạt động khác nhau
        List<ExcelObject> excelObjects = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);

             if (sheet.getLastRowNum() < 0) {
                 System.err.println("Cảnh báo: Sheet đầu tiên trong file Excel trống.");
                 return excelObjects;
             }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                System.err.println("Cảnh báo: Tệp Excel không có dòng tiêu đề (dòng 0).");
            }

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) { // Bắt đầu từ hàng 1
                Row row = sheet.getRow(rowIndex);
                if (row == null || isRowEmpty(row)) {
                    continue;
                }

                ExcelObject excelObject = new ExcelObject(
                    getCellValueAsString(row.getCell(0)),
                    getCellValueAsString(row.getCell(1)),
                    // ... các cột còn lại ...
                    getCellValueAsString(row.getCell(19))
                );
                excelObjects.add(excelObject);
            }
        } catch (IOException e) {
            System.err.println("Lỗi IO khi đọc tệp Excel từ path: " + e.getMessage());
             e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Lỗi không mong muốn khi đọc file Excel từ path: " + e.getMessage());
            e.printStackTrace();
        }
        return excelObjects;
        */
    }

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
}