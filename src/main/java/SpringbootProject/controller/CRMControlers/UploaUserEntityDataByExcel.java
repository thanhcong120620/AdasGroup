package SpringbootProject.controller.CRMControlers;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import SpringbootProject.algorithms.IOAlgorithm.IOFunction;
import SpringbootProject.entity.UserEntity;

@Controller
public class UploaUserEntityDataByExcel {
	//------------------------------Đang test trên Thymleaf - Hãy chuyển đổi về Vue-------------------------------------	
	
	/*
	 * Chạy trên thymleaf
	 * Upload danh sách UserEntity bằng file Excel từ client
	 * */
	@GetMapping("/data-from-excel")
	public String dataFromExcel() {

		
		return "/TestDevelop/dataFromExcel";
	}
	
	/*
	 * Chạy trên thymleaf
	 * Upload danh sách UserEntity bằng file Excel từ client
	 * */
	@PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile[] files) throws IllegalStateException, IOException {
		System.out.println("1");
		
        IOFunction ioFunction = new IOFunction();
        List<UserEntity> users = ioFunction.userListFromExcelFile(files);
        
        for (UserEntity user : users) {
            System.out.println("From controller: "+user.getFullName());
        }
		
		return "/TestDevelop/dataFromExcel";
	}
}
