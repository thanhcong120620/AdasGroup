package SpringbootProject.service;

import java.util.List;

import SpringbootProject.entity.UserEntity;

public interface IUser {
	UserEntity userCreateUpdate(UserEntity userDTO);
	List<UserEntity> findAllUser();
	UserEntity findById(Long id);
	UserEntity findByGmail(String gmail);
	UserEntity userCreater(UserEntity userEntity);
	void deleteUser(Long id);
}
