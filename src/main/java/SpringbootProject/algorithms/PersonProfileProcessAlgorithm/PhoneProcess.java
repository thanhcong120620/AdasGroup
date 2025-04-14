package SpringbootProject.algorithms.PersonProfileProcessAlgorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Import Logger nếu bạn muốn dùng thay thế System.out.println
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import SpringbootProject.entity.notSaving.ExcelObject;

public class PhoneProcess {

    // Logger (Optional)
    // private static final Logger logger = LoggerFactory.getLogger(PhoneProcess.class);

    // --- Các hằng số Pattern và Set đầu số (Giữ nguyên) ---
    private static final Pattern POTENTIAL_PHONE_FINDER_PATTERN = Pattern.compile(
            "\\b(?:(?:\\+|00)?84|0|\\d)(?:[\\s.-]?\\d){6,12}\\b"
    );
    private static final Pattern NON_DIGIT_CLEANUP_PATTERN = Pattern.compile("[^0-9]");
    private static final Set<String> VALID_VN_MOBILE_PREFIXES = Set.of(
            "090", "091", "092", "093", "094", "096", "097", "098", "099",
            "081", "082", "083", "084", "085", "086", "088", "089",
            "032", "033", "034", "035", "036", "037", "038", "039",
            "070", "076", "077", "078", "079",
            "052", "056", "058", "059"
    );
    private static final Pattern VN_LANDLINE_PREFIX_PATTERN = Pattern.compile("^02[0-9]$");

    // --- Các phương thức Helper (Giữ nguyên) ---

    /**
     * Làm sạch một chuỗi tiềm năng chứa số điện thoại.
     */
    private String cleanPotentialPhone(String potentialPhone) {
        if (potentialPhone == null) {
            return "";
        }
        return NON_DIGIT_CLEANUP_PATTERN.matcher(potentialPhone).replaceAll("");
    }

    /**
     * Chuẩn hóa và xác thực một số điện thoại *đã được làm sạch*.
     * Ưu tiên chuẩn hóa thành số di động VN (10 số, bắt đầu bằng 0).
     */
    private String normalizeAndValidateVietnameseNumber(String cleanedPhone) {
        // (Giữ nguyên logic của hàm này)
        if (cleanedPhone == null || cleanedPhone.isEmpty()) {
            return null;
        }
        String mobileCandidate = null;
        int len = cleanedPhone.length();
        if (len == 10 && cleanedPhone.startsWith("0")) { mobileCandidate = cleanedPhone; }
        else if (len == 9 && !cleanedPhone.startsWith("0")) { mobileCandidate = "0" + cleanedPhone; }
        else if ((len == 11 || len == 12) && (cleanedPhone.startsWith("84") || cleanedPhone.startsWith("084"))) {
            String potentialMobilePart = cleanedPhone.startsWith("084") ? cleanedPhone.substring(3) : cleanedPhone.substring(2);
            if (potentialMobilePart.length() == 9 && !potentialMobilePart.startsWith("0")) { mobileCandidate = "0" + potentialMobilePart; }
        }
        if (mobileCandidate != null && mobileCandidate.length() == 10) {
            String prefix = mobileCandidate.substring(0, 3);
            if (VALID_VN_MOBILE_PREFIXES.contains(prefix)) { return mobileCandidate; }
        }
        return null;
    }

    /**
     * Tìm kiếm, trích xuất, làm sạch, chuẩn hóa và xác thực các số điện thoại
     * (ưu tiên di động VN) từ một chuỗi đầu vào phức tạp.
     */
    public List<String> extractAndValidateVietnameseNumbers(String input) {
        Set<String> validNormalizedPhones = new HashSet<>();
        if (input == null || input.trim().isEmpty()) { return new ArrayList<>(validNormalizedPhones); }
        Matcher matcher = POTENTIAL_PHONE_FINDER_PATTERN.matcher(input);
        while (matcher.find()) {
            String potentialMatch = matcher.group();
            String cleanedCandidate = cleanPotentialPhone(potentialMatch);
            String normalizedPhone = normalizeAndValidateVietnameseNumber(cleanedCandidate);
            if (normalizedPhone != null) { validNormalizedPhones.add(normalizedPhone); }
        }
        return new ArrayList<>(validNormalizedPhones);
    }

