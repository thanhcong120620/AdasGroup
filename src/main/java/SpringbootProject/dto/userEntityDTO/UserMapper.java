package SpringbootProject.dto.userEntityDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import SpringbootProject.entity.UserEntity;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Chuyển đổi UserEntity thành UserDto
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "genderUser", target = "genderUser")
    @Mapping(source = "gmail", target = "gmail")
    @Mapping(source = "status", target = "status")
    UserDto toDto(UserEntity userEntity);

    // Chuyển đổi UserDto thành UserEntity
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "genderUser", target = "genderUser")
    @Mapping(source = "gmail", target = "gmail")
    @Mapping(source = "status", target = "status")
    UserEntity toEntity(UserDto userDto);
}