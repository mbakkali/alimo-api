package com.sunitkatkar.blogspot.web.mappers;

import com.sunitkatkar.blogspot.tenant.model.User;
import com.sunitkatkar.blogspot.web.dtos.UserDTO;
import org.springframework.beans.BeanUtils;

public class UserMapper {

    public static User toUser(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

    public static UserDTO toDto(User user){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
}