    // --- Lớp chứa kết quả xử lý (Giữ nguyên) ---
    public static class PhoneProcessingResult {
        private final List<ExcelObject> filteredList;
        private final List<ExcelObject> removedItems;
        private final String[] statusMessages;
        public PhoneProcessingResult(List<ExcelObject> filteredList, List<ExcelObject> removedItems, String[] statusMessages) {
            this.filteredList = filteredList; this.removedItems = removedItems; this.statusMessages = statusMessages;
        }
        public List<ExcelObject> getFilteredList() { return filteredList; }
        public List<ExcelObject> getRemovedItems() { return removedItems; }
        public String[] getStatusMessages() { return statusMessages; }
    }


    // --- Hàm Gốc: Kết hợp lọc nguồn và lọc nội bộ ---
    /**
     * [HÀM GỐC - Giữ nguyên]
     * Lọc và xác thực danh sách `listToFilter` dựa trên `sourceList` VÀ loại bỏ trùng lặp nội bộ.
     * Một mục được giữ lại chỉ khi nó chứa ít nhất một SĐT hợp lệ KHÔNG có trong `sourceList`
     * VÀ KHÔNG có trong các SĐT đã được giữ lại trước đó của `listToFilter`.
     *
     * @param sourceList Danh sách nguồn.
     * @param listToFilter Danh sách cần lọc (sẽ bị thay đổi).
     * @return PhoneProcessingResult chứa danh sách cuối cùng và các mục bị loại bỏ.
     */
    public PhoneProcessingResult filterAndValidatePhoneDataIntergrated(List<ExcelObject> sourceList, List<ExcelObject> listToFilter) {
        Set<String> sourceNormalizedPhones = new HashSet<>();
        Set<String> uniqueNormalizedPhonesInFilteredList = new HashSet<>(); // Theo dõi SĐT duy nhất đã giữ lại
        List<ExcelObject> removedItems = new ArrayList<>();
        List<ExcelObject> currentFilteredList = listToFilter != null ? listToFilter : new ArrayList<>(); // Sửa đổi trực tiếp

        int countInvalidRows = 0;
        int countRowsRemovedDueToAllDuplicate = 0;
        int countDuplicatePhonesDetected = 0;

        // 1. Chuẩn bị Set nguồn
        if (sourceList != null) {
            for (ExcelObject excelObject : sourceList) {
                if (excelObject != null && excelObject.getColumn1() != null) {
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

            // Kiểm tra hợp lệ cơ bản
            if (itemToFilter == null || originalValue == null || originalValue.trim().isEmpty()) {
                countInvalidRows++;
                removedItems.add(itemToFilter == null ? new ExcelObject() : itemToFilter);
                iterator.remove(); continue;
            }

            // Trích xuất SĐT hợp lệ
            List<String> phonesInCell = extractAndValidateVietnameseNumbers(originalValue);
            if (phonesInCell.isEmpty()) {
                countInvalidRows++;
                removedItems.add(itemToFilter);
                iterator.remove(); continue;
            }

            // Kiểm tra và quyết định giữ/xóa
            boolean keepItem = false;
            List<String> uniqueNewPhonesInThisCell = new ArrayList<>();

            for (String normalizedPhone : phonesInCell) {
                boolean isDuplicateWithSource = sourceNormalizedPhones.contains(normalizedPhone);
                boolean isAlreadyInFiltered = uniqueNormalizedPhonesInFilteredList.contains(normalizedPhone);

                if (!isDuplicateWithSource && !isAlreadyInFiltered) {
                    keepItem = true; // Tìm thấy lý do để giữ
                    if (!uniqueNewPhonesInThisCell.contains(normalizedPhone)) {
                         uniqueNewPhonesInThisCell.add(normalizedPhone);
                    }
                } else {
                    countDuplicatePhonesDetected++; // Ghi nhận trùng
                }
            }

            // Thực hiện giữ hoặc xóa
            if (keepItem) {
                // Giữ lại, thêm SĐT mới vào set chung
                uniqueNormalizedPhonesInFilteredList.addAll(uniqueNewPhonesInThisCell);
            } else {
                // Xóa vì tất cả SĐT đều trùng (nguồn hoặc nội bộ)
                countRowsRemovedDueToAllDuplicate++;
                removedItems.add(itemToFilter);
                iterator.remove();
            }
        }

        // 3. Tạo thông báo trạng thái
        String[] statusMessages = new String[3];
        statusMessages[0] = "[Combined] Phát hiện " + countDuplicatePhonesDetected + " trường hợp SĐT trùng lặp (với nguồn hoặc trong file lọc).";
        statusMessages[1] = "[Combined] Đã loại bỏ " + countRowsRemovedDueToAllDuplicate + " dòng vì tất cả SĐT trong đó đều là bản sao đã tồn tại.";
        statusMessages[2] = "[Combined] Có " + countInvalidRows + " dòng dữ liệu không hợp lệ ban đầu (rỗng, null, hoặc không chứa SĐT hợp lệ).";

        // 4. Trả về kết quả
        return new PhoneProcessingResult(currentFilteredList, removedItems, statusMessages);
    }


  //=================================================================================================================

    // --- Hàm Mới 1: Chỉ lọc dựa trên danh sách nguồn ---

    /**
     * [HÀM MỚI]
     * Lọc danh sách `listToFilter` CHỈ dựa trên danh sách `sourceList`.
     * Một mục trong `listToFilter` sẽ bị loại bỏ nếu TẤT CẢ các số điện thoại
     * hợp lệ trích xuất từ cột 1 của nó đều tồn tại trong `sourceList`.
     * Các mục không hợp lệ cũng bị loại bỏ.
     * KHÔNG xử lý trùng lặp nội bộ trong `listToFilter`.
     *
     * @param sourceList Danh sách nguồn chứa các SĐT gốc.
     * @param listToFilter Danh sách cần được lọc (sẽ bị thay đổi).
     * @return PhoneProcessingResult chứa danh sách đã lọc và danh sách các mục bị loại bỏ.
     */
    public PhoneProcessingResult filterBySourceListOnly(List<ExcelObject> sourceList, List<ExcelObject> listToFilter) {
        Set<String> sourceNormalizedPhones = new HashSet<>();
        List<ExcelObject> removedItems = new ArrayList<>();
        List<ExcelObject> currentFilteredList = listToFilter != null ? listToFilter : new ArrayList<>(); // Sửa đổi trực tiếp

        int countInvalidRows = 0;
        int countRowsRemovedDueToSourceDuplicate = 0;

        // 1. Chuẩn bị Set nguồn
        if (sourceList != null) {
            for (ExcelObject excelObject : sourceList) {
                if (excelObject != null && excelObject.getColumn1() != null) {
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

            // Kiểm tra hợp lệ cơ bản
            if (itemToFilter == null || originalValue == null || originalValue.trim().isEmpty()) {
                countInvalidRows++;
                removedItems.add(itemToFilter == null ? new ExcelObject() : itemToFilter);
                iterator.remove(); continue;
            }

            // Trích xuất SĐT hợp lệ
            List<String> phonesInCell = extractAndValidateVietnameseNumbers(originalValue);
            if (phonesInCell.isEmpty()) {
                countInvalidRows++;
                removedItems.add(itemToFilter);
                iterator.remove(); continue;
            }

            // Kiểm tra với nguồn
            boolean removeCurrentItem = true; // Giả định xóa
            for (String normalizedPhone : phonesInCell) {
                if (!sourceNormalizedPhones.contains(normalizedPhone)) {
                    removeCurrentItem = false; // Tìm thấy SĐT không có trong nguồn -> Giữ lại
                    break;
                }
            }

            // Thực hiện xóa nếu cần
            if (removeCurrentItem) {
                countRowsRemovedDueToSourceDuplicate++;
                removedItems.add(itemToFilter);
                iterator.remove();
            }
        }

        // 3. Tạo thông báo trạng thái
        String[] statusMessages = new String[2];
        statusMessages[0] = "[Source Only] Đã loại bỏ " + countRowsRemovedDueToSourceDuplicate + " dòng vì tất cả SĐT hợp lệ đều trùng với danh sách nguồn.";
        statusMessages[1] = "[Source Only] Có " + countInvalidRows + " dòng dữ liệu không hợp lệ ban đầu (rỗng, null, hoặc không chứa SĐT hợp lệ).";

        // 4. Trả về kết quả
        return new PhoneProcessingResult(currentFilteredList, removedItems, statusMessages);
    }

    
     // --- Hàm Mới 2: Chỉ lọc trùng lặp nội bộ (First-Come, First-Served) ---

    /**
     * [HÀM MỚI]
     * Lọc danh sách `inputList` CHỈ để loại bỏ các mục mà TẤT CẢ các số điện thoại
     * hợp lệ trong cột 1 của nó đã xuất hiện trong các mục được giữ lại TRƯỚC ĐÓ.
     * Nó giữ lại lần xuất hiện đầu tiên của một số điện thoại hợp lệ.
     * Các mục không hợp lệ cũng bị loại bỏ.
     * KHÔNG so sánh với danh sách nguồn bên ngoài.
     *
     * @param inputList Danh sách cần lọc trùng lặp nội bộ (sẽ bị thay đổi).
     * @return PhoneProcessingResult chứa danh sách đã lọc và danh sách các mục bị loại bỏ.
     */
    public PhoneProcessingResult filterInternalDuplicatesOnly(List<ExcelObject> inputList) {
        Set<String> keptNormalizedPhones = new HashSet<>(); // Theo dõi SĐT đã thấy ở các dòng được giữ
        List<ExcelObject> removedItems = new ArrayList<>();
        List<ExcelObject> currentFilteredList = inputList != null ? inputList : new ArrayList<>(); // Sửa đổi trực tiếp

        int countInvalidRows = 0;
        int countRowsRemovedDueToInternalDuplicate = 0;
        int countUniquePhonesKept = 0;

        Iterator<ExcelObject> iterator = currentFilteredList.iterator();
        while (iterator.hasNext()) {
            ExcelObject itemToFilter = iterator.next();
            String originalValue = (itemToFilter != null) ? itemToFilter.getColumn1() : null;

            // Kiểm tra hợp lệ cơ bản
            if (itemToFilter == null || originalValue == null || originalValue.trim().isEmpty()) {
                countInvalidRows++;
                removedItems.add(itemToFilter == null ? new ExcelObject() : itemToFilter);
                iterator.remove(); continue;
            }

            // Trích xuất SĐT hợp lệ
            List<String> phonesInCell = extractAndValidateVietnameseNumbers(originalValue);
            if (phonesInCell.isEmpty()) {
                countInvalidRows++;
                removedItems.add(itemToFilter);
                iterator.remove(); continue;
            }

            // Kiểm tra trùng lặp nội bộ
            boolean keepCurrentItem = false;
            List<String> newPhonesInThisItem = new ArrayList<>();

            for (String normalizedPhone : phonesInCell) {
                 if (!keptNormalizedPhones.contains(normalizedPhone)) {
                     keepCurrentItem = true; // Tìm thấy SĐT mới -> Giữ lại
                     if (!newPhonesInThisItem.contains(normalizedPhone)) {
                        newPhonesInThisItem.add(normalizedPhone);
                     }
                 }
            }

            // Thực hiện giữ hoặc xóa
            if (keepCurrentItem) {
                // Giữ lại, cập nhật set SĐT đã thấy
                keptNormalizedPhones.addAll(newPhonesInThisItem);
                countUniquePhonesKept = keptNormalizedPhones.size();
            } else {
                // Xóa vì tất cả SĐT đã thấy trước đó
                countRowsRemovedDueToInternalDuplicate++;
                removedItems.add(itemToFilter);
                iterator.remove();
            }
        }

        // 3. Tạo thông báo trạng thái
        String[] statusMessages = new String[3];
        statusMessages[0] = "[Internal Only] Đã loại bỏ " + countRowsRemovedDueToInternalDuplicate + " dòng vì tất cả SĐT hợp lệ đã xuất hiện trong các dòng trước đó.";
        statusMessages[1] = "[Internal Only] Có " + countInvalidRows + " dòng dữ liệu không hợp lệ ban đầu (rỗng, null, hoặc không chứa SĐT hợp lệ).";
        statusMessages[2] = "[Internal Only] Số lượng SĐT di động duy nhất được giữ lại sau khi lọc nội bộ: " + countUniquePhonesKept + ".";

        // 4. Trả về kết quả
        return new PhoneProcessingResult(currentFilteredList, removedItems, statusMessages);
    }

}