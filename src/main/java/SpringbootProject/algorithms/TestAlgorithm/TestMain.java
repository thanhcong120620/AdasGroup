package SpringbootProject.algorithms.TestAlgorithm;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import FileUtil.ContactUtils;
import FileUtil.ContactUtils.ContactResult;
import FileUtil.EnumExtractorUtil;
import FileUtil.StringProcess;
import SpringbootProject.algorithms.IOAlgorithm.SimpleExcelReader;
import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.GenderProcess;
import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.PersonProfileProcessFunction;
import SpringbootProject.entity.enums.Gender;
import SpringbootProject.entity.enums.Salutation;
import SpringbootProject.entity.enums.Status;
import SpringbootProject.entity.notSaving.ExcelObject;

public class TestMain {

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		List<String> testData = List.of(
	            "Nguyen Van A; 0987654321; hanoi@gmail.com",                                      // 1. Đầy đủ, ngăn cách bởi ";"
	            "0912345678, hcm city, user@yahoo.com",                                            // 2. Ngăn cách bởi ","
	            "Da Nang city - 0905123456 - mientrung@hotmail.com",                               // 3. Ngăn cách bởi "-"
	            "0333444555 0333666777 vung tau city",                                             // 4. Hai số điện thoại, không email
	            "contact@company.com; support@company.com; 123 Street, District 1",                // 5. Hai email, không số điện thoại
	            "0988111222; hcm; 0977222333; test@gmail.com; hn; admin@work.com",                 // 6. Hỗn hợp nhiều loại xen kẽ
	            "No info here",                                                                    // 7. Chỉ có địa chỉ (hoặc rác)
	            "0944555666",                                                                      // 8. Chỉ có số điện thoại
	            "only-email@web.vn",                                                               // 9. Chỉ có email
	            "   ",                                                                             // 10. Chuỗi trống
	            "Số 10, đường 12, Q3...0966777888...email.gan.nhau@gmail.com",                      // 11. Địa chỉ có số và dấu chấm
	            "0243.123.456; 0912-345-678; 10 Hoang Dieu",                                       // 12. Số điện thoại định dạng lạ (sẽ bắt theo regex)
	            "sales@store.com|0888999000|Can Tho city|19001000",                                // 13. Ngăn cách bởi dấu gạch đứng "|"
	            "Dung.Nguyen@kmail.com.vn, 0909090909, 456 Le Loi Str.",                           // 14. Email có đuôi phức tạp .com.vn
	            "0911222333 0911222444 0911222555",                                                // 15. Ba số điện thoại
	            "email1@a.com;email2@b.com;email3@c.com",                                          // 16. Ba email
	            "123/45 Q.Tân Bình, TP.HCM; 0707123123",                                           // 17. Địa chỉ có xẹt "/"
	            "++++0982123123++++address_here++++",                                              // 18. Ký tự lạ bao quanh
	            "0901234567; hanoi; 0901234567; hanoi",                                            // 19. Dữ liệu lặp lại
	            "Email: me@work.com, Phone: 0988777666, Addr: 789 Long An"
				);
		
		// Gọi hàm xử lý
//		ContactResult result = ContactUtils.parseMixContacts(testData);
//
//        // In kết quả
//        System.out.println("=== KẾT QUẢ TRÍCH XUẤT ===");
//        System.out.println("PHONES (" + result.phones().size() + "): " + result.phones());
//        System.out.println("EMAILS (" + result.emails().size() + "): " + result.emails());
//        System.out.println("ADDRESSES (" + result.addresses().size() + "): " + result.addresses());
		}
		

