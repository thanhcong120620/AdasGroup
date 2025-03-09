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

import SpringbootProject.entity.notSaving.ExcelObject;

@SpringBootApplication
public class AlgorithmReaderExcel {
	

	
//	----------------------------------------Reading---------------------------------------------

    // Hàm đọc file Excel và trả về danh sách người dùng
    public List<ExcelObject> readExcelFile(String filePath) {
        List<ExcelObject> excelObjects = new ArrayList<>();

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

                // Lấy dữ liệu từ các ô và tạo đối tượng User
                String column1 = getCellValueAsString(row.getCell(0));  
                String column2 = getCellValueAsString(row.getCell(1));    
                String column3 = getCellValueAsString(row.getCell(2));     
                String column4 = getCellValueAsString(row.getCell(3));   
                String column5 = getCellValueAsString(row.getCell(4));    
                String column6 = getCellValueAsString(row.getCell(5));     
                String column7 = getCellValueAsString(row.getCell(6));  
                String column8 = getCellValueAsString(row.getCell(7));    
                String column9 = getCellValueAsString(row.getCell(8));     
                String column10 = getCellValueAsString(row.getCell(9));
                String column11 = getCellValueAsString(row.getCell(10));  
                String column12 = getCellValueAsString(row.getCell(11));    
                String column13 = getCellValueAsString(row.getCell(12));     
                String column14 = getCellValueAsString(row.getCell(13));   
                String column15 = getCellValueAsString(row.getCell(14));    
                String column16 = getCellValueAsString(row.getCell(15));     
                String column17 = getCellValueAsString(row.getCell(16));  
                String column18 = getCellValueAsString(row.getCell(17));    
                String column19 = getCellValueAsString(row.getCell(18));     
                String column20 = getCellValueAsString(row.getCell(19));  

                // Tạo đối tượng User và thêm vào danh sách
                ExcelObject excelObject = new ExcelObject();
                excelObject.setColumn1(column1);
                excelObject.setColumn2(column2);
                excelObject.setColumn3(column3);
                excelObject.setColumn4(column4);
                excelObject.setColumn5(column5);
                excelObject.setColumn6(column6);
                excelObject.setColumn7(column7);
                excelObject.setColumn8(column8);
                excelObject.setColumn9(column9);
                excelObject.setColumn10(column10);
                excelObject.setColumn11(column11);
                excelObject.setColumn12(column12);
                excelObject.setColumn13(column13);
                excelObject.setColumn14(column14);
                excelObject.setColumn15(column15);
                excelObject.setColumn16(column16);
                excelObject.setColumn17(column17);
                excelObject.setColumn18(column18);
                excelObject.setColumn19(column19);
                excelObject.setColumn20(column20);
                
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
