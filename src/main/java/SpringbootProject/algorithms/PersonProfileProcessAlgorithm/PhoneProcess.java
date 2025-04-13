package SpringbootProject.algorithms.PersonProfileProcessAlgorithm;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors; // Import Stream API for cleaning

// Import Logger nếu bạn muốn dùng thay thế System.out.println
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import SpringbootProject.entity.notSaving.ExcelObject;

public class PhoneProcess {

    // Logger (Optional)
    // private static final Logger logger = LoggerFactory.getLogger(PhoneProcess.class);

    // --- Các hằng số Pattern ---

    // Pattern mới để TÌM KIẾM các chuỗi trông giống SĐT hơn
    // Tìm các chuỗi bắt đầu bằng (+84), 0, hoặc số khác 0, theo sau bởi các chữ số
    // có thể xen kẽ bởi dấu cách, chấm, gạch ngang, và có tổng cộng ít nhất 7 chữ số.
    // (?:\+|00)?84 -> Tùy chọn +84 hoặc 0084 ở đầu
    // 0 -> Hoặc bắt đầu bằng 0
    // [1-9] -> Hoặc bắt đầu bằng số khác 0 (cho trường hợp thiếu số 0 hoặc máy bàn)
    // (?:[\s.-]?\d){6,} -> Theo sau bởi ít nhất 6 nhóm (dấu phân cách tùy chọn + chữ số)
    // => Đảm bảo tổng cộng có ít nhất 7 chữ số.
    // \b -> Word boundary để tách biệt với các từ khác.
    // Lưu ý: Pattern này rộng hơn để bắt nhiều ứng viên, việc lọc chính xác diễn ra sau khi làm sạch và chuẩn hóa.
    private static final Pattern POTENTIAL_PHONE_FINDER_PATTERN = Pattern.compile(
            "\\b(?:(?:\\+|00)?84|0|\\d)(?:[\\s.-]?\\d){6,12}\\b" // Mở rộng độ dài tối đa để bắt nhiều trường hợp hơn
    );

    // Pattern để làm sạch các ký tự không phải số khỏi chuỗi tìm được
    private static final Pattern NON_DIGIT_CLEANUP_PATTERN = Pattern.compile("[^0-9]");

    // --- Các Set đầu số (Cần được cập nhật khi có thay đổi từ nhà mạng) ---
    private static final Set<String> VALID_VN_MOBILE_PREFIXES = Set.of(
            "090", "091", "092", "093", "094", "096", "097", "098", "099",
            "081", "082", "083", "084", "085", "086", "088", "089",
            "032", "033", "034", "035", "036", "037", "038", "039",
            "070", "076", "077", "078", "079",
            "052", "056", "058", "059"
            // Đầu số cố định mới chuyển đổi (nếu muốn coi là di động): 087? (ITelecom), 055? (Reddi)
    );

    // Đầu số cố định (máy bàn) cơ bản của Việt Nam (bắt đầu bằng 02)
    // Có thể mở rộng với các mã vùng cụ thể nếu cần kiểm tra chi tiết hơn
    private static final Pattern VN_LANDLINE_PREFIX_PATTERN = Pattern.compile("^02[0-9]$"); // 020-029

    // --- Các phương thức xử lý ---

    /**
     * Làm sạch một chuỗi tiềm năng chứa số điện thoại bằng cách loại bỏ
     * các ký tự không phải số (bao gồm cả dấu cách, chấm, gạch ngang).
     * @param potentialPhone Chuỗi đầu vào.
     * @return Chuỗi chỉ chứa chữ số hoặc chuỗi rỗng.
     */
    private String cleanPotentialPhone(String potentialPhone) {
        if (potentialPhone == null) {
            return "";
        }
        // Loại bỏ các ký tự không phải số
        return NON_DIGIT_CLEANUP_PATTERN.matcher(potentialPhone).replaceAll("");
    }


