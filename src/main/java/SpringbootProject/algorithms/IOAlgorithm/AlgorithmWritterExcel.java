package SpringbootProject.algorithms.IOAlgorithm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle; // Import CellStyle
import org.apache.poi.ss.usermodel.DataFormat; // Import DataFormat
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import FileUtil.EnumExtractorUtil;
import FileUtil.StringProcess;
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
        String[] headers = {"Column1", "Column2", "Column3", "Column4", "Column5", "Column6","Column7", "Column8", "Column9", "Column10", 
        		"Column11", "Column12","Column13", "Column14", "Column15", "Column16", "Column17", "Column18","Column19", "Column20",
        		"Column21", "Column22", "Column23", "Column24", "Column25", "Column26","Column27", "Column28", "Column29", "Column30",
        		"Column31", "Column32", "Column33", "Column34", "Column35", "Column36","Column37", "Column38", "Column39", "Column40",
        		"Column41", "Column42", "Column43", "Column4", "Column4", "Column46","Column47", "Column48", "Column4", "Column50",
        		"Column51", "Column52", "Column53", "Column54", "Column55", "Column56","Column57", "Column58", "Column59", "Column60",
        		"Column61", "Column62", "Column63", "Column64", "Column65", "Column66","Column67", "Column68", "Column69", "Column70",
        		"Column71", "Column72", "Column73", "Column74", "Column75", "Column76","Column77", "Column78", "Column79", "Column80",
        		"Column81", "Column82", "Column83", "Column84", "Column85", "Column86","Column87", "Column88", "Column89", "Column90",
        		"Column91", "Column92", "Column93", "Column94", "Column95", "Column96","Column97", "Column98", "Column99", "Column100"
        };

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
                row.createCell(19).setCellValue(entity.getColumn20());
                
                row.createCell(20).setCellValue(entity.getColumn21());
                row.createCell(21).setCellValue(entity.getColumn22());
                row.createCell(22).setCellValue(entity.getColumn23());
                row.createCell(23).setCellValue(entity.getColumn24());
                row.createCell(24).setCellValue(entity.getColumn25());
                row.createCell(25).setCellValue(entity.getColumn26());
                row.createCell(26).setCellValue(entity.getColumn27());
                row.createCell(27).setCellValue(entity.getColumn28());
                row.createCell(28).setCellValue(entity.getColumn29()); 
                row.createCell(29).setCellValue(entity.getColumn30());
                
                row.createCell(30).setCellValue(entity.getColumn31());
                row.createCell(31).setCellValue(entity.getColumn32());
                row.createCell(32).setCellValue(entity.getColumn33());
                row.createCell(33).setCellValue(entity.getColumn34());
                row.createCell(34).setCellValue(entity.getColumn35());
                row.createCell(35).setCellValue(entity.getColumn36());
                row.createCell(36).setCellValue(entity.getColumn37());
                row.createCell(37).setCellValue(entity.getColumn38());
                row.createCell(38).setCellValue(entity.getColumn39());
                row.createCell(39).setCellValue(entity.getColumn40());
                
                row.createCell(40).setCellValue(entity.getColumn41());
                row.createCell(41).setCellValue(entity.getColumn42());
                row.createCell(42).setCellValue(entity.getColumn43());
                row.createCell(43).setCellValue(entity.getColumn44());
                row.createCell(44).setCellValue(entity.getColumn45());
                row.createCell(45).setCellValue(entity.getColumn46());
                row.createCell(46).setCellValue(entity.getColumn47());
                row.createCell(47).setCellValue(entity.getColumn48());
                row.createCell(48).setCellValue(entity.getColumn49());
                row.createCell(49).setCellValue(entity.getColumn50());
                
                row.createCell(50).setCellValue(entity.getColumn51());
                row.createCell(51).setCellValue(entity.getColumn52());
                row.createCell(52).setCellValue(entity.getColumn53());
                row.createCell(53).setCellValue(entity.getColumn54());
                row.createCell(54).setCellValue(entity.getColumn55());
                row.createCell(55).setCellValue(entity.getColumn56());
                row.createCell(56).setCellValue(entity.getColumn57());
                row.createCell(57).setCellValue(entity.getColumn58());
                row.createCell(58).setCellValue(entity.getColumn59());
                row.createCell(59).setCellValue(entity.getColumn60());
                
                row.createCell(60).setCellValue(entity.getColumn61());
                row.createCell(61).setCellValue(entity.getColumn62());
                row.createCell(62).setCellValue(entity.getColumn63());
                row.createCell(63).setCellValue(entity.getColumn64());
                row.createCell(64).setCellValue(entity.getColumn65());
                row.createCell(65).setCellValue(entity.getColumn66());
                row.createCell(66).setCellValue(entity.getColumn67());
                row.createCell(67).setCellValue(entity.getColumn68());
                row.createCell(68).setCellValue(entity.getColumn69());
                row.createCell(69).setCellValue(entity.getColumn70());
                
                row.createCell(70).setCellValue(entity.getColumn71());
                row.createCell(71).setCellValue(entity.getColumn72());
                row.createCell(72).setCellValue(entity.getColumn73());
                row.createCell(73).setCellValue(entity.getColumn74());
                row.createCell(74).setCellValue(entity.getColumn75());
                row.createCell(75).setCellValue(entity.getColumn76());
                row.createCell(76).setCellValue(entity.getColumn77());
                row.createCell(77).setCellValue(entity.getColumn78());
                row.createCell(78).setCellValue(entity.getColumn79());
                row.createCell(79).setCellValue(entity.getColumn80());
                
                row.createCell(80).setCellValue(entity.getColumn81());
                row.createCell(81).setCellValue(entity.getColumn82());
                row.createCell(82).setCellValue(entity.getColumn83());
                row.createCell(83).setCellValue(entity.getColumn84());
                row.createCell(84).setCellValue(entity.getColumn85());
                row.createCell(85).setCellValue(entity.getColumn86());
                row.createCell(86).setCellValue(entity.getColumn87());
                row.createCell(87).setCellValue(entity.getColumn88());
                row.createCell(88).setCellValue(entity.getColumn89());
                row.createCell(89).setCellValue(entity.getColumn90());
                
                row.createCell(90).setCellValue(entity.getColumn91());
                row.createCell(91).setCellValue(entity.getColumn92());
                row.createCell(92).setCellValue(entity.getColumn93());
                row.createCell(93).setCellValue(entity.getColumn94());
                row.createCell(94).setCellValue(entity.getColumn95());
                row.createCell(95).setCellValue(entity.getColumn96());
                row.createCell(96).setCellValue(entity.getColumn97());
                row.createCell(97).setCellValue(entity.getColumn98());
                row.createCell(98).setCellValue(entity.getColumn99());
                row.createCell(99).setCellValue(entity.getColumn100());
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
	        String[] headers = {"Column1", "Column2", "Column3", "Column4", "Column5", "Column6","Column7", "Column8", "Column9", "Column10", 
	        		"Column11", "Column12","Column13", "Column14", "Column15", "Column16", "Column17", "Column18","Column19", "Column20",
	        		"Column21", "Column22", "Column23", "Column24", "Column25", "Column26","Column27", "Column28", "Column29", "Column30",
	        		"Column31", "Column32", "Column33", "Column34", "Column35", "Column36","Column37", "Column38", "Column39", "Column40",
	        		"Column41", "Column42", "Column43", "Column4", "Column4", "Column46","Column47", "Column48", "Column4", "Column50",
	        		"Column51", "Column52", "Column53", "Column54", "Column55", "Column56","Column57", "Column58", "Column59", "Column60",
	        		"Column61", "Column62", "Column63", "Column64", "Column65", "Column66","Column67", "Column68", "Column69", "Column70",
	        		"Column71", "Column72", "Column73", "Column74", "Column75", "Column76","Column77", "Column78", "Column79", "Column80",
	        		"Column81", "Column82", "Column83", "Column84", "Column85", "Column86","Column87", "Column88", "Column89", "Column90",
	        		"Column91", "Column92", "Column93", "Column94", "Column95", "Column96","Column97", "Column98", "Column99", "Column100"
	        };

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
	                row.createCell(19).setCellValue(entity.getColumn20());
	                
	                row.createCell(20).setCellValue(entity.getColumn21());
	                row.createCell(21).setCellValue(entity.getColumn22());
	                row.createCell(22).setCellValue(entity.getColumn23());
	                row.createCell(23).setCellValue(entity.getColumn24());
	                row.createCell(24).setCellValue(entity.getColumn25());
	                row.createCell(25).setCellValue(entity.getColumn26());
	                row.createCell(26).setCellValue(entity.getColumn27());
	                row.createCell(27).setCellValue(entity.getColumn28());
	                row.createCell(28).setCellValue(entity.getColumn29()); 
	                row.createCell(29).setCellValue(entity.getColumn30());
	                
	                row.createCell(30).setCellValue(entity.getColumn31());
	                row.createCell(31).setCellValue(entity.getColumn32());
	                row.createCell(32).setCellValue(entity.getColumn33());
	                row.createCell(33).setCellValue(entity.getColumn34());
	                row.createCell(34).setCellValue(entity.getColumn35());
	                row.createCell(35).setCellValue(entity.getColumn36());
	                row.createCell(36).setCellValue(entity.getColumn37());
	                row.createCell(37).setCellValue(entity.getColumn38());
	                row.createCell(38).setCellValue(entity.getColumn39());
	                row.createCell(39).setCellValue(entity.getColumn40());
	                
	                row.createCell(40).setCellValue(entity.getColumn41());
	                row.createCell(41).setCellValue(entity.getColumn42());
	                row.createCell(42).setCellValue(entity.getColumn43());
	                row.createCell(43).setCellValue(entity.getColumn44());
	                row.createCell(44).setCellValue(entity.getColumn45());
	                row.createCell(45).setCellValue(entity.getColumn46());
	                row.createCell(46).setCellValue(entity.getColumn47());
	                row.createCell(47).setCellValue(entity.getColumn48());
	                row.createCell(48).setCellValue(entity.getColumn49());
	                row.createCell(49).setCellValue(entity.getColumn50());
	                
	                row.createCell(50).setCellValue(entity.getColumn51());
	                row.createCell(51).setCellValue(entity.getColumn52());
	                row.createCell(52).setCellValue(entity.getColumn53());
	                row.createCell(53).setCellValue(entity.getColumn54());
	                row.createCell(54).setCellValue(entity.getColumn55());
	                row.createCell(55).setCellValue(entity.getColumn56());
	                row.createCell(56).setCellValue(entity.getColumn57());
	                row.createCell(57).setCellValue(entity.getColumn58());
	                row.createCell(58).setCellValue(entity.getColumn59());
	                row.createCell(59).setCellValue(entity.getColumn60());
	                
	                row.createCell(60).setCellValue(entity.getColumn61());
	                row.createCell(61).setCellValue(entity.getColumn62());
	                row.createCell(62).setCellValue(entity.getColumn63());
	                row.createCell(63).setCellValue(entity.getColumn64());
	                row.createCell(64).setCellValue(entity.getColumn65());
	                row.createCell(65).setCellValue(entity.getColumn66());
	                row.createCell(66).setCellValue(entity.getColumn67());
	                row.createCell(67).setCellValue(entity.getColumn68());
	                row.createCell(68).setCellValue(entity.getColumn69());
	                row.createCell(69).setCellValue(entity.getColumn70());
	                
	                row.createCell(70).setCellValue(entity.getColumn71());
	                row.createCell(71).setCellValue(entity.getColumn72());
	                row.createCell(72).setCellValue(entity.getColumn73());
	                row.createCell(73).setCellValue(entity.getColumn74());
	                row.createCell(74).setCellValue(entity.getColumn75());
	                row.createCell(75).setCellValue(entity.getColumn76());
	                row.createCell(76).setCellValue(entity.getColumn77());
	                row.createCell(77).setCellValue(entity.getColumn78());
	                row.createCell(78).setCellValue(entity.getColumn79());
	                row.createCell(79).setCellValue(entity.getColumn80());
	                
	                row.createCell(80).setCellValue(entity.getColumn81());
	                row.createCell(81).setCellValue(entity.getColumn82());
	                row.createCell(82).setCellValue(entity.getColumn83());
	                row.createCell(83).setCellValue(entity.getColumn84());
	                row.createCell(84).setCellValue(entity.getColumn85());
	                row.createCell(85).setCellValue(entity.getColumn86());
	                row.createCell(86).setCellValue(entity.getColumn87());
	                row.createCell(87).setCellValue(entity.getColumn88());
	                row.createCell(88).setCellValue(entity.getColumn89());
	                row.createCell(89).setCellValue(entity.getColumn90());
	                
	                row.createCell(90).setCellValue(entity.getColumn91());
	                row.createCell(91).setCellValue(entity.getColumn92());
	                row.createCell(92).setCellValue(entity.getColumn93());
	                row.createCell(93).setCellValue(entity.getColumn94());
	                row.createCell(94).setCellValue(entity.getColumn95());
	                row.createCell(95).setCellValue(entity.getColumn96());
	                row.createCell(96).setCellValue(entity.getColumn97());
	                row.createCell(97).setCellValue(entity.getColumn98());
	                row.createCell(98).setCellValue(entity.getColumn99());
	                row.createCell(99).setCellValue(entity.getColumn100());
	                // Sửa lại index cuối cùng là 99
	            }
	        }

             // Tự động điều chỉnh độ rộng cột (tùy chọn)
              for (int i = 0; i < headers.length; i++) {
//                  sheet.autoSizeColumn(i);
            	  sheet.setColumnWidth(i, 4000);
              }
