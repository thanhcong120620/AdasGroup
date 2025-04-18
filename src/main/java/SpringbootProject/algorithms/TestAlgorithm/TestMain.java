package SpringbootProject.algorithms.TestAlgorithm;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import SpringbootProject.algorithms.IOAlgorithm.IOFunction;
import SpringbootProject.algorithms.IOAlgorithm.SimpleExcelReader;
import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.PersonProfileProcessFunction;
import SpringbootProject.entity.notSaving.ExcelObject;

public class TestMain {

	public static void main(String[] args) {

		System.out.println("Lấy tên1: "+ extractFirstName("NGUYEN THANH CONG", "thành công"));
		System.out.println("Lấy tên2: "+ extractFirstName("NGUYEN THANH CONG", "công"));
		System.out.println("Lấy tên3: "+ extractFirstName("NGUYEN THANH CONG", "công nguyễn"));
		System.out.println("Lấy tên4: "+ extractFirstName("NGUYỄN THÀNH CÔNG", "thành công"));
		System.out.println("Lấy tên5: "+ extractFirstName("NGUYỄN THÀNH CÔNG", "công"));
		System.out.println("Lấy tên6: "+ extractFirstName("NGUYỄN THÀNH CÔNG", "công nguyễn"));
    
}
		

	
	
	/*
	 * Function test last name from String
	 * Use for Excel Object
	 * */
	public static String extractFirstName (String fullName, String nickname) {
		PersonProfileProcessFunction pppFunction = new PersonProfileProcessFunction();
		return pppFunction.getNomalizeName(fullName);
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
	public static void algorithmWritterTest() {
		String filePathSaving = "D:\\Desktop\\Diary\\ExcelObjectWitter.xlsx";
		 // Giả sử bạn có một list các entity
       List<ExcelObject> excelObjectList = List.of(
           new ExcelObject("Value122", "Value2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15", "Value16", "Value17", "Value18", "Value19", "Value20"),
           new ExcelObject("Value1", "Value2", "Value3", "Value4", "Value5", "Value6", "Value7", "Value8", "Value9", "Value10", "Value11", "Value12", "Value13", "Value14", "Value15", "Value16", "Value17", "Value18", "Value19", "Value20")
       );
       IOFunction.algorithmWitter(filePathSaving, excelObjectList);
	}

}
