package FileUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.DateUtil;

public class DateAndTimeProcess {
	
	public LocalDateTime parseLocalDateTimeFlexible(String value) {
        if (value == null || value.isBlank()) return null;

        value = value.trim();

        // Danh sách các formatter phổ biến cho LocalDateTime
        List<DateTimeFormatter> formatters = List.of(
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,                 // 2026-01-08T20:03:36
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),    // 2026-01-08 20:03:36
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"),    // 08/01/2026 20:03:36
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),       // 08/01/2026 20:03
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),             // 08/01/2026
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),             // 2026-01-08
            DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH) // Sat Jan 03 00:00:00 ICT 1981
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                // Nếu format chỉ có ngày thì mặc định thời gian là 00:00
                if (formatter.toString().contains("d") && !formatter.toString().contains("H")) {
                    LocalDate date = LocalDate.parse(value, formatter);
                    return date.atStartOfDay();
                }
                return LocalDateTime.parse(value, formatter);
            } catch (DateTimeParseException ignored) {}
        }

        throw new IllegalArgumentException("Unsupported date-time format: " + value);
    }
	
	
	public  LocalDate parseLocalDateFlexible(Object value) {

	    if (value == null) return null;

	    // ========== CASE 1: Excel Date (BEST PRACTICE) ==========
	    if (value instanceof java.util.Date) {
	        return ((java.util.Date) value)
	                .toInstant()
	                .atZone(ZoneId.systemDefault())
	                .toLocalDate();
	    }

	    // ========== CASE 2: Excel numeric serial ==========
	    if (value instanceof Number) {
	        return DateUtil.getLocalDateTime(
	                ((Number) value).doubleValue()
	        ).toLocalDate();
	    }

	    // ========== CASE 3: String ==========
	    String text = value.toString().trim();
	    if (text.isEmpty()) return null;
	    
	    // ---- Formatter list (order matters) ----
	    List<DateTimeFormatter> dateFormatters = List.of(
	            DateTimeFormatter.ISO_LOCAL_DATE,                 // 1981-01-03
	            DateTimeFormatter.ofPattern("dd/MM/yyyy"),        // 03/01/1981
	            DateTimeFormatter.ofPattern("dd-MM-yyyy"),        // 03-01-1981
	            DateTimeFormatter.ofPattern("yyyy/MM/dd"),        // 1981/01/03
	            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
	            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
	            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"),
	            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
	            DateTimeFormatter.ofPattern(
	                    "EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH
	            )
	    );

	    // ========== CASE 3.1: Parse LocalDate ==========
	    for (DateTimeFormatter formatter : dateFormatters) {
	        try {
	            return LocalDate.parse(text, formatter);
	        } catch (Exception ignored) {}
	    }

	    // ========== CASE 3.2: ISO DateTime ==========
	    try {
	        return LocalDateTime.parse(text).toLocalDate();
	    } catch (Exception ignored) {}

	    // ========== CASE 3.3: Zoned DateTime ==========
	    try {
	        return ZonedDateTime.parse(text).toLocalDate();
	    } catch (Exception ignored) {}

	    // ========== FAIL ==========
	    throw new IllegalArgumentException("Unsupported date format: " + text);
	}

}
