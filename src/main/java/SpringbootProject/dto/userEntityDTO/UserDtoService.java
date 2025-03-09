package SpringbootProject.dto.userEntityDTO;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpringbootProject.entity.UserEntity;
import SpringbootProject.service.IUser;

@Service
public class UserDtoService {

    @Autowired
    private IUser userService;

    private UserMapper userMapper;

    // Lấy tất cả người dùng
    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntities = userService.findAllUser();
        return userEntities.stream()
                           .map(userMapper::toDto)  // Chuyển UserEntity thành UserDto
                           .collect(Collectors.toList());
    }

    // Tìm người dùng theo ID
    public UserDto getUserById(Long id) {
        UserEntity userEntity = userService.findById(id);
        return userMapper.toDto(userEntity);  // Chuyển UserEntity thành UserDto
    }

    // Tìm người dùng theo Gmail
    public UserDto getUserByGmail(String gmail) {
        UserEntity userEntity = userService.findByGmail(gmail);
        return userMapper.toDto(userEntity);  // Chuyển UserEntity thành UserDto
    }
}