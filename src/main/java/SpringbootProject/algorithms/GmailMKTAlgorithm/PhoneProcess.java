package SpringbootProject.algorithms.GmailMKTAlgorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher; // Import Matcher
import java.util.regex.Pattern; // Import Pattern
// Import Logger nếu bạn muốn dùng thay thế System.out.println
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// Giả định bạn có class ExcelObject trong package này hoặc đã import đúng
import SpringbootProject.entity.notSaving.ExcelObject;

public class PhoneProcess {

    // Nếu dùng Logger:
    // private static final Logger logger = LoggerFactory.getLogger(PhoneProcess.class);

    // --- Các hằng số Pattern và Prefix ---
    private static final Pattern NON_DIGIT_PATTERN = Pattern.compile("[^0-9]");
    private static final Pattern DOT_ZERO_SUFFIX_PATTERN = Pattern.compile("\\.0$");
    // SEPARATOR_PATTERN không còn dùng trong phoneStringProcessing mới, nhưng có thể giữ lại nếu dùng ở nơi khác
    // private static final Pattern SEPARATOR_PATTERN = Pattern.compile("[,\\n;/-]+");

    // --- PATTERN MỚI ĐỂ TÌM KIẾM SỐ ĐIỆN THOẠI TIỀM NĂNG ---
    // Tìm các chuỗi gồm 9 đến 11 chữ số liên tiếp, đứng độc lập (\b)
    private static final Pattern POTENTIAL_PHONE_FINDER_PATTERN = Pattern.compile("\\b\\d{9,11}\\b");

    private static final Set<String> VALID_VN_MOBILE_PREFIXES = Set.of(
            "090", "091", "092", "093", "094", "096", "097", "098", "099",
            "081", "082", "083", "084", "085", "086", "088", "089",
            "032", "033", "034", "035", "036", "037", "038", "039",
            "070", "076", "077", "078", "079",
            "052", "056", "058", "059"
    );

    // --- Các phương thức xử lý số điện thoại ---

