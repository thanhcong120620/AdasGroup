package SpringbootProject.algorithms.IOAlgorithm;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import SpringbootProject.entity.notSaving.ExcelObject; // Import ExcelObject

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Đây KHÔNG phải là Spring Bean (@Service)
public class AlgorithmReadDataFromExcelMerge {

    private static final Logger logger = LoggerFactory.getLogger(AlgorithmReadDataFromExcelMerge.class);
    private static final int MAX_COLUMNS_TO_PROCESS = 20;

    // Phương thức chính để Controller gọi
    public List<ExcelObject> processExcelFile(MultipartFile file) throws IOException {
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
            default: break;
        }
    }
}
