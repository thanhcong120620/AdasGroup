package SpringbootProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringbootProject.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByGmail(String mailUser);
}
