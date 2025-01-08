package SpringbootProject.algorithms.TestDevelop;

import java.util.List;

import SpringbootProject.algorithms.IOAlgorithm.IOFunction;
import SpringbootProject.entity.UserEntity;

public class TestMain {

	public static void main(String[] args) {
		 algorithmTest();

	}
	
	public static void algorithmTest() {
		// init iofunction object
		IOFunction ioFunction = new IOFunction();
		String filePath = "D:\\Desktop\\Diary\\Users.xlsx";
		
		//use function from iofunction
		List<UserEntity> users = ioFunction.userListFromExcel(filePath);
		
		//check result
        for (UserEntity user : users) {
            System.out.println(">>"+user);
        }
	}

}
