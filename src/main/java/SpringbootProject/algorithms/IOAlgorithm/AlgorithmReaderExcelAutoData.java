//package SpringbootProject.algorithms.IOAlgorithm;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.*;
//
//@SpringBootApplication
//public class AlgorithmReaderExcelAutoData implements CommandLineRunner {
//
//    public static void main(String[] args) {
//        SpringApplication.run(ExcelReaderApplicationAutoData.class, args);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        String filePath = "D:\\Desktop\\Diary\\Users.xlsx";
//        List<Map<String, String>> records = readExcelFile(filePath);
//
//        // In kết quả ra console
//        for (Map<String, String> record : records) {
//            System.out.println(record);
//        }
//    }
//
//    private List<Map<String, String>> readExcelFile(String filePath) {
//        List<Map<String, String>> records = new ArrayList<>();
//
//        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
//            Workbook workbook = WorkbookFactory.create(fis);
//            Sheet sheet = workbook.getSheetAt(0);  // Lấy sheet đầu tiên
//
//            // Đọc dòng tiêu đề (header)
//            Row headerRow = sheet.getRow(0);
//            if (headerRow == null) {
//                throw new IllegalStateException("Tệp Excel không có dòng tiêu đề!");
//            }
//
//            // Lưu tên cột vào danh sách
//            List<String> headers = new ArrayList<>();
//            for (Cell cell : headerRow) {
//                headers.add(cell.getStringCellValue());
//            }
//
//            // Đọc dữ liệu từ các dòng tiếp theo, bắt đầu từ dòng thứ 2
//            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
//                Row row = sheet.getRow(rowIndex);
//                if (row == null || isRowEmpty(row)) {  // Kiểm tra nếu hàng trống
//                    continue;
//                }
//
//                Map<String, String> record = new LinkedHashMap<>();
//                for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
//                    Cell cell = row.getCell(colIndex);
//                    String cellValue = getCellValueAsString(cell);
//                    record.put(headers.get(colIndex), cellValue);
//                }
//                if (!record.isEmpty()) {  // Chỉ thêm bản ghi nếu có dữ liệu hợp lệ
//                    records.add(record);
//                }
//            }
//
//        } catch (IOException e) {
//            System.err.println("Lỗi khi đọc tệp Excel: " + e.getMessage());
//        }
//
//        return records;
//    }
//
//    // Hàm kiểm tra xem một dòng có dữ liệu hay không
//    private boolean isRowEmpty(Row row) {
//        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
//            Cell cell = row.getCell(i);
//            if (cell != null && !cell.toString().trim().isEmpty()) {
//                return false; // Dòng có dữ liệu
//            }
//        }
//        return true; // Dòng trống
//    }
//
//    // Hàm xử lý giá trị ô (cell) và trả về chuỗi tương ứng
//    private String getCellValueAsString(Cell cell) {
//        if (cell == null) {
//            return "";
//        }
//        switch (cell.getCellType()) {
//            case STRING:
//                return cell.getStringCellValue();  // Nếu là chuỗi, trả về chuỗi
//            case NUMERIC:
//                // Nếu là số, kiểm tra xem có phải ngày không
//                if (DateUtil.isCellDateFormatted(cell)) {
//                    return cell.getDateCellValue().toString();  // Nếu là ngày, chuyển đổi thành chuỗi ngày
//                } else {
//                    return String.valueOf(cell.getNumericCellValue());  // Nếu là số, chuyển thành chuỗi
//                }
//            case BOOLEAN:
//                return String.valueOf(cell.getBooleanCellValue());  // Nếu là boolean, chuyển thành chuỗi
//            default:
//                return "";  // Nếu không phải các kiểu trên, trả về chuỗi rỗng
//        }
//    }
//}






