package FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class ContactUtils {
	// Regex cho Email và Số điện thoại (Việt Nam)
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("(\\+84|0)\\d{9,10}");

    // Record để lưu trữ kết quả trả về
    public record ContactResult(List<String> phones, List<String> addresses, List<String> emails) {}
    
    /**
     * Hàm xử lý cho 1 String duy nhất
     */
    public static ContactResult parseSingleMixContact(String mixContact) {
        if (mixContact == null || mixContact.isBlank()) {
            return new ContactResult(List.of(), List.of(), List.of());
        }

        // 1. Trích xuất tất cả Emails bằng Stream API (Java 9+)
        List<String> emails = EMAIL_PATTERN.matcher(mixContact)
                .results()
                .map(MatchResult::group)
                .toList();

        // 2. Trích xuất tất cả Phones
        List<String> phones = PHONE_PATTERN.matcher(mixContact)
                .results()
                .map(MatchResult::group)
                .toList();

        // 3. Trích xuất Địa chỉ (phần còn lại)
        String remain = mixContact;
        for (String e : emails) remain = remain.replace(e, "");
        for (String p : phones) remain = remain.replace(p, "");

        // Dọn dẹp các ký tự ngăn cách thừa
        String cleanedAddress = remain
                .replaceAll("^[\\s;,.|/\\-:+]+|[\\s;,.|/\\-:+]+$", "")
                .replaceAll("\\s{2,}", " ")
                .trim();

        List<String> addresses = cleanedAddress.isEmpty() ? List.of() : List.of(cleanedAddress);

        return new ContactResult(phones, addresses, emails);
    }
    
    
    
    /**
     * Hàm xử lý tách dữ liệu từ List chuỗi hỗn hợp
     */
    public static ContactResult parseMixContactsList(List<String> mixContacts) {
        List<String> allPhones = new ArrayList<>();
        List<String> allEmails = new ArrayList<>();
        List<String> allAddresses = new ArrayList<>();

        for (String item : mixContacts) {
            if (item == null || item.isBlank()) continue;

            // Lấy danh sách Emails trong chuỗi
            List<String> emails = EMAIL_PATTERN.matcher(item)
                    .results()
                    .map(MatchResult::group)
                    .toList();
            allEmails.addAll(emails);

            // Lấy danh sách Phones trong chuỗi
            List<String> phones = PHONE_PATTERN.matcher(item)
                    .results()
                    .map(MatchResult::group)
                    .toList();
            allPhones.addAll(phones);

            // Tách Địa chỉ bằng cách xóa bỏ Phone và Email đã tìm thấy
            String remain = item;
            for (String e : emails) remain = remain.replace(e, "");
            for (String p : phones) remain = remain.replace(p, "");

            // Dọn dẹp địa chỉ: Xóa các ký tự phân tách ở đầu/cuối và khoảng trắng thừa
            String cleanedAddress = remain
                    .replaceAll("^[\\s;,.|/\\-:+]+|[\\s;,.|/\\-:+]+$", "") // Xóa ký tự lạ ở 2 đầu
                    .replaceAll("\\s{2,}", " ")                             // Gom khoảng trắng kép
                    .trim();

            if (!cleanedAddress.isEmpty()) {
                allAddresses.add(cleanedAddress);
            }
        }

        return new ContactResult(allPhones, allAddresses, allEmails);
    }
}
