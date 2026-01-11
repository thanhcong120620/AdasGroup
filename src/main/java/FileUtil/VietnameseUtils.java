package FileUtil;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class VietnameseUtils {
	private static final Pattern DIACRITICS_PATTERN =
            Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    private VietnameseUtils() {}

    public static String removeAccent(String input) {
        if (input == null) return "";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return DIACRITICS_PATTERN.matcher(normalized).replaceAll("");
    }
}
