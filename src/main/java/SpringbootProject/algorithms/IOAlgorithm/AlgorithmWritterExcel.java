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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import SpringbootProject.entity.notSaving.ExcelObject;


public class AlgorithmWritterExcel {

	public static void writeToExcel(List<ExcelObject> entities, String filePath) throws IOException {
        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();
        // Tạo sheet
        Sheet sheet = workbook.createSheet("Sheet1");

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
        for (ExcelObject entity : entities) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entity.getColumn1());
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
            row.createCell(19).setCellValue(entity.getColumn20());
            row.createCell(20).setCellValue(entity.getColumn20());
        }

        // Ghi workbook ra file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }

        // Đóng workbook
        workbook.close();
    }
	
	
	
	/*
	 * Phương thức viết dữ liệu và trả về MultipartFile
	 * */
	 public static MultipartFile writeToExcel(List<ExcelObject> entities) throws IOException {
	        // Tạo workbook mới
	        Workbook workbook = new XSSFWorkbook();
	        // Tạo sheet
	        Sheet sheet = workbook.createSheet("Sheet1");

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
	        for (ExcelObject entity : entities) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(entity.getColumn1());
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
	            row.createCell(19).setCellValue(entity.getColumn20());
	            row.createCell(20).setCellValue(entity.getColumn20());
	        }

	        // Sử dụng ByteArrayOutputStream để lưu file vào bộ nhớ
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        workbook.write(byteArrayOutputStream);

	        // Đóng workbook
	        workbook.close();

	        // Lấy byte array từ ByteArrayOutputStream
	        byte[] byteArray = byteArrayOutputStream.toByteArray();

	        // Tạo ByteArrayResource từ byte array
	        ByteArrayResource resource = new ByteArrayResource(byteArray) {
	            @Override
	            public String getFilename() {
	                return "excel_file.xlsx";
	            }
	        };

	        // Trả về MultipartFile từ ByteArrayResource
	        return new MultipartFile() {
	            @Override
	            public String getName() {
	                return "excel_file";
	            }

	            @Override
	            public String getOriginalFilename() {
	                return "excel_file.xlsx";
	            }

	            @Override
	            public String getContentType() {
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
	                Files.copy(getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
	            }
	        };
	    }
	
	
}