//             sheet.setColumnWidth(0, 4000); // Ví dụ: đặt độ rộng cột số điện thoại

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

//===========================================NEW=======================================================================
	 /**
		 * Ghi danh sách ExcelObject vào bộ nhớ và trả về dưới dạng MultipartFile.
		 * Cột đầu tiên (Column1 - số điện thoại) sẽ được định dạng là Text để giữ số 0 ở đầu.
		 * @param entities Danh sách các đối tượng ExcelObject.
		 * @return Một đối tượng MultipartFile chứa dữ liệu Excel.
		 * @throws IOException Nếu có lỗi trong quá trình tạo file trong bộ nhớ.
		 */
		 public static MultipartFile writeToExcelNoHead(List<ExcelObject> entities) throws IOException {
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
		        String[] headers = {
		        		entities.get(0).getColumn1(), entities.get(0).getColumn2(), entities.get(0).getColumn3(), entities.get(0).getColumn4(), 
		        		entities.get(0).getColumn5(), entities.get(0).getColumn6(), entities.get(0).getColumn7(), entities.get(0).getColumn8(),
		        		entities.get(0).getColumn9(), entities.get(0).getColumn10(), entities.get(0).getColumn11(), entities.get(0).getColumn12(),
		        		entities.get(0).getColumn13(), entities.get(0).getColumn14(), entities.get(0).getColumn15(), entities.get(0).getColumn16(),
		        		entities.get(0).getColumn17(), entities.get(0).getColumn18(), entities.get(0).getColumn19(), entities.get(0).getColumn20(),
		        		entities.get(0).getColumn21(), entities.get(0).getColumn22(), entities.get(0).getColumn23(), entities.get(0).getColumn24(),
		        		entities.get(0).getColumn25(), entities.get(0).getColumn26(), entities.get(0).getColumn27(), entities.get(0).getColumn28(),
		        		entities.get(0).getColumn29(), entities.get(0).getColumn30(), entities.get(0).getColumn31(), entities.get(0).getColumn32(),
		        		entities.get(0).getColumn33(), entities.get(0).getColumn34(), entities.get(0).getColumn35(), entities.get(0).getColumn36(),
		        		entities.get(0).getColumn37(), entities.get(0).getColumn38(), entities.get(0).getColumn39(), entities.get(0).getColumn40(),
		        		entities.get(0).getColumn41(), entities.get(0).getColumn42(), entities.get(0).getColumn43(), entities.get(0).getColumn44(),
		        		entities.get(0).getColumn45(), entities.get(0).getColumn46(), entities.get(0).getColumn47(), entities.get(0).getColumn48(),
		        		entities.get(0).getColumn49(), entities.get(0).getColumn50(), entities.get(0).getColumn51(), entities.get(0).getColumn52(),
		        		entities.get(0).getColumn53(), entities.get(0).getColumn54(), entities.get(0).getColumn55(), entities.get(0).getColumn56(),
		        		entities.get(0).getColumn57(), entities.get(0).getColumn58(), entities.get(0).getColumn59(), entities.get(0).getColumn60(),
		        		entities.get(0).getColumn61(), entities.get(0).getColumn62(), entities.get(0).getColumn63(), entities.get(0).getColumn64(),
		        		entities.get(0).getColumn65(), entities.get(0).getColumn66(), entities.get(0).getColumn67(), entities.get(0).getColumn68(),
		        		entities.get(0).getColumn69(), entities.get(0).getColumn70(), entities.get(0).getColumn71(), entities.get(0).getColumn72(),
		        		entities.get(0).getColumn73(), entities.get(0).getColumn74(), entities.get(0).getColumn75(), entities.get(0).getColumn76(),
		        		entities.get(0).getColumn77(), entities.get(0).getColumn78(), entities.get(0).getColumn79(), entities.get(0).getColumn80(),
		        		entities.get(0).getColumn81(), entities.get(0).getColumn82(), entities.get(0).getColumn83(), entities.get(0).getColumn84(),
		        		entities.get(0).getColumn85(), entities.get(0).getColumn86(), entities.get(0).getColumn87(), entities.get(0).getColumn88(),
		        		entities.get(0).getColumn89(), entities.get(0).getColumn90(), entities.get(0).getColumn91(), entities.get(0).getColumn92(),
		        		entities.get(0).getColumn93(), entities.get(0).getColumn94(), entities.get(0).getColumn95(), entities.get(0).getColumn96(),
		        		entities.get(0).getColumn97(), entities.get(0).getColumn98(), entities.get(0).getColumn99(), entities.get(0).getColumn100()
		        		
		        };

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
		                row.createCell(19).setCellValue(entity.getColumn20());
		                
		                row.createCell(20).setCellValue(entity.getColumn21());
		                row.createCell(21).setCellValue(entity.getColumn22());
		                row.createCell(22).setCellValue(entity.getColumn23());
		                row.createCell(23).setCellValue(entity.getColumn24());
		                row.createCell(24).setCellValue(entity.getColumn25());
		                row.createCell(25).setCellValue(entity.getColumn26());
		                row.createCell(26).setCellValue(entity.getColumn27());
		                row.createCell(27).setCellValue(entity.getColumn28());
		                row.createCell(28).setCellValue(entity.getColumn29()); 
		                row.createCell(29).setCellValue(entity.getColumn30());
		                
		                row.createCell(30).setCellValue(entity.getColumn31());
		                row.createCell(31).setCellValue(entity.getColumn32());
		                row.createCell(32).setCellValue(entity.getColumn33());
		                row.createCell(33).setCellValue(entity.getColumn34());
		                row.createCell(34).setCellValue(entity.getColumn35());
		                row.createCell(35).setCellValue(entity.getColumn36());
		                row.createCell(36).setCellValue(entity.getColumn37());
		                row.createCell(37).setCellValue(entity.getColumn38());
		                row.createCell(38).setCellValue(entity.getColumn39());
		                row.createCell(39).setCellValue(entity.getColumn40());
		                
		                row.createCell(40).setCellValue(entity.getColumn41());
		                row.createCell(41).setCellValue(entity.getColumn42());
		                row.createCell(42).setCellValue(entity.getColumn43());
		                row.createCell(43).setCellValue(entity.getColumn44());
		                row.createCell(44).setCellValue(entity.getColumn45());
		                row.createCell(45).setCellValue(entity.getColumn46());
		                row.createCell(46).setCellValue(entity.getColumn47());
		                row.createCell(47).setCellValue(entity.getColumn48());
		                row.createCell(48).setCellValue(entity.getColumn49());
		                row.createCell(49).setCellValue(entity.getColumn50());
		                
		                row.createCell(50).setCellValue(entity.getColumn51());
		                row.createCell(51).setCellValue(entity.getColumn52());
		                row.createCell(52).setCellValue(entity.getColumn53());
		                row.createCell(53).setCellValue(entity.getColumn54());
		                row.createCell(54).setCellValue(entity.getColumn55());
		                row.createCell(55).setCellValue(entity.getColumn56());
		                row.createCell(56).setCellValue(entity.getColumn57());
		                row.createCell(57).setCellValue(entity.getColumn58());
		                row.createCell(58).setCellValue(entity.getColumn59());
		                row.createCell(59).setCellValue(entity.getColumn60());
		                
		                row.createCell(60).setCellValue(entity.getColumn61());
		                row.createCell(61).setCellValue(entity.getColumn62());
		                row.createCell(62).setCellValue(entity.getColumn63());
		                row.createCell(63).setCellValue(entity.getColumn64());
		                row.createCell(64).setCellValue(entity.getColumn65());
		                row.createCell(65).setCellValue(entity.getColumn66());
		                row.createCell(66).setCellValue(entity.getColumn67());
		                row.createCell(67).setCellValue(entity.getColumn68());
		                row.createCell(68).setCellValue(entity.getColumn69());
		                row.createCell(69).setCellValue(entity.getColumn70());
		                
		                row.createCell(70).setCellValue(entity.getColumn71());
		                row.createCell(71).setCellValue(entity.getColumn72());
		                row.createCell(72).setCellValue(entity.getColumn73());
		                row.createCell(73).setCellValue(entity.getColumn74());
		                row.createCell(74).setCellValue(entity.getColumn75());
		                row.createCell(75).setCellValue(entity.getColumn76());
		                row.createCell(76).setCellValue(entity.getColumn77());
		                row.createCell(77).setCellValue(entity.getColumn78());
		                row.createCell(78).setCellValue(entity.getColumn79());
		                row.createCell(79).setCellValue(entity.getColumn80());
		                
		                row.createCell(80).setCellValue(entity.getColumn81());
		                row.createCell(81).setCellValue(entity.getColumn82());
		                row.createCell(82).setCellValue(entity.getColumn83());
		                row.createCell(83).setCellValue(entity.getColumn84());
		                row.createCell(84).setCellValue(entity.getColumn85());
		                row.createCell(85).setCellValue(entity.getColumn86());
		                row.createCell(86).setCellValue(entity.getColumn87());
		                row.createCell(87).setCellValue(entity.getColumn88());
		                row.createCell(88).setCellValue(entity.getColumn89());
		                row.createCell(89).setCellValue(entity.getColumn90());
		                
		                row.createCell(90).setCellValue(entity.getColumn91());
		                row.createCell(91).setCellValue(entity.getColumn92());
		                row.createCell(92).setCellValue(entity.getColumn93());
		                row.createCell(93).setCellValue(entity.getColumn94());
		                row.createCell(94).setCellValue(entity.getColumn95());
		                row.createCell(95).setCellValue(entity.getColumn96());
		                row.createCell(96).setCellValue(entity.getColumn97());
		                row.createCell(97).setCellValue(entity.getColumn98());
		                row.createCell(98).setCellValue(entity.getColumn99());
		                row.createCell(99).setCellValue(entity.getColumn100());
		                // Sửa lại index cuối cùng là 99
		            }
		        }

	             // Tự động điều chỉnh độ rộng cột (tùy chọn)
	              for (int i = 0; i < headers.length; i++) {
	                  sheet.autoSizeColumn(i);
//	            	  sheet.setColumnWidth(i, 4000);
	              }
