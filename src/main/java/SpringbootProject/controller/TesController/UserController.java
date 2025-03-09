package SpringbootProject.controller.TesController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringbootProject.dto.userEntityDTO.UserDto;
import SpringbootProject.dto.userEntityDTO.UserDtoService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDtoService userDtoService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userDtoService.getAllUsers();  // Lấy tất cả người dùng dưới dạng UserDto
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userDtoService.getUserById(id);  // Lấy thông tin người dùng theo ID dưới dạng UserDto
    }
}
