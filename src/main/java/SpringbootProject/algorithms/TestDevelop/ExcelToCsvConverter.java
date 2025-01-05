package SpringbootProject.algorithms.TestDevelop;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExcelToCsvConverter {

    public static MultipartFile convertExcelToCsv(MultipartFile multipartFile) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
             ByteArrayOutputStream csvOutputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                for (int i = 0; i < 5; i++) { // Giả sử bạn muốn chuyển đổi 4 cột đầu tiên
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                csvOutputStream.write(cell.getStringCellValue().getBytes());
                                break;
                            case NUMERIC:
                                csvOutputStream.write(String.valueOf(cell.getNumericCellValue()).getBytes());
                                break;
                            case BOOLEAN:
                                csvOutputStream.write(String.valueOf(cell.getBooleanCellValue()).getBytes());
                                break;
                            case FORMULA:
                                csvOutputStream.write(cell.getCellFormula().getBytes());
                                break;
                            default:
                                csvOutputStream.write("".getBytes());
                        }
                    } else {
                        csvOutputStream.write("".getBytes()); // Thêm giá trị rỗng nếu cell không có dữ liệu
                    }
                    if (i < 4) { // Thêm dấu phẩy nếu không phải là cột cuối cùng
                        csvOutputStream.write(',');
                    }
                }
                csvOutputStream.write('\n');
            }

            FileItem fileItem = new DiskFileItem("output.csv", "text/csv", true, "output.csv", (int) csvOutputStream.size(), null);
            try (OutputStream os = fileItem.getOutputStream()) {
                os.write(csvOutputStream.toByteArray());
            }

            return new CommonsMultipartFile(fileItem);
        }
    }
}