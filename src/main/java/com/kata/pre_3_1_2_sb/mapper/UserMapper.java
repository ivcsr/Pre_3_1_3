package com.kata.pre_3_1_2_sb.mapper;

import com.kata.pre_3_1_2_sb.api.request.UserRequest;
import com.kata.pre_3_1_2_sb.api.response.UserResponse;
import com.kata.pre_3_1_2_sb.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> userList);

    User toUser(UserRequest userRequest);

    UserRequest toUserRequest(User user);
}
