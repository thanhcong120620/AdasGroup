package SpringbootProject.algorithms.TestDevelop;

import SpringbootProject.algorithms.GmailProcess;

public class TestDevelopMain {
	GmailProcess gmailProcess = new GmailProcess();	

	public static void main(String[] args) {
		 	printNames("Alice", "Bob", "Charlie");
	        printNames("John");
	        printNames(); // Không truyền tham số
	        
	        System.out.println("D:\\Desktop\\My data\\1.My working\\1.IVC\\2.ICV-Digital\\1.Develop\\1.IVCDevelop\\2.BackEnd\\2.IVCBackEnd\\AdminSystem\\src\\main\\resources\\static\\image\\miaPoster1.png");

	}
	
	public static void printNames(String... names) {
        if (names.length == 0) {
            System.out.println("Không có tên nào được truyền vào.");
        } else {
            for (String name : names) {
                System.out.println("Tên: " + name);
            }
        }
    }

}
