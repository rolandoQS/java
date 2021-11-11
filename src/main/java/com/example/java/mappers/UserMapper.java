package com.example.java.mappers;


import com.example.java.Request.CreateUserRequest;
import com.example.java.entities.UserEntity;
import com.example.java.models.User;
import com.example.java.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CommonMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userEntityToUser(UserEntity userEntity);
    UserEntity userToUserEntity(User user);
    List<User> listUserEntityToListUser(List<UserEntity> userEntities);
    UserResponse userToUserResponse(User user);
    List<UserResponse> userListToUserResponse(List<User> users);
    @Mapping(target = "phones", ignore = true)
    User createUserRequestToUser(CreateUserRequest createUserRequest);


}
