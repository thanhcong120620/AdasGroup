package SpringbootProject.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SpringbootProject.entity.UserEntity;
import SpringbootProject.repository.UserRepository;
import SpringbootProject.service.IUser;

@Service
@Transactional
public class UserService implements IUser {

	@Autowired
	UserRepository userRepository;
	

	
	@Override
	public UserEntity userCreater(UserEntity userEntity) {
		UserEntity newEntity = userRepository.findById(userEntity.getId()).get();
		newEntity = userRepository.save(userEntity);
//		System.out.println("Đã cập nhật user (id: " + oldEntity.getId() + ")");
		return newEntity;
	}
	
	

	@Override
	public UserEntity userCreateUpdate(UserEntity userEntity) {
		if(userEntity.getId() == null) {
			//Create new user
			userRepository.save(userEntity);
			UserEntity checkUser = userRepository.findById(userEntity.getId()).get();
			System.out.println("Đã tạo mới user (checkUser: " + checkUser.toString());
			return checkUser;
		} else {
			//Update user
			UserEntity newEntity = userRepository.findById(userEntity.getId()).get();
			newEntity = userRepository.save(userEntity);
			UserEntity checkUser = userRepository.findById(newEntity.getId()).get();
			System.out.println("Đã cập nhật user (User: " + checkUser.toString());
			return checkUser;
		}

		
		
	}

	@Override
	public List<UserEntity> findAllUser() {
		List<UserEntity> UserList = userRepository.findAll();
		if(UserList.size() == 0) {
			System.out.println("User data is null !");
			UserEntity demoUser = new UserEntity();
			demoUser.setFullName("- -");
			demoUser.setGenderUser("-");
			demoUser.setGmail("-");
			demoUser.setStatus("-");
			UserList.add(demoUser);
			return UserList;
		} else {
			return UserList;
		}
		
	}

	@Override
	public UserEntity findById(Long id) {
		UserEntity oldEntity = userRepository.findById(id).get();
		return oldEntity;
	}

	@Override
	public UserEntity findByGmail(String gmail) {
		UserEntity oldEntity = userRepository.findByGmail(gmail);
		return oldEntity;
	}

    // 5. Delete - Xóa user theo id
	@Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