//	             sheet.setColumnWidth(0, 4000); // Ví dụ: đặt độ rộng cột số điện thoại

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
		 
		 /**
			 * Hàm ghi dữ liệu có sẵn dòng head 1.
			 * @param entities Danh sách các đối tượng ExcelObject.
			 * @return Một đối tượng MultipartFile chứa dữ liệu Excel.
			 * @throws IOException Nếu có lỗi trong quá trình tạo file trong bộ nhớ.
			 */
			 public static MultipartFile writeToExcelHasHead(List<ExcelObject> entities, String[] headArray) throws IOException {
			        // Tạo workbook mới
			        Workbook workbook = new XSSFWorkbook();
			        // Tạo sheet
			        Sheet sheet = workbook.createSheet("Main-Data");

			        // --- TẠO CELL STYLE CHO TEXT ---
			        CellStyle textCellStyle = workbook.createCellStyle();
			        DataFormat fmt = workbook.createDataFormat();
			        textCellStyle.setDataFormat(fmt.getFormat("@")); // "@" là mã định dạng Text
			        // --- KẾT THÚC TẠO CELL STYLE ---

			        // Tạo header row
			        Row headerRow = sheet.createRow(0);
			        // Tạo CellStyle cho header
			        CellStyle headerStyle = workbook.createCellStyle();

			        // Tô màu nền
			        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			        // (Tuỳ chọn) căn giữa
			        headerStyle.setAlignment(HorizontalAlignment.CENTER);

			        // (Tuỳ chọn) font đậm
			        Font font = workbook.createFont();
			        font.setBold(true);
			        headerStyle.setFont(font);
			        String[] headers = Arrays.copyOf(headArray, headArray.length);
			        

			        // Ghi header vào các cột
			        for (int i = 0; i < headers.length; i++) {
			            Cell cell = headerRow.createCell(i);
			            cell.setCellValue(headers[i]);
			            cell.setCellStyle(headerStyle);
			            int columnWidth = (headers[i].length() + 2) * 256;
			            sheet.setColumnWidth(i, columnWidth);
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
			                row.createCell(19).setCellValue(entity.getColumn20());
			                
			                row.createCell(20).setCellValue(entity.getColumn21());
			                row.createCell(21).setCellValue(entity.getColumn22());
			                row.createCell(22).setCellValue(entity.getColumn23());
			                row.createCell(23).setCellValue(entity.getColumn24());
			                row.createCell(24).setCellValue(entity.getColumn25());
			                row.createCell(25).setCellValue(entity.getColumn26());
			                row.createCell(26).setCellValue(entity.getColumn27());
			                row.createCell(27).setCellValue(entity.getColumn28());
			                row.createCell(28).setCellValue(entity.getColumn29()); 
			                row.createCell(29).setCellValue(entity.getColumn30());
			                
			                row.createCell(30).setCellValue(entity.getColumn31());
			                row.createCell(31).setCellValue(entity.getColumn32());
			                row.createCell(32).setCellValue(entity.getColumn33());
			                row.createCell(33).setCellValue(entity.getColumn34());
			                row.createCell(34).setCellValue(entity.getColumn35());
			                row.createCell(35).setCellValue(entity.getColumn36());
			                row.createCell(36).setCellValue(entity.getColumn37());
			                row.createCell(37).setCellValue(entity.getColumn38());
			                row.createCell(38).setCellValue(entity.getColumn39());
			                row.createCell(39).setCellValue(entity.getColumn40());
			                
			                row.createCell(40).setCellValue(entity.getColumn41());
			                row.createCell(41).setCellValue(entity.getColumn42());
			                row.createCell(42).setCellValue(entity.getColumn43());
			                row.createCell(43).setCellValue(entity.getColumn44());
			                row.createCell(44).setCellValue(entity.getColumn45());
			                row.createCell(45).setCellValue(entity.getColumn46());
			                row.createCell(46).setCellValue(entity.getColumn47());
			                row.createCell(47).setCellValue(entity.getColumn48());
			                row.createCell(48).setCellValue(entity.getColumn49());
			                row.createCell(49).setCellValue(entity.getColumn50());
			                
			                row.createCell(50).setCellValue(entity.getColumn51());
			                row.createCell(51).setCellValue(entity.getColumn52());
			                row.createCell(52).setCellValue(entity.getColumn53());
			                row.createCell(53).setCellValue(entity.getColumn54());
			                row.createCell(54).setCellValue(entity.getColumn55());
			                row.createCell(55).setCellValue(entity.getColumn56());
			                row.createCell(56).setCellValue(entity.getColumn57());
			                row.createCell(57).setCellValue(entity.getColumn58());
			                row.createCell(58).setCellValue(entity.getColumn59());
			                row.createCell(59).setCellValue(entity.getColumn60());
			                
			                row.createCell(60).setCellValue(entity.getColumn61());
			                row.createCell(61).setCellValue(entity.getColumn62());
			                row.createCell(62).setCellValue(entity.getColumn63());
			                row.createCell(63).setCellValue(entity.getColumn64());
			                row.createCell(64).setCellValue(entity.getColumn65());
			                row.createCell(65).setCellValue(entity.getColumn66());
			                row.createCell(66).setCellValue(entity.getColumn67());
			                row.createCell(67).setCellValue(entity.getColumn68());
			                row.createCell(68).setCellValue(entity.getColumn69());
			                row.createCell(69).setCellValue(entity.getColumn70());
			                
			                row.createCell(70).setCellValue(entity.getColumn71());
			                row.createCell(71).setCellValue(entity.getColumn72());
			                row.createCell(72).setCellValue(entity.getColumn73());
			                row.createCell(73).setCellValue(entity.getColumn74());
			                row.createCell(74).setCellValue(entity.getColumn75());
			                row.createCell(75).setCellValue(entity.getColumn76());
			                row.createCell(76).setCellValue(entity.getColumn77());
			                row.createCell(77).setCellValue(entity.getColumn78());
			                row.createCell(78).setCellValue(entity.getColumn79());
			                row.createCell(79).setCellValue(entity.getColumn80());
			                
			                row.createCell(80).setCellValue(entity.getColumn81());
			                row.createCell(81).setCellValue(entity.getColumn82());
			                row.createCell(82).setCellValue(entity.getColumn83());
			                row.createCell(83).setCellValue(entity.getColumn84());
			                row.createCell(84).setCellValue(entity.getColumn85());
			                row.createCell(85).setCellValue(entity.getColumn86());
			                row.createCell(86).setCellValue(entity.getColumn87());
			                row.createCell(87).setCellValue(entity.getColumn88());
			                row.createCell(88).setCellValue(entity.getColumn89());
			                row.createCell(89).setCellValue(entity.getColumn90());
			                
			                row.createCell(90).setCellValue(entity.getColumn91());
			                row.createCell(91).setCellValue(entity.getColumn92());
			                row.createCell(92).setCellValue(entity.getColumn93());
			                row.createCell(93).setCellValue(entity.getColumn94());
			                row.createCell(94).setCellValue(entity.getColumn95());
			                row.createCell(95).setCellValue(entity.getColumn96());
			                row.createCell(96).setCellValue(entity.getColumn97());
			                row.createCell(97).setCellValue(entity.getColumn98());
			                row.createCell(98).setCellValue(entity.getColumn99());
			                row.createCell(99).setCellValue(entity.getColumn100());
			                // Sửa lại index cuối cùng là 99
			            }
			        } else {
			        	Row row = sheet.createRow(1);

		                row.createCell(0).setCellValue("Không có dữ liệu --> Hãy điền dữ liệu !");
			        }

		             // Tự động điều chỉnh độ rộng cột (tùy chọn)
