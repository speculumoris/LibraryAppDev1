package com.lib.mapper;

import com.lib.domain.User;
import com.lib.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    List<UserDTO> map(List<User> userList);


    UserDTO userToUserDTO(User user);




}
