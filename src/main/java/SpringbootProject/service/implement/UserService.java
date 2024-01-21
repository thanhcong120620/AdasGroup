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
	public UserEntity save(UserEntity userEntity) {
		UserEntity oldEntity = userRepository.findById(userEntity.getId()).get();
		oldEntity = userRepository.save(userEntity);
//		System.out.println("Đã cập nhật user (id: " + oldEntity.getId() + ")");
		return oldEntity;
	}

	@Override
	public List<UserEntity> findAllUser() {
		List<UserEntity> UserList = userRepository.findAll();
		return UserList;
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



}
