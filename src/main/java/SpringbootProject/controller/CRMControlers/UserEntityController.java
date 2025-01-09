package SpringbootProject.controller.CRMControlers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import SpringbootProject.algorithms.IOAlgorithm.IOFunction;
import SpringbootProject.entity.UserEntity;
import SpringbootProject.service.IUser;



//@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*") 
@RequestMapping("/users")
@RestController 
public class UserEntityController {
	@Autowired
	private IUser userService;
	
	


	
	/*
	 * Chạy trên vue
	 * Dùng khi bạn muốn cập nhật tài nguyên hiện có trên server. 
	 * PUT thường thay thế toàn bộ tài nguyên (hoặc cập nhật tất cả thông tin của đối tượng).
	 * */
	@PutMapping("/test-crud-update-user")
	public ResponseEntity<String> updateUser(@RequestBody UserEntity userClient) {
		userService.userCreateUpdate(userClient);
		UserEntity user = userService.findById(userClient.getId());
		
		return ResponseEntity.ok("Đã cập nhật user: " + user);
	}
	
	
	
	/*
	 * Chạy trên vue
	 * Dùng khi bạn muốn tạo mới một đối tượng hoặc tài nguyên trên server (như thêm mới một người dùng, một bài viết
	 * */
	@PostMapping("/test-crud-create-user")
	public ResponseEntity<String> createUser(@RequestBody UserEntity userClient) {
		userService.userCreateUpdate(userClient);
		UserEntity user = userService.findById(userClient.getId());
	
		return ResponseEntity.ok("Đã tạo mới user: " + user);
	}
	
	
	/*
	 * Chạy trên vue
	 * Dùng khi bạn muốn trả về dữ liệu từ server, ví dụ như khi truy vấn dữ liệu từ cơ sở dữ liệu hoặc khi lấy thông tin.
	 * */
	@GetMapping("/test-crud-show-userList")
	public ResponseEntity<List<UserEntity>> showUserList() {
	    List<UserEntity> userResponse = userService.findAllUser();
	    return ResponseEntity.ok(userResponse); // Trả về danh sách người dùng dưới dạng JSON
	}
	
	
	/*
	 * Chạy trên vue
	 * Dùng khi bạn muốn xóa một tài nguyên hoặc đối tượng khỏi hệ thống.
	 * */
	@DeleteMapping("/test-crud-delete-user")
	public ResponseEntity<String> deleteUser(@RequestBody List<Long> userIds) {
		for(Long id:userIds) {
			userService.deleteUser(id);
//			System.out.println("Id: "+ id + "from controller");
		};
		

		return ResponseEntity.ok("Deleted users with IDs: " + userIds);
	}
	
//----------------------------------------------------------------------------------------------------------------
	

	

}