    /**
     * Chuẩn hóa và xác thực một số điện thoại *đã được làm sạch* (chỉ chứa số).
     * Ưu tiên chuẩn hóa thành số di động VN (10 số, bắt đầu bằng 0).
     * Nếu không phải di động, kiểm tra xem có phải số máy bàn VN cơ bản không.
     * @param cleanedPhone Số điện thoại chỉ chứa chữ số.
     * @return Số điện thoại đã chuẩn hóa (ưu tiên di động 10 số) nếu hợp lệ, hoặc null nếu không.
     */
    private String normalizeAndValidateVietnameseNumber(String cleanedPhone) {
        if (cleanedPhone == null || cleanedPhone.isEmpty()) {
            return null;
        }

        String mobileCandidate = null;
        int len = cleanedPhone.length();

        // 1. Ưu tiên chuẩn hóa và kiểm tra di động
        if (len == 10 && cleanedPhone.startsWith("0")) { // Dạng 0xxxxxxxxx
            mobileCandidate = cleanedPhone;
        } else if (len == 9 && !cleanedPhone.startsWith("0")) { // Dạng xxxxxxxxx (thiếu 0)
            mobileCandidate = "0" + cleanedPhone;
        } else if ((len == 11 || len == 12) && (cleanedPhone.startsWith("84") || cleanedPhone.startsWith("084"))) { // Dạng 84xxxxxxxxx hoặc 084xxxxxxxxx
            String potentialMobilePart = cleanedPhone.startsWith("084") ? cleanedPhone.substring(3) : cleanedPhone.substring(2);
             if (potentialMobilePart.length() == 9 && !potentialMobilePart.startsWith("0")) {
                 mobileCandidate = "0" + potentialMobilePart;
             }
        } // Có thể thêm xử lý cho 0084 nếu cần

        // Nếu tìm được ứng viên di động, kiểm tra đầu số
        if (mobileCandidate != null && mobileCandidate.length() == 10) {
            String prefix = mobileCandidate.substring(0, 3);
            if (VALID_VN_MOBILE_PREFIXES.contains(prefix)) {
                return mobileCandidate; // Trả về số di động hợp lệ đã chuẩn hóa
            }
        }

        // 2. Nếu không phải di động hợp lệ, kiểm tra xem có phải máy bàn VN cơ bản không
        // Ví dụ: Số máy bàn VN thường có 10 hoặc 11 chữ số (bao gồm 02x)
        if (len >= 10 && len <= 11 && cleanedPhone.startsWith("0")) { // Phải bắt đầu bằng 0
             String landlinePrefixCandidate = cleanedPhone.substring(0, 3); // Lấy mã vùng 02x
             if (VN_LANDLINE_PREFIX_PATTERN.matcher(landlinePrefixCandidate).matches()) {
                 // Đây có thể là số máy bàn VN hợp lệ.
                 // Quyết định: Có thể trả về số này hoặc null tùy yêu cầu.
                 // Hiện tại sẽ trả về null để chỉ tập trung lọc số di động như logic cũ.
                 // Nếu muốn giữ lại máy bàn, hãy return cleanedPhone ở đây.
                 // return cleanedPhone; // Bỏ comment dòng này nếu muốn lấy cả máy bàn
                 return null;
             }
        }

        // Không phải di động hoặc máy bàn VN hợp lệ (theo định nghĩa hiện tại)
        return null;
    }


    /**
     * Tìm kiếm, trích xuất, làm sạch, chuẩn hóa và xác thực các số điện thoại
     * (ưu tiên di động VN) từ một chuỗi đầu vào phức tạp.
     * @param input Chuỗi đầu vào có thể chứa văn bản, dấu phân cách và nhiều số điện thoại.
     * @return Danh sách các số điện thoại DI ĐỘNG VN đã được chuẩn hóa (10 số, bắt đầu bằng 0) và hợp lệ.
     */
    public List<String> extractAndValidateVietnameseNumbers(String input) {
        Set<String> validNormalizedPhones = new HashSet<>();
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>(validNormalizedPhones);
        }

        Matcher matcher = POTENTIAL_PHONE_FINDER_PATTERN.matcher(input);