    /**
     * Làm sạch một chuỗi số điện thoại: loại bỏ ký tự không phải số và đuôi ".0".
     * Phương thức này vẫn hữu ích để làm sạch dữ liệu đầu vào trước khi chuẩn hóa/lọc.
     */
    private String cleanPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return "";
        }
        String cleaned = phone.trim();
        // Loại bỏ .0 trước để tránh lỗi nếu số là dạng double string
        cleaned = DOT_ZERO_SUFFIX_PATTERN.matcher(cleaned).replaceAll("");
        // Loại bỏ tất cả ký tự không phải số
        cleaned = NON_DIGIT_PATTERN.matcher(cleaned).replaceAll("");
        return cleaned;
    }

    /**
     * Chuẩn hóa số điện thoại đã làm sạch về dạng 10 chữ số bắt đầu bằng '0' nếu hợp lệ.
     * Hỗ trợ các dạng: 10 số (0xxxxxxxxx), 9 số (xxxxxxxxx), 11 số (84xxxxxxxxx).
     * Bao gồm kiểm tra đầu số hợp lệ.
     */
    private String normalizeVietnameseMobile(String cleanedPhone) {
        if (cleanedPhone == null || cleanedPhone.isEmpty()) {
            return null;
        }
        String normalizedPhone = null;
        int len = cleanedPhone.length();

        if (len == 10 && cleanedPhone.startsWith("0")) {
            normalizedPhone = cleanedPhone;
        } else if (len == 9 && !cleanedPhone.startsWith("0")) {
            normalizedPhone = "0" + cleanedPhone;
        } else if (len == 11 && cleanedPhone.startsWith("84")) {
            String mobilePart = cleanedPhone.substring(2);
            if (mobilePart.length() == 9 && !mobilePart.startsWith("0")) {
                normalizedPhone = "0" + mobilePart;
            }
        }

        // Kiểm tra đầu số sau khi đã có dạng chuẩn 10 số tiềm năng
        if (normalizedPhone != null && isValidVnMobilePrefix(normalizedPhone)) {
            return normalizedPhone;
        }
        return null; // Trả về null nếu không chuẩn hóa được hoặc đầu số không hợp lệ
    }

    /**
     * Kiểm tra xem số điện thoại (đã ở dạng 10 số bắt đầu bằng 0) có đầu số hợp lệ không.
     */
    private boolean isValidVnMobilePrefix(String normalizedPhone) {
        if (normalizedPhone == null || normalizedPhone.length() != 10 || !normalizedPhone.startsWith("0")) {
            return false;
        }
        String prefix = normalizedPhone.substring(0, 3);
        return VALID_VN_MOBILE_PREFIXES.contains(prefix);
    }

    /**
     * Tìm kiếm và trích xuất các số điện thoại Việt Nam hợp lệ từ một chuỗi đầu vào phức tạp.
     * Sử dụng Regex để tìm các chuỗi số tiềm năng và sau đó chuẩn hóa/xác thực chúng.
     * @param input Chuỗi đầu vào có thể chứa văn bản và nhiều số điện thoại.
     * @return Danh sách các số điện thoại đã được chuẩn hóa (10 số, bắt đầu bằng 0) và hợp lệ.
     */
    public List<String> phoneStringProcessing(String input) {
        // Sử dụng Set để tự động loại bỏ các số trùng lặp tìm được
        Set<String> foundNormalizedPhones = new HashSet<>();
        if (input == null || input.trim().isEmpty()) {
            // Trả về danh sách rỗng nếu input không hợp lệ
            return new ArrayList<>(foundNormalizedPhones);
        }

        // Sử dụng Matcher để tìm tất cả các chuỗi con khớp với pattern
        Matcher matcher = POTENTIAL_PHONE_FINDER_PATTERN.matcher(input);

        while (matcher.find()) {
            // Lấy chuỗi số tiềm năng đã tìm thấy
            String potentialPhone = matcher.group();

            // Thử chuẩn hóa và xác thực số điện thoại này
            // normalizeVietnameseMobile đã bao gồm việc kiểm tra đầu số hợp lệ
            String normalizedPhone = normalizeVietnameseMobile(potentialPhone);

            // Nếu chuẩn hóa thành công (trả về số hợp lệ dạng 0xxxxxxxxx)
            if (normalizedPhone != null) {
                // Thêm vào Set kết quả
                foundNormalizedPhones.add(normalizedPhone);
            }
            // Các chuỗi số không chuẩn hóa được sẽ bị bỏ qua
        }

        // Chuyển Set thành List để trả về
        return new ArrayList<>(foundNormalizedPhones);
    }

    // --- Lớp chứa kết quả xử lý ---
    public static class PhoneProcessingResult {
        private final List<ExcelObject> filteredList;
        private final List<ExcelObject> removedItems;
        private final String[] statusMessages;

        public PhoneProcessingResult(List<ExcelObject> filteredList, List<ExcelObject> removedItems, String[] statusMessages) {
            this.filteredList = filteredList;
            this.removedItems = removedItems;
            this.statusMessages = statusMessages;
        }

        public List<ExcelObject> getFilteredList() {
            return filteredList;
        }

        public List<ExcelObject> getRemovedItems() {
            return removedItems;
        }

        public String[] getStatusMessages() {
            return statusMessages;
        }
    }

    // --- Phương thức lọc và xác thực chính ---
    /**
     * Lọc và xác thực danh sách ExcelObject dựa trên số điện thoại (cột 1),
     * chuẩn hóa số điện thoại về dạng 10 số bắt đầu bằng '0' trước khi so sánh.
     * @param sourceList Danh sách ExcelObject nguồn để đối chiếu trùng lặp.
     * @param listToFilter Danh sách ExcelObject cần lọc.
     * @return Một đối tượng PhoneProcessingResult chứa danh sách đã lọc, danh sách bị loại bỏ và thống kê.
     */
    public PhoneProcessingResult filterAndValidatePhoneData(List<ExcelObject> sourceList, List<ExcelObject> listToFilter) {
        Set<String> sourceNormalizedPhones = new HashSet<>();
        Set<String> uniqueNormalizedPhonesInFilteredList = new HashSet<>();
        List<ExcelObject> removedItems = new ArrayList<>();
        // Tạo bản sao để làm việc, tránh sửa đổi listToFilter gốc trực tiếp
        List<ExcelObject> currentFilteredList = new ArrayList<>(listToFilter != null ? listToFilter : List.of());

        int countDuplicateWithSource = 0;
        int countDuplicateInFile = 0;
        int countInvalid = 0;

        // 1. Chuẩn bị Set số điện thoại đã chuẩn hóa từ danh sách nguồn
        if (sourceList != null) {
            for (ExcelObject excelObject : sourceList) {
                if (excelObject != null && excelObject.getColumn1() != null) {
                    // Dùng phoneStringProcessing để có thể xử lý ô nguồn chứa nhiều SĐT hoặc văn bản
                    List<String> phonesFromSourceCell = phoneStringProcessing(excelObject.getColumn1());
                    for (String normalizedPhone : phonesFromSourceCell) {
                        // phoneStringProcessing đã trả về SĐT chuẩn hóa và hợp lệ
                         sourceNormalizedPhones.add(normalizedPhone);
                    }
                    // Nếu muốn làm sạch đơn giản hơn cho source list (chỉ clean và normalize):
                    // String cleanedPhone = cleanPhoneNumber(excelObject.getColumn1());
                    // String normalizedPhone = normalizeVietnameseMobile(cleanedPhone);
                    // if (normalizedPhone != null) {
                    //    sourceNormalizedPhones.add(normalizedPhone);
                    // }
                }
            }
        }

        // 2. Duyệt qua danh sách cần lọc và thực hiện kiểm tra
        // System.out.println("--- Starting Phone Filtering Process ---");
        Iterator<ExcelObject> iterator = currentFilteredList.iterator();
        while (iterator.hasNext()) {
            ExcelObject itemToFilter = iterator.next();
            String originalValue = (itemToFilter != null) ? itemToFilter.getColumn1() : null;
            boolean removed = false; // Cờ để đánh dấu nếu mục đã bị xóa

            // --- Kiểm tra null/rỗng cho đối tượng hoặc cột 1 ---
            if (itemToFilter == null || originalValue == null || originalValue.trim().isEmpty()) {
                countInvalid++;
                ExcelObject itemToAddError = (itemToFilter == null) ? new ExcelObject() : itemToFilter;
                // Log hoặc xử lý thêm nếu cần
                // System.out.println("[REMOVED] Reason: Row data or phone number is null/empty. Data: " + itemToAddError.toString());
                removedItems.add(itemToAddError);
                iterator.remove();
                removed = true;
                continue; // Chuyển sang phần tử tiếp theo
            }

            // --- Trích xuất các SĐT hợp lệ từ ô dữ liệu ---
            List<String> phonesInCell = phoneStringProcessing(originalValue);

            // --- Xử lý nếu không tìm thấy SĐT hợp lệ nào trong ô ---
            if (phonesInCell.isEmpty()) {
                countInvalid++;
                // System.out.println("[REMOVED] Reason: No valid phone number found in cell. Original: '" + originalValue + "'. Data: " + itemToFilter.toString());
                removedItems.add(itemToFilter);
                iterator.remove();
                removed = true;
                continue; // Chuyển sang phần tử tiếp theo
            }

            // --- Kiểm tra từng SĐT tìm được trong ô ---
            // Giữ lại dòng nếu ÍT NHẤT MỘT SĐT trong ô là hợp lệ và không trùng
            // Xóa dòng nếu TẤT CẢ SĐT trong ô đều không hợp lệ hoặc bị trùng
            boolean keepItem = false; // Cờ để quyết định giữ lại item hay không
            List<String> validUniquePhonesInCurrentCell = new ArrayList<>(); // Lưu các SĐT hợp lệ, chưa trùng để thêm vào Set sau

            for (String normalizedPhone : phonesInCell) {
                 // Đã được chuẩn hóa và xác thực bởi phoneStringProcessing
                 boolean isDuplicateWithSource = sourceNormalizedPhones.contains(normalizedPhone);
                 boolean isDuplicateInFile = uniqueNormalizedPhonesInFilteredList.contains(normalizedPhone);

                 if (!isDuplicateWithSource && !isDuplicateInFile) {
                     // Tìm thấy ít nhất một SĐT hợp lệ và chưa trùng
                     keepItem = true;
                     validUniquePhonesInCurrentCell.add(normalizedPhone);
                     // Không break ngay, vì có thể có SĐT khác trong ô cũng hợp lệ và cần thêm vào Set
                 } else {
                    // Ghi nhận lý do trùng lặp (có thể log chi tiết hơn nếu muốn)
                     if (isDuplicateWithSource) countDuplicateWithSource++; // Tăng biến đếm nhưng không xóa ngay
                     if (isDuplicateInFile) countDuplicateInFile++;       // Tăng biến đếm nhưng không xóa ngay
                 }
            }

            // --- Quyết định xóa hay giữ lại dòng ---
            if (keepItem) {
                // Nếu quyết định giữ lại dòng, thêm các SĐT hợp lệ và duy nhất của dòng này vào Set chung
                 uniqueNormalizedPhonesInFilteredList.addAll(validUniquePhonesInCurrentCell);
            } else {
                // Nếu không có SĐT nào trong ô thỏa mãn điều kiện giữ lại (tất cả đều trùng hoặc không hợp lệ)
                // Thì mới xóa dòng này khỏi danh sách lọc
                // Lý do đã được ghi nhận ở trên (thông qua biến đếm)
                 // System.out.println("[REMOVED] Reason: All phone numbers in cell were duplicates or invalid. Original: '" + originalValue + "'. Data: " + itemToFilter.toString());
                 removedItems.add(itemToFilter);
                 iterator.remove();
                 removed = true;
            }
        }
        // System.out.println("--- Finished Phone Filtering Process ---");

        // 3. Tạo thông báo trạng thái
        // Lưu ý: Cách đếm hiện tại có thể chưa hoàn toàn chính xác nếu một dòng bị xóa vì nhiều lý do
        // Cần xem xét lại logic đếm nếu cần độ chính xác tuyệt đối cho từng loại lỗi trên mỗi dòng.
        String[] statusMessages = new String[3];
        statusMessages[0] = "Có khoảng " + countDuplicateWithSource + " SĐT trùng với file gốc được phát hiện!"; // Lưu ý từ "khoảng"
        statusMessages[1] = "Có khoảng " + countDuplicateInFile + " SĐT trùng lặp lẫn nhau trong file cần lọc được phát hiện!"; // Lưu ý từ "khoảng"
        statusMessages[2] = "Có " + countInvalid + " dòng dữ liệu rỗng, không chứa SĐT hợp lệ, hoặc có SĐT không thể chuẩn hóa!";

        // 4. Trả về kết quả
        return new PhoneProcessingResult(currentFilteredList, removedItems, statusMessages);
    }
}