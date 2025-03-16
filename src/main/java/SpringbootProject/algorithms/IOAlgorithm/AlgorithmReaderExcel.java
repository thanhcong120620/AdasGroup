package SpringbootProject.algorithms.IOAlgorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import SpringbootProject.algorithms.GmailMKTAlgorithm.PhoneProcess;
import SpringbootProject.entity.notSaving.ExcelObject;

@SpringBootApplication
public class AlgorithmReaderExcel {
	

	
	
//	----------------------------------------Reading---------------------------------------------
	
	
    // Hàm đọc file Excel bằng file trực tiếp và trả về danh sách người dùng
	public List<ExcelObject> readExcelFile(File file) {
        List<ExcelObject> excelObjects = new ArrayList<>();
        PhoneProcess phoneProcess = new PhoneProcess();

//        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
          try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);  // Lấy sheet đầu tiên

            // Đọc dòng tiêu đề (header)
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("Tệp Excel không có dòng tiêu đề!");
            }

            // Lưu tên cột vào danh sách
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue());
            }

            // Đọc dữ liệu từ các dòng tiếp theo, bắt đầu từ dòng thứ 1
            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null || isRowEmpty(row)) {  // Kiểm tra nếu hàng trống
                    continue;
                }
                
                //Xử lý data từ excel
                List<String> phoneFromExcelList = phoneProcess.phoneStringProcessing(getCellValueAsString(row.getCell(0)));
               
                for (String phoneFromExcel : phoneFromExcelList) {
//                	System.out.println(phoneFromExcel);
                	
                	// Tạo đối tượng User và thêm vào danh sách
                    ExcelObject excelObject = new ExcelObject(phoneFromExcel,getCellValueAsString(row.getCell(1)),
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
                            getCellValueAsString(row.getCell(19)));
                    
//                    System.out.println(">> Check phone format" + excelObject.toString());
                    
                    excelObjects.add(excelObject);
                }

            }

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc tệp Excel: " + e.getMessage());
        }

        return excelObjects;
    }

    // Hàm đọc file Excel bằng path và trả về danh sách người dùng 
    public List<ExcelObject> readExcelFile(String filePath) {
        List<ExcelObject> excelObjects = new ArrayList<>();

//        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
          try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);  // Lấy sheet đầu tiên

            // Đọc dòng tiêu đề (header)
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("Tệp Excel không có dòng tiêu đề!");
            }

            // Lưu tên cột vào danh sách
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue());
            }

            // Đọc dữ liệu từ các dòng tiếp theo, bắt đầu từ dòng thứ 1
            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null || isRowEmpty(row)) {  // Kiểm tra nếu hàng trống
                    continue;
                }

             // Trực tiếp tạo đối tượng ExcelObject mà không cần mảng
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
                    getCellValueAsString(row.getCell(19))
                );


                
                excelObjects.add(excelObject);
            }

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc tệp Excel: " + e.getMessage());
        }

        return excelObjects;
    }

    // Hàm kiểm tra xem một dòng có dữ liệu hay không
    private boolean isRowEmpty(Row row) {
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && !cell.toString().trim().isEmpty()) {
                return false; // Dòng có dữ liệu
            }
        }
        return true; // Dòng trống
    }

    // Hàm xử lý giá trị ô (cell) và trả về chuỗi tương ứng
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();  // Nếu là chuỗi, trả về chuỗi
            case NUMERIC:
                // Nếu là số, kiểm tra xem có phải ngày không
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();  // Nếu là ngày, chuyển đổi thành chuỗi ngày
                } else {
                    return String.valueOf(cell.getNumericCellValue());  // Nếu là số, chuyển thành chuỗi
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());  // Nếu là boolean, chuyển thành chuỗi
            default:
                return "";  // Nếu không phải các kiểu trên, trả về chuỗi rỗng
        }
    }
}