//		              for (int i = 0; i < headers.length; i++) {
//		                  sheet.autoSizeColumn(i);
////		            	  sheet.setColumnWidth(i, 4000);
//		              }
//		             sheet.setColumnWidth(0, 4000); // Ví dụ: đặt độ rộng cột số điện thoại

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
//=====================CÁC HÀM MỚI==========================================================================================================
			 /**
				 * Hàm ghi dữ liệu có sẵn dòng head 1, tạo file excel có droplist với data sẵn
				 * Ghi danh sách ExcelObject vào bộ nhớ và trả về dưới dạng MultipartFile.
				 * @param entities Danh sách các đối tượng ExcelObject.
				 * @return Một đối tượng MultipartFile chứa dữ liệu Excel.
				 * @throws IOException Nếu có lỗi trong quá trình tạo file trong bộ nhớ.
				 */
				 public static MultipartFile writeToExcelHasHeadWithDropListForm(Object object) throws IOException {
					 
					 // Tạo workbook và sheet mới
				        Workbook workbook = new XSSFWorkbook();
				        Sheet dataSheet = workbook.createSheet("Main Data");
				        
				        // --- TẠO CELL STYLE CHO TEXT ---
				        CellStyle textCellStyle = workbook.createCellStyle();
				        DataFormat fmt = workbook.createDataFormat();
				        textCellStyle.setDataFormat(fmt.getFormat("@")); // "@" là mã định dạng Text
				        // --- KẾT THÚC TẠO CELL STYLE ---

				        // Tạo header row
				        Row headerRow = dataSheet.createRow(0);
				        // Tạo CellStyle cho header
				        CellStyle headerStyle = workbook.createCellStyle();

				        // Tô màu nền
				        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
				        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

				        // (Tuỳ chọn) căn giữa
				        headerStyle.setAlignment(HorizontalAlignment.CENTER);

				        // (Tuỳ chọn) font đậm
				        Font font = workbook.createFont();
				        font.setBold(true);
				        headerStyle.setFont(font);
//				        String[] headers = Arrays.copyOf(headArray, headArray.length);
				        String[] headers = StringProcess.getAllFieldNames(object);

				        // Ghi header vào các cột
				        for (int i = 0; i < headers.length; i++) {
				            Cell cell = headerRow.createCell(i);
				            cell.setCellValue(headers[i]);
				            cell.setCellStyle(headerStyle);
				            int columnWidth = (headers[i].length() + 2) * 256;
				            dataSheet.setColumnWidth(i, columnWidth);
				        }
				        

//				        Tạo droplist
				        int firstRow = 1;
			            int lastRow = 10000;
				        Map<String, List<String>> enumValueMap = EnumExtractorUtil.extractEnumLabelMap(object);
//				        int columCount = enumValue.size();
				        for (Map.Entry<String, List<String>> enumList : enumValueMap.entrySet()) {
				            String enumValue = enumList.getKey();
				            List<String> enumeLablesList = enumList.getValue();
				            String[] enumeLablesArray = enumeLablesList.stream().toArray(String[]::new);
				            int indexOfValueValue = StringProcess.indexOfIgnoreCase(headers, enumValue);
				            createDropdown(workbook,dataSheet,firstRow,lastRow, indexOfValueValue,enumeLablesArray);
//				            Row valueRow = dataSheet.createRow(1);
//				            Cell cell = valueRow.createCell(firstRow+=1);
//				            cell.setCellStyle(dropListCell);
				        }

				        // Ghi dữ liệu của từng Entity vào sheet (bỏ, vì chỉ là tạo form.
				        

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
				 
				 
				 
//==========================CÁC HÀM HELPER=========================================================================				 
				 
				 	/**
				     * Tạo dropdown (Data Validation) cho Excel
				     */
				    public static void createDropdown(
				    		Workbook workbook,
				            Sheet sheet,
				            int firstRow,
				            int lastRow,
				            int column,
				            String[] listData
				    ) {
				        DataValidationHelper helper = sheet.getDataValidationHelper();
				        DataValidationConstraint constraint =
				                helper.createExplicitListConstraint(listData);

				        CellRangeAddressList addressList =
				                new CellRangeAddressList(firstRow, lastRow, column, column);

				        DataValidation validation =
				                helper.createValidation(constraint, addressList);

				        validation.setSuppressDropDownArrow(true);
				        validation.setShowErrorBox(true);

				        sheet.addValidationData(validation);
				        
				     // ===== 2. Tạo style tô màu
				        CellStyle style = workbook.createCellStyle();
				        style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
				        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				        style.setBorderBottom(BorderStyle.THIN);
				        style.setBorderTop(BorderStyle.THIN);
				        style.setBorderLeft(BorderStyle.THIN);
				        style.setBorderRight(BorderStyle.THIN);

				        // ===== 3. Áp style cho toàn bộ vùng droplist
				        for (int r = firstRow; r <= lastRow; r++) {
				            Row row = sheet.getRow(r);
				            if (row == null) row = sheet.createRow(r);

				            Cell cell = row.getCell(column);
				            if (cell == null) cell = row.createCell(column);

				            cell.setCellStyle(style);
				    }
				    }
				    
				    
}