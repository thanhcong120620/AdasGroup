package SpringbootProject.service;

import java.util.List;

import SpringbootProject.entity.UserEntity;

public interface IUser {
	UserEntity save(UserEntity userDTO);
	List<UserEntity> findAllUser();
	UserEntity findById(Long id);
	UserEntity findByGmail(String gmail);
	
}