        while (matcher.find()) {
            String potentialMatch = matcher.group();
            // Làm sạch chuỗi tìm được (loại bỏ dấu cách, chấm, gạch ngang,...)
            String cleanedCandidate = cleanPotentialPhone(potentialMatch);

            // Chuẩn hóa và xác thực (hiện tại tập trung vào di động)
            String normalizedPhone = normalizeAndValidateVietnameseNumber(cleanedCandidate);

            if (normalizedPhone != null) {
                validNormalizedPhones.add(normalizedPhone);
            }
        }
        return new ArrayList<>(validNormalizedPhones);
    }

    // --- Lớp chứa kết quả xử lý (Giữ nguyên) ---
    public static class PhoneProcessingResult {
         // ... (Giữ nguyên constructor và getters) ...
        private final List<ExcelObject> filteredList;
        private final List<ExcelObject> removedItems;
        private final String[] statusMessages;

        public PhoneProcessingResult(List<ExcelObject> filteredList, List<ExcelObject> removedItems, String[] statusMessages) {
            this.filteredList = filteredList;
            this.removedItems = removedItems;
            this.statusMessages = statusMessages;
        }
        public List<ExcelObject> getFilteredList() { return filteredList; }
        public List<ExcelObject> getRemovedItems() { return removedItems; }
        public String[] getStatusMessages() { return statusMessages; }
    }

    // --- Phương thức lọc và xác thực chính ---
    public PhoneProcessingResult filterAndValidatePhoneData(List<ExcelObject> sourceList, List<ExcelObject> listToFilter) {
        Set<String> sourceNormalizedPhones = new HashSet<>();
        // Lưu trữ các số điện thoại đã xuất hiện trong các dòng hợp lệ của file lọc
        Set<String> uniqueNormalizedPhonesInFilteredList = new HashSet<>();
        List<ExcelObject> removedItems = new ArrayList<>();
        List<ExcelObject> currentFilteredList = new ArrayList<>(listToFilter != null ? listToFilter : List.of());

        // Biến đếm chi tiết hơn (tùy chọn)
        int countInvalidRows = 0; // Dòng null, rỗng, hoặc không có SĐT hợp lệ nào
        int countRowsRemovedDueToAllDuplicate = 0; // Dòng bị xóa vì TẤT CẢ SĐT đều trùng
        int countDuplicatePhonesDetected = 0; // Tổng số SĐT trùng (nguồn hoặc file) được phát hiện

        // 1. Chuẩn bị Set nguồn
        if (sourceList != null) {
            for (ExcelObject excelObject : sourceList) {
                if (excelObject != null && excelObject.getColumn1() != null) {
                    // Sử dụng phương thức trích xuất mới cho cả nguồn
                    List<String> phonesFromSourceCell = extractAndValidateVietnameseNumbers(excelObject.getColumn1());
                    sourceNormalizedPhones.addAll(phonesFromSourceCell);
                }
            }
        }

        // 2. Duyệt và lọc danh sách cần lọc
        Iterator<ExcelObject> iterator = currentFilteredList.iterator();
        while (iterator.hasNext()) {
            ExcelObject itemToFilter = iterator.next();
            String originalValue = (itemToFilter != null) ? itemToFilter.getColumn1() : null;

            // --- Kiểm tra tính hợp lệ cơ bản của dòng và cột 1 ---
            if (itemToFilter == null || originalValue == null || originalValue.trim().isEmpty()) {
                countInvalidRows++;
                removedItems.add(itemToFilter == null ? new ExcelObject() : itemToFilter);
                iterator.remove();
                continue;
            }

            // --- Trích xuất các SĐT hợp lệ từ ô dữ liệu của dòng cần lọc ---
            List<String> phonesInCell = extractAndValidateVietnameseNumbers(originalValue);

            // --- Xử lý nếu không tìm thấy SĐT hợp lệ nào trong ô ---
            if (phonesInCell.isEmpty()) {
                countInvalidRows++;
                removedItems.add(itemToFilter);
                iterator.remove();
                continue;
            }

            // --- Kiểm tra và quyết định giữ/xóa dòng dựa trên SĐT trong ô ---
            boolean keepItem = false; // Mặc định là xóa, chỉ giữ nếu có lý do
            List<String> uniqueNewPhonesInThisCell = new ArrayList<>(); // SĐT mới, hợp lệ, chưa trùng để thêm vào set chung

            for (String normalizedPhone : phonesInCell) {
                boolean isDuplicateWithSource = sourceNormalizedPhones.contains(normalizedPhone);
                boolean isAlreadyInFiltered = uniqueNormalizedPhonesInFilteredList.contains(normalizedPhone);

                if (!isDuplicateWithSource && !isAlreadyInFiltered) {
                    // Tìm thấy SĐT hợp lệ, không trùng nguồn, và chưa có trong kết quả lọc -> Giữ lại dòng
                    keepItem = true;
                    // Chỉ thêm vào danh sách tạm nếu nó chưa có trong danh sách tạm này
                    if (!uniqueNewPhonesInThisCell.contains(normalizedPhone)) {
                         uniqueNewPhonesInThisCell.add(normalizedPhone);
                    }
                } else {
                    // SĐT này bị trùng (ghi nhận để đếm)
                    countDuplicatePhonesDetected++;
                }
            }

            // --- Thực hiện giữ hoặc xóa dòng ---
            if (keepItem) {
                // Giữ lại dòng, thêm các SĐT mới và duy nhất của nó vào set chung
                uniqueNormalizedPhonesInFilteredList.addAll(uniqueNewPhonesInThisCell);
            } else {
                // Xóa dòng vì tất cả SĐT trong ô đều trùng hoặc không hợp lệ
                countRowsRemovedDueToAllDuplicate++;
                removedItems.add(itemToFilter);
                iterator.remove();
            }
        }

        // 3. Tạo thông báo trạng thái chính xác hơn
        String[] statusMessages = new String[3];
        statusMessages[0] = "Phát hiện " + countDuplicatePhonesDetected + " trường hợp SĐT trùng lặp (với nguồn hoặc trong file lọc).";
        statusMessages[1] = "Đã loại bỏ " + countRowsRemovedDueToAllDuplicate + " dòng vì tất cả SĐT trong đó đều là bản sao đã tồn tại.";
        statusMessages[2] = "Có " + countInvalidRows + " dòng dữ liệu không hợp lệ (rỗng, null, hoặc không chứa SĐT hợp lệ).";

        // 4. Trả về kết quả
        return new PhoneProcessingResult(currentFilteredList, removedItems, statusMessages);
    }
}