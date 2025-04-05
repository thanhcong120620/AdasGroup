package SpringbootProject.algorithms.IOAlgorithm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle; // Import CellStyle
import org.apache.poi.ss.usermodel.DataFormat; // Import DataFormat
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import SpringbootProject.entity.notSaving.ExcelObject;


public class AlgorithmWritterExcel {

	/**
	 * Ghi danh sách ExcelObject vào một file Excel tại đường dẫn được chỉ định.
	 * Cột đầu tiên (Column1 - số điện thoại) sẽ được định dạng là Text để giữ số 0 ở đầu.
	 * @param entities Danh sách các đối tượng ExcelObject.
	 * @param filePath Đường dẫn file Excel để ghi.
	 * @throws IOException Nếu có lỗi trong quá trình ghi file.
	 */
	public static void writeToExcel(List<ExcelObject> entities, String filePath) throws IOException {
        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();
        // Tạo sheet
        Sheet sheet = workbook.createSheet("Sheet1");

        // --- TẠO CELL STYLE CHO TEXT ---
        CellStyle textCellStyle = workbook.createCellStyle();
        DataFormat fmt = workbook.createDataFormat();
        textCellStyle.setDataFormat(fmt.getFormat("@")); // "@" là mã định dạng Text trong Excel
        // --- KẾT THÚC TẠO CELL STYLE ---

        // Tạo header row
        Row headerRow = sheet.createRow(0);
        // Chỉ định rõ 20 headers tương ứng với 20 cột (index 0-19)
        String[] headers = {"Column1", "Column2", "Column3", "Column4", "Column5", "Column6",
                            "Column7", "Column8", "Column9", "Column10", "Column11", "Column12",
                            "Column13", "Column14", "Column15", "Column16", "Column17", "Column18",
                            "Column19", "Column20"};

        // Ghi header vào các cột
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            // Optional: Áp dụng style Text cho header cột điện thoại nếu muốn
            // if (i == 0) {
            //    cell.setCellStyle(textCellStyle);
            // }
        }

        // Ghi dữ liệu của từng Entity vào sheet
        int rowNum = 1;
        if (entities != null) { // Thêm kiểm tra null cho entities
            for (ExcelObject entity : entities) {
                if (entity == null) continue; // Bỏ qua entity null

                Row row = sheet.createRow(rowNum++);

                // --- Cột 0: Số điện thoại (Áp dụng Text Style) ---
                Cell phoneCell = row.createCell(0);
                phoneCell.setCellStyle(textCellStyle); // Áp dụng style Text
                phoneCell.setCellValue(entity.getColumn1()); // Đặt giá trị

                // --- Các cột còn lại (1 đến 19) ---
                row.createCell(1).setCellValue(entity.getColumn2());
                row.createCell(2).setCellValue(entity.getColumn3());
                row.createCell(3).setCellValue(entity.getColumn4());
                row.createCell(4).setCellValue(entity.getColumn5());
                row.createCell(5).setCellValue(entity.getColumn6());
                row.createCell(6).setCellValue(entity.getColumn7());
                row.createCell(7).setCellValue(entity.getColumn8());
                row.createCell(8).setCellValue(entity.getColumn9());
                row.createCell(9).setCellValue(entity.getColumn10());
                row.createCell(10).setCellValue(entity.getColumn11());
                row.createCell(11).setCellValue(entity.getColumn12());
                row.createCell(12).setCellValue(entity.getColumn13());
                row.createCell(13).setCellValue(entity.getColumn14());
                row.createCell(14).setCellValue(entity.getColumn15());
                row.createCell(15).setCellValue(entity.getColumn16());
                row.createCell(16).setCellValue(entity.getColumn17());
                row.createCell(17).setCellValue(entity.getColumn18());
                row.createCell(18).setCellValue(entity.getColumn19());
                row.createCell(19).setCellValue(entity.getColumn20()); // Sửa lại index cuối cùng là 19
            }
        }

        // Tự động điều chỉnh độ rộng cột (tùy chọn, có thể làm chậm với file lớn)
        // for (int i = 0; i < headers.length; i++) {
        //     sheet.autoSizeColumn(i);
        // }
        // Đặc biệt cho cột số điện thoại (cột 0) nếu không autoSize
         sheet.setColumnWidth(0, 4000); // Ví dụ: đặt độ rộng khoảng 15 ký tự

