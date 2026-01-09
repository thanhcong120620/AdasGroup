package SpringbootProject.algorithms.IOAlgorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat; // Vẫn cần để đọc số đúng cách
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

// Import lớp ExcelObject của bạn
import SpringbootProject.entity.notSaving.ExcelObject;

/**
 * Lớp đọc file Excel đơn giản, ánh xạ trực tiếp dữ liệu từ các ô sang đối tượng ExcelObject.
 * Không thực hiện xử lý số điện thoại hoặc logic phức tạp khác trong quá trình đọc.
 */
public class SimpleExcelReader {

    // Định dạng số để đọc số từ ô NUMERIC chính xác, tránh ".0" và "E" notation.
    private static final DecimalFormat df = new DecimalFormat("0");

    /**
     * Đọc file Excel từ đối tượng File và trả về danh sách ExcelObject.
     * Mỗi hàng trong Excel (sau header) sẽ tương ứng với một ExcelObject.
     * @param file Đối tượng File Excel cần đọc.
     * @return Danh sách các đối tượng ExcelObject chứa dữ liệu thô từ file.
     */
    public List<ExcelObject> readFile(File file) {
        List<ExcelObject> excelObjects = new ArrayList<>();

        if (file == null || !file.exists()) {
            System.err.println("SimpleExcelReader Error: File does not exist or is null.");
            return excelObjects; // Return empty list
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet

            if (sheet == null || sheet.getLastRowNum() < 0) {
                System.err.println("SimpleExcelReader Warning: First sheet is empty or does not exist.");
                return excelObjects;
            }

            // Bỏ qua header row (row 0), bắt đầu đọc từ row 1
            int firstDataRow = 1; // Assuming header is at row 0

            // Đọc dữ liệu từ các dòng, bắt đầu từ firstDataRow
            for (int rowIndex = firstDataRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null || isRowEmpty(row)) { // Skip null or empty rows
                    continue;
                }

                // Tạo một đối tượng ExcelObject cho mỗi hàng
                // Đọc tối đa 20 cột (index 0 đến 19)
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

                excelObjects.add(excelObject);
            }

        } catch (IOException e) {
            System.err.println("SimpleExcelReader IO Error reading Excel file: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        } catch (Exception e) { // Catch other potential errors (e.g., invalid file format)
            System.err.println("SimpleExcelReader Unexpected error reading Excel file: " + e.getMessage());
            e.printStackTrace();
        }

        return excelObjects;
    }

    /**
     * Đọc file Excel từ đường dẫn (filePath) và trả về danh sách ExcelObject.
     * Gọi lại phương thức readFile(File file).
     * @param filePath Đường dẫn đến file Excel.
     * @return Danh sách các đối tượng ExcelObject.
     */
    public List<ExcelObject> readFile(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
             System.err.println("SimpleExcelReader Error: File path is null or empty.");
             return new ArrayList<>();
        }
        return readFile(new File(filePath));
    }

    /**
     * Kiểm tra xem một dòng có hoàn toàn trống hay không.
     * (Giữ nguyên từ code trước vì nó hữu ích)
     * @param row Dòng cần kiểm tra.
     * @return true nếu dòng trống, false nếu có ít nhất một ô có dữ liệu.
     */
    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null && !getCellValueAsString(cell).trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Lấy giá trị của ô dưới dạng Chuỗi, xử lý các kiểu dữ liệu phổ biến.
     * (Giữ nguyên từ code trước vì nó xử lý tốt các kiểu dữ liệu)
     * @param cell Ô cần lấy giá trị.
     * @return Giá trị của ô dưới dạng Chuỗi, hoặc chuỗi rỗng nếu ô null hoặc không xử lý được.
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // Hoặc định dạng ngày cụ thể nếu muốn
                } else {
                    // Sử dụng DecimalFormat để đọc số chính xác
                    return df.format(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue().trim();
                } catch (IllegalStateException e) {
                    try {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            return cell.getDateCellValue().toString();
                        } else {
                            return df.format(cell.getNumericCellValue());
                        }
                    } catch (IllegalStateException e2) {
                         System.err.println("SimpleExcelReader: Cannot evaluate formula at " + cell.getAddress().formatAsString() + ": " + e2.getMessage());
                         return ""; // Return empty for formula errors
                    }
                }
            case BLANK:
                return "";
            default:
                 System.err.println("SimpleExcelReader: Unsupported cell type " + cell.getCellType() + " at " + cell.getAddress().formatAsString());
                return "";
        }
    }
}
