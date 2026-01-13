package SpringbootProject.algorithms.PersonProfileProcessAlgorithm;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import FileUtil.VietnameseUtils;
import SpringbootProject.entity.enums.Gender;
import SpringbootProject.entity.enums.Salutation;

public class GenderProcess {
	

	
//==========================================Salutation---------------------------------------------		
	
	public static Salutation detectSalutationFromGender(Gender gender, LocalDate dayOfBirh) {
		if(!gender.equals(Gender.UNDEFINED)&&dayOfBirh !=null) {
//			System.out.println(">> detectSalutationFromGender");
			if(dayOfBirh.getYear()<=1970) {
				if(gender.equals(Gender.MALE)) { 
					return Salutation.CHU;
				} else if(gender.equals(Gender.FEMALE)) {
					return Salutation.CO;
				}
			} else if ((dayOfBirh.getYear()>=1970) && (dayOfBirh.getYear()<=1999)) {
				if(gender.equals(Gender.MALE)) { 
					return Salutation.ANH;
				} else if(gender.equals(Gender.FEMALE)) {
					return Salutation.CHI;
				}
			} 
		} 
			return Salutation.UNDEFINED;
		
	}
	
	
//==========================================Gender---------------------------------------------	
	
	/* =========================
    RAW DATA (CÓ DẤU)
 ========================= */

 private static final Set<String> MALE_PREFIX_RAW = Set.of("Ông");
 private static final Set<String> FEMALE_PREFIX_RAW = Set.of("Bà");

 private static final String MALE_MIDDLE_RAW = "Văn";
 private static final String FEMALE_MIDDLE_RAW = "Thị";

 private static final Set<String> MALE_NAMES_RAW = Set.of(
         "Dũng", "Mạnh", "Cường", "Hùng", "Hưng", "Tấn",
         "Tùng", "Bách", "Lâm"
 );

 private static final Set<String> FEMALE_NAMES_RAW = Set.of(
         "Tiên", "Dung", "Lan", "Hoa", "Mai", "Nguyệt",
         "Yến", "Thơ", "Vy", "Nhung", "Trâm",
         "Như", "Duyên", "Thúy", "Thùy"
 );

 /* =========================
    NORMALIZED DATA (KHÔNG DẤU)
 ========================= */

 private static final Set<String> MALE_PREFIX;
 private static final Set<String> FEMALE_PREFIX;
 private static final String MALE_MIDDLE;
 private static final String FEMALE_MIDDLE;
 private static final Set<String> MALE_NAMES;
 private static final Set<String> FEMALE_NAMES;

 static {
     MALE_PREFIX = normalizeSet(MALE_PREFIX_RAW);
     FEMALE_PREFIX = normalizeSet(FEMALE_PREFIX_RAW);

     MALE_MIDDLE = LowcaseAndRemoveAcent(MALE_MIDDLE_RAW);
     FEMALE_MIDDLE = LowcaseAndRemoveAcent(FEMALE_MIDDLE_RAW);

     MALE_NAMES = normalizeSet(MALE_NAMES_RAW);
     FEMALE_NAMES = normalizeSet(FEMALE_NAMES_RAW);
 }

 /* =========================
    PUBLIC API
 ========================= */

 public static Gender detectGenderFromFullName(String fullName) {

     if (fullName == null || fullName.isBlank()) {
         return Gender.UNDEFINED;
     }

     List<String> parts = splitAndLowcaseAndRemoveAcent(fullName);

     if (parts.isEmpty()) {
         return Gender.UNDEFINED;
     }

     /* ========= 1️⃣ PREFIX ========= */
     String firstWord = parts.get(0);
     if (MALE_PREFIX.contains(firstWord)) {
         return Gender.MALE;
     }
     if (FEMALE_PREFIX.contains(firstWord)) {
         return Gender.FEMALE;
     }

     /* ========= 2️⃣ MIDDLE NAME NEXT TO SURNAME ========= */
     // parts[0] = họ
     // parts[1] = tên lót cạnh họ (nếu tồn tại)
     if (parts.size() >= 2) {
         String middleNearSurname = parts.get(1);

         if (FEMALE_MIDDLE.equals(middleNearSurname)) {
             return Gender.FEMALE;
         }
         if (MALE_MIDDLE.equals(middleNearSurname)) {
             return Gender.MALE;
         }
     }

     /* ========= 3️⃣ GIVEN NAME (LAST PART) ========= */
     List<String> partsHasAcent = splitAndCapitalizeAndNotRemoveAcent(fullName);
     String givenName = partsHasAcent.get(partsHasAcent.size() - 1);
//     System.out.println("givenName: "+givenName);
     if (FEMALE_NAMES_RAW.contains(givenName)) {
         return Gender.FEMALE;
     }
     if (MALE_NAMES_RAW.contains(givenName)) {
         return Gender.MALE;
     }

     return Gender.UNDEFINED;
 }

 /* =========================
    INTERNAL HELPERS
 ========================= */

 private static List<String> splitAndLowcaseAndRemoveAcent(String fullName) {
     String normalized = LowcaseAndRemoveAcent(fullName);
     return Arrays.stream(normalized.split("\\s+"))
             .filter(s -> !s.isBlank())
             .toList();
 }
 
 private static List<String> splitAndCapitalizeAndNotRemoveAcent(String fullName) {
     String normalized = capitalizeFirstLetter(fullName).trim();
     return Arrays.stream(normalized.split("\\s+"))
             .filter(s -> !s.isBlank())
             .toList();
 }
 
 private static String LowcaseAndRemoveAcent(String input) {
     return VietnameseUtils
             .removeAccent(input)
             .toLowerCase(Locale.ROOT)
             .trim();
 }
 
 public static String capitalizeFirstLetter(String input) {
	    if (input == null || input.isBlank()) {
	        return input;
	    }
	    return input.substring(0, 1).toUpperCase() + input.substring(1);
	}

 private static Set<String> normalizeSet(Set<String> rawSet) {
     return rawSet.stream()
             .map(GenderProcess::LowcaseAndRemoveAcent)
             .collect(Collectors.toUnmodifiableSet());
 }

 public GenderProcess() {}
}