//=========================================================	
	
	//Test sự trùng lặp các giá trị String trong 1 List
	static void testListStringDuplicate() {
		List<String> list = List.of(
		        "User_01",
		        "admin",
		        "User_01",          // trùng
		        "ADMIN",            // khác hoa thường
		        " guest ",
		        "guest",            // khác khoảng trắng
		        "user-02",
		        "user-02",          // trùng
		        "user_03",
		        "user#03",          // khác ký tự đặc biệt
		        "123",
		        "123",              // trùng
		        "order_2024_01",
		        "order_2024_01",    // trùng
		        "Hello World",
		        "Hello  World",     // 2 khoảng trắng
		        "hello world",      // khác hoa thường
		        "こんにちは",        // Unicode (Japanese)
		        "你好",              // Unicode (Chinese)
		        "こんにちは"         // trùng Unicode
		);
		List<String> result = StringProcess.removeDuplicates(list);
		for(String s : result) {
        System.out.println(s); // [A, B, C, D]
		}
	};
	
	//Test sự hoạt động của GenderProcess
	static void testGenderProcess() {
		String[] testCases = {
                "ONG NGUYEN THANH CONG",
                "NGUYEN THANH CONG",
                "NGUYỄN THÀNH CÔNG",
                "Nguyễn Thành Công",
                "Ông Nguyễn Thành Công",
                "ong nguyen thanh cong",

                "Bà Nguyễn Thị Hoàng Thi",
                "ba nguyen thi hoang thi",
                
                "Nguyễn Thị Hoàng Thi",
                "nguyen van thi hoang thi",

                "Nguyễn Văn Công",
                "nguyen van cong",

                "Nguyễn Thị Hoàng Thi",
                "nguyen thi hoang thi",

                "nguyen thi van",
                "nguyen van thi sach",
                "nguyen thi van sach",

                "Nguyễn Lan",
                "Nguyễn Dũng",
                "Nguyễn Mai",
                "Nguyễn Hùng",

                null,
                "",
                "   "
        };

        System.out.println("====== TEST GENDER DETECTION ======");
        GenderProcess genderProcess = new GenderProcess();
        for (String fullName : testCases) {
            Gender gender = genderProcess.detectGenderFromFullName(fullName);

            System.out.printf(
                    "Input: %-30s => Gender: %-10s | Label: %s%n",
                    String.valueOf(fullName),
                    gender.name(),
                    gender.getLabel()
            );
        }

        System.out.println("====== END TEST ======");
	}
	
	
	//Test giá trị được lấy ra dữ liệu của một enum
	private static void testLabelEnum(String input) {
        try {
            System.out.println("Input: " + input);
            Status status = Status.fromLabel(input);
            System.out.println("=> Result: " + status);
        } catch (Exception e) {
            System.out.println("=> Error: " + e.getMessage());
        }
        System.out.println("---------------------");
    }
	
	public static void testEnumExtractorUtil (Object entity) {
		System.out.println("=== ENUM NAME MAP ===");
	    EnumExtractorUtil.extractEnumMap(entity)
	            .forEach((k, v) -> System.out.println(k + " -> " + v));

	    System.out.println("\n=== ENUM LABEL MAP ===");
	    EnumExtractorUtil.extractEnumLabelMap(entity)
	            .forEach((k, v) -> System.out.println(k + " -> " + v));
	}
	
	
	
	public static void testGetName () {
		// Dữ liệu test
        String[][] data = {
                {"HOANG THI LE THANH DINH", "Thanh Định"},
                {"NGUYEN THI XUAN NGUYEN", "Nguyên nguyễn"},
                {"NGUYEN THI XUAN HUYEN", "Xuân Huyền"},
                {"LE THI CHIEU AN", "Lê Thị Chiêu An"},
                {"CAN THI TINH", "Tình Cần"},
                {"NGUYEN HIEN LUONG", "Hien Luong"},
                {"DOAN THI THU THUY", "Đoàn Thị Thu Thủy"},
                {"HONG THI ANH HONG", "Anh Hong"},
                {"HO THI ANH HONG", "Anh Hong"},
                {"NGUYEN THI THANH LINH", "Linh Nguyen"},
                {"TRAN THI TU", "Tư Trần"},
                {"TRAN THI NHAN", "Nhan"},
                {"HUYNH THI NGOC HOA", "Huỳnh Hoa"},
                {"NGUYEN THI LE HOA", "Lệ Hoa"},
                {"TRAN CONG ANH", "Trần Anh"},
                {"NGUYEN THI NHU THAO", "Thảo"},
                {"CAO THI NGUYET HOA", "Cao Nguyệt Hoa"},
                {"PHAM THI THANH", "Pham Thị Thanh"},
                {"NGUYEN CONG LY", "Nguyễn Công Lý"},
                {"NGUYEN THI MY HUONG", "Nguyễn Thị Mỹ Hương"},
                {"HUYNH THI THU HUONG", "Huỳnh Hương"},
                {"NGUYEN THI NGOC BA", "Nguyễn Thị Ngọc Ba"},
                {"TRAN THI THANH THUY", "Thanh Thủy"},
                {"NGUYEN THI HONG THO", "Hồng Tho Nguyễn"},
                {"HUYNH THI TU", "Huỳnh Thị Tư"},
                {"NGUYEN HOANG LIÊM", "Hoangliem Nguyen"},
                {"PHAM MANH KHA", "Manh Kha"},
                {"LE THI THANH HA", "Ha Nguyễn"},
                {"LE THI THUY HA", "Ha Le"},
                {"NGO THI HUONG", "Huong Tdtm"},
                {"NGUYEN HUU NAM", "Nam Nguyễn"},
                {"HUA THI PHUONG", "Phuonghua"},
                {"HUYNH THI LIEN HUONG", "Huong Liên Huynh"},
                {"NGUYEN TRONG MAN", "Trong Man"},
                {"HUYNH THI KIM THU", "Thu"},
                {"NGUYEN THI KIM ANH", "Nguyễn Thị Kim Anh"},
                {"NGUYEN THI HONG ANH", "Nguyễn Thị Kim Anh"},
                {"NGUYEN THI TƯ ANH", "Nguyễn Thị Kim Anh"},
                {"NGUYEN THI KIM ANH", "Nguyễn Thị Kim Ánh"},
                {"PHAM THI YEN", "Yen.pham"},
                {"PHAM CHAN", "Chan Pham"},
                {"LE THI THU VINH", "Thuvinh"},
                {"LE THI HAI ANH", "Haianh Le"},
                {"PHAM BON", "Phambonqh"},
                {"PHAM DUC", "Đức Phạm"},
                {"PHAM Dong", "Đông Phạm"}
        };

        System.out.println("Fullname\t\tNickname\t\tnames[0]\t\tnames[1]\t\tnames[2]");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        for (String[] row : data) {
            String fullName = row[0];
            String nickname = row[1];
            String[] names = extractFirstName(fullName, nickname);
            System.out.println(fullName + "\t\t" + nickname + "\t\t" + names[0] + "\t\t" + names[1] + "\t\t" + names[2]);
        }
		
	}
	
	/*
	 * Function test last name from String
	 * Use for Excel Object
	 * */
	public static String[] extractFirstName (String fullName, String nickname) {
		PersonProfileProcessFunction pppFunction = new PersonProfileProcessFunction();
		return pppFunction.extractFirstName(fullName, nickname);
	}
	
	
	public static void printSentences() {
		// Khởi tạo Scanner để đọc dữ liệu từ bàn phím
        Scanner scanner = new Scanner(System.in);

        // Hướng dẫn người dùng nhập chuỗi
        System.out.println("Nhập chuỗi dài (Nhập dòng trống để kết thúc): ");
        
        // Đọc nhiều dòng đầu vào từ người dùng cho đến khi họ nhập dòng trống
        StringBuilder inputBuilder = new StringBuilder();
        String line;
        
        // Đọc các dòng nhập vào và nối lại với nhau
        while (!(line = scanner.nextLine()).isEmpty()) {
            inputBuilder.append(line).append(" "); // Thêm khoảng trắng giữa các dòng
        }

        // Lấy chuỗi đầy đủ từ StringBuilder
        String input = inputBuilder.toString().trim();

        // Tách chuỗi thành các câu kết thúc bằng dấu chấm (có thể có khoảng trắng sau dấu chấm)
        String[] sentences = input.split("(?<=\\.)");

        // In các câu ra với dấu "-" đầu dòng
        for (String sentence : sentences) {
            // Nếu câu không rỗng, in ra dấu "-" đầu dòng
            if (!sentence.trim().isEmpty()) {
                System.out.println("- " + sentence.trim());
            }
        }

        // Đóng scanner sau khi sử dụng
        scanner.close();
    }
	
	
	
	/*
	 * 
	 * */
	public static List<ExcelObject> getDataFromExcelSimple(String filePath) {
		SimpleExcelReader algorithmReaderExcel = new SimpleExcelReader();
//		String filePath = "D:\\Desktop\\Diary\\ExcelObject.xlsx";
		File file = new File(filePath);
		List<ExcelObject> excelObjects = algorithmReaderExcel.readFile(file);
		
		//check result
       for (ExcelObject excelObject : excelObjects) {
           System.out.println(">>"+excelObject);
       }
		
		return excelObjects;
	}
	
	
	
	/*
	 * 
	 * */
//	public static void algorithmWritterTest() {
//		String filePathSaving = "D:\\Desktop\\Diary\\ExcelObjectWitter.xlsx";
//		 // Giả sử bạn có một list các entity
//       List<ExcelObject> excelObjectList = List.of(
//           new ExcelObject("Value122", "Value2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15", "Value16", "Value17", "Value18", "Value19", "Value20"),
//           new ExcelObject("Value1", "Value2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15", "Value16", "Value17", "Value18", "Value19", "Value20")
//       );
//       IOFunction.algorithmWitter(filePathSaving, excelObjectList);
//	}

}
