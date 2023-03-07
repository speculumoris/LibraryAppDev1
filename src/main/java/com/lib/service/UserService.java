package com.lib.service;

import com.lib.domain.Role;
import com.lib.domain.User;
import com.lib.domain.enums.RoleType;
import com.lib.dto.UserDTO;
import com.lib.dto.request.AdminCreateByUserRequest;
import com.lib.exception.ConflictException;
import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.mapper.UserMapper;
import com.lib.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, RoleService roleService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;

        this.userMapper = userMapper;
    }


    public Page<UserDTO> getUserPage(Pageable pageable) {

        Page<User> usersPage = userRepository.findAll(pageable);

        return usersPage.map(userMapper::userToUserDTO);

    }


    public UserDTO getUserById(Long id) {
       User user = userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUNT_EXCEPTION, id)));

       UserDTO userDTO = userMapper.userToUserDTO(user);
       return userDTO;
    }


    public void UserCreatedByAdmin(User user, AdminCreateByUserRequest request) {

        boolean exists = userRepository.existsByEmail();

        if(exists){
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, request.getEmail()));
        }

        if(request.getPassword() != null){
            user.setPassword(request.getPassword());  ///   !!! Encode edilecek  --- -
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setBirthDate(request.getBirthDate());
        user.setEmail(request.getEmail());

        Set<String> userStrRole = request.getRoles();
        Set<Role> roles = convertRoles(userStrRole);

        user.setRoles(roles);

        userRepository.save(user);

    }

    // Request den gelen String rolleri ROLE_TYPE cevirme
    private Set<Role> convertRoles(Set<String> pRoles){
        Set<Role> roles = new HashSet<>();

        if(pRoles==null){
            Role userRole = roleService.findByType(RoleType.MEMBER_TYPE);
            roles.add(userRole);
        }else {
            pRoles.forEach(roleStr->{
                if(roleStr.equals(RoleType.ADMIN_TYPE.getName())){
                    Role adminRole = roleService.findByType(RoleType.ADMIN_TYPE);
                    roles.add(adminRole);
                }else if(roleStr.equals(RoleType.EMPLOYEE_TYPE.getName())){
                    Role employeeRole = roleService.findByType(RoleType.EMPLOYEE_TYPE);
                    roles.add(employeeRole);
                }else{
                    Role userRole = roleService.findByType(RoleType.MEMBER_TYPE);
                    roles.add(userRole);
                }
            });
        }
        return roles;
    }



}