        // Ghi workbook ra file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        } finally {
             // Đảm bảo đóng workbook ngay cả khi có lỗi
             workbook.close();
        }
    }


	/**
	 * Ghi danh sách ExcelObject vào bộ nhớ và trả về dưới dạng MultipartFile.
	 * Cột đầu tiên (Column1 - số điện thoại) sẽ được định dạng là Text để giữ số 0 ở đầu.
	 * @param entities Danh sách các đối tượng ExcelObject.
	 * @return Một đối tượng MultipartFile chứa dữ liệu Excel.
	 * @throws IOException Nếu có lỗi trong quá trình tạo file trong bộ nhớ.
	 */
	 public static MultipartFile writeToExcel(List<ExcelObject> entities) throws IOException {
	        // Tạo workbook mới
	        Workbook workbook = new XSSFWorkbook();
	        // Tạo sheet
	        Sheet sheet = workbook.createSheet("Sheet1");

	        // --- TẠO CELL STYLE CHO TEXT ---
	        CellStyle textCellStyle = workbook.createCellStyle();
	        DataFormat fmt = workbook.createDataFormat();
	        textCellStyle.setDataFormat(fmt.getFormat("@")); // "@" là mã định dạng Text
	        // --- KẾT THÚC TẠO CELL STYLE ---

	        // Tạo header row
	        Row headerRow = sheet.createRow(0);
	        String[] headers = {"Column1", "Column2", "Column3", "Column4", "Column5", "Column6",
	                            "Column7", "Column8", "Column9", "Column10", "Column11", "Column12",
	                            "Column13", "Column14", "Column15", "Column16", "Column17", "Column18",
	                            "Column19", "Column20"};

	        // Ghi header vào các cột
	        for (int i = 0; i < headers.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(headers[i]);
	        }

	        // Ghi dữ liệu của từng Entity vào sheet
	        int rowNum = 1;
	        if (entities != null) { // Thêm kiểm tra null
	            for (ExcelObject entity : entities) {
	                 if (entity == null) continue; // Bỏ qua entity null

	                Row row = sheet.createRow(rowNum++);

	                // --- Cột 0: Số điện thoại (Áp dụng Text Style) ---
	                Cell phoneCell = row.createCell(0);
	                phoneCell.setCellStyle(textCellStyle); // Áp dụng style Text
	                phoneCell.setCellValue(entity.getColumn1()); // Đặt giá trị

	                // --- Các cột còn lại (1 đến 19) ---
	                row.createCell(1).setCellValue(entity.getColumn2());
	                row.createCell(2).setCellValue(entity.getColumn3());
	                row.createCell(3).setCellValue(entity.getColumn4());
	                row.createCell(4).setCellValue(entity.getColumn5());
	                row.createCell(5).setCellValue(entity.getColumn6());
	                row.createCell(6).setCellValue(entity.getColumn7());
	                row.createCell(7).setCellValue(entity.getColumn8());
	                row.createCell(8).setCellValue(entity.getColumn9());
	                row.createCell(9).setCellValue(entity.getColumn10());
	                row.createCell(10).setCellValue(entity.getColumn11());
	                row.createCell(11).setCellValue(entity.getColumn12());
	                row.createCell(12).setCellValue(entity.getColumn13());
	                row.createCell(13).setCellValue(entity.getColumn14());
	                row.createCell(14).setCellValue(entity.getColumn15());
	                row.createCell(15).setCellValue(entity.getColumn16());
	                row.createCell(16).setCellValue(entity.getColumn17());
	                row.createCell(17).setCellValue(entity.getColumn18());
	                row.createCell(18).setCellValue(entity.getColumn19());
	                row.createCell(19).setCellValue(entity.getColumn20()); // Sửa lại index cuối cùng là 19
	            }
	        }

             // Tự động điều chỉnh độ rộng cột (tùy chọn)
             // for (int i = 0; i < headers.length; i++) {
             //     sheet.autoSizeColumn(i);
             // }
             sheet.setColumnWidth(0, 4000); // Ví dụ: đặt độ rộng cột số điện thoại

	        // Sử dụng ByteArrayOutputStream để lưu file vào bộ nhớ
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        try {
	            workbook.write(byteArrayOutputStream);
	        } finally {
	             // Đảm bảo đóng workbook ngay cả khi có lỗi ghi vào stream
	             workbook.close();
	        }


	        // Lấy byte array từ ByteArrayOutputStream
	        byte[] byteArray = byteArrayOutputStream.toByteArray();

	        // --- Tạo đối tượng MultipartFile ẩn danh ---
	        // (Phần này giữ nguyên logic tạo MultipartFile của bạn)
	        return new MultipartFile() {
	            @Override
	            public String getName() {
	                // Thường là tên của trường input file trong form
	                return "excelFile";
	            }

	            @Override
	            public String getOriginalFilename() {
	                // Đặt tên file mặc định khi tải về
	                return "exported_data.xlsx";
	            }

	            @Override
	            public String getContentType() {
	                // MIME type cho file .xlsx
	                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	            }

	            @Override
	            public boolean isEmpty() {
	                return byteArray.length == 0;
	            }

	            @Override
	            public long getSize() {
	                return byteArray.length;
	            }

	            @Override
	            public byte[] getBytes() throws IOException {
	                return byteArray;
	            }

	            @Override
	            public InputStream getInputStream() throws IOException {
	                return new ByteArrayInputStream(byteArray);
	            }

	            @Override
	            public void transferTo(File dest) throws IOException, IllegalStateException {
	                // Ghi trực tiếp byte array vào file đích
	                 try (FileOutputStream fos = new FileOutputStream(dest)) {
	                     fos.write(byteArray);
	                 }
	                // Hoặc dùng cách của bạn nếu thích:
	                // Files.copy(getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
	            }
	        };
	    }


}