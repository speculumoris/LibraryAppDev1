package com.lib.service;

import com.lib.domain.Role;
import com.lib.domain.User;
import com.lib.domain.enums.RoleType;
import com.lib.dto.LoanDTO;
import com.lib.dto.UserDTO;
import com.lib.dto.request.AdminCreateByUserRequest;
import com.lib.dto.request.RegisterRequest;
import com.lib.dto.request.UserUpdateRequest;
import com.lib.exception.BadRequestException;
import com.lib.exception.ConflictException;

import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.mapper.UserMapper;
import com.lib.repository.UserRepository;
import com.lib.security.SecurityUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleService roleService,
                       UserMapper userMapper, @Lazy PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public Page<UserDTO> getUserPage(Pageable pageable) {

        Page<User> usersPage = userRepository.findAll(pageable);

        return getUserDTOPage(usersPage);
    }


    private Page<UserDTO> getUserDTOPage(Page<User> userPage) {
        return userPage.map(
                user -> userMapper.userToUserDTO(user)
        );
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_EXCEPTION, id)));

        UserDTO userDTO = userMapper.userToUserDTO(user);
        return userDTO;
    }


    public void UserCreatedByAdmin(AdminCreateByUserRequest request) {


        User user = new User();
        boolean exists = userRepository.existsByEmail(request.getEmail());

        if (exists) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, request.getEmail()));
        }

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setBirthDate(request.getBirthDate());
        user.setEmail(request.getEmail());

        Set<String> userStrRole = request.getRoles();

        Set<Role> roles = convertRoles(userStrRole);

        User currentUser = getCurrentUser();

        if (currentUser.getRoles().equals(RoleType.ROLE_EMPLOYEE)) {
            if (!roles.equals(RoleType.ROLE_MEMBER)) {
                throw new ResourceNotFoundException(ErrorMessage.UNAUTHRIZED_FOUND_MESSAGE);
            }
        }

        user.setRoles(roles);

        userRepository.save(user);
    }


    public void updateUser(Long id, UserUpdateRequest userUpdateRequest) {

        User user = getById(id);

        // builtIn control
        if (user.isBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        // email control
        boolean emailExist = userRepository.existsByEmail(userUpdateRequest.getEmail());

        if (emailExist && !userUpdateRequest.getEmail().equals(user.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE,
                    userUpdateRequest.getEmail()));
        }


        // password control
        if (userUpdateRequest.getPassword() == null) {
            userUpdateRequest.setPassword(user.getPassword());
        } else {
            String encodedPassword = passwordEncoder.encode(userUpdateRequest.getPassword());
            userUpdateRequest.setPassword(encodedPassword);
        }


        //Rol kontrol
        Set<String> userStrRoles = userUpdateRequest.getRoles();
        Set<Role> roles = convertRoles(userStrRoles);

        // ADMIN TUM KULLANICILARI UPDATE EDER - EMPLOYEE ISE SADECE MEMBER'I UPDATE EDER
        User currentUser = getCurrentUser();

        if (currentUser.getRoles().equals(RoleType.ROLE_EMPLOYEE) && !roles.equals(RoleType.ROLE_MEMBER)) {
            throw new ResourceNotFoundException(ErrorMessage.UNAUTHRIZED_FOUND_MESSAGE);
        }

        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setScore(userUpdateRequest.getScore());
        user.setAddress(userUpdateRequest.getAddress());
        user.setPhone(userUpdateRequest.getPhone());
        user.setBirthDate(userUpdateRequest.getBirthDate());
        user.setPassword(userUpdateRequest.getPassword());

        Set<Role> role = convertRoles(userUpdateRequest.getRoles());
        user.setRoles(role);

        userRepository.save(user);
    }


    public User getById(Long id) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_EXCEPTION, id)));
        return user;
    }


    // Request den gelen String rolleri ROLE_TYPE cevirme
    private Set<Role> convertRoles(Set<String> pRoles) {
        Set<Role> roles = new HashSet<>();

        if (pRoles == null) {
            Role userRole = roleService.findByType(RoleType.ROLE_MEMBER);
            roles.add(userRole);
        } else {
            pRoles.forEach(roleStr -> {
                if (roleStr.equals(RoleType.ROLE_ADMIN.getName())) {
                    Role adminRole = roleService.findByType(RoleType.ROLE_ADMIN);
                    roles.add(adminRole);
                } else if (roleStr.equals(RoleType.ROLE_EMPLOYEE.getName())) {
                    Role employeeRole = roleService.findByType(RoleType.ROLE_EMPLOYEE);
                    roles.add(employeeRole);
                } else {
                    Role userRole = roleService.findByType(RoleType.ROLE_MEMBER);
                    roles.add(userRole);
                }
            });
        }
        return roles;
    }


    public void deleteUser(Long id) {
        User user = getById(id);

        boolean builtIn = user.isBuiltIn();

        if (builtIn) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        // member in emanette kitabi var mi kontrol edilecek

        //   !!!!!!
        //    !!!


        userRepository.delete(user);
    }


    public User getUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_EXCEPTION, email)));
        return user;
    }

    public void saveUser(RegisterRequest registerRequest) {


        // email daha once kullanildi mi
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ClassCastException(
                    String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, registerRequest.getEmail()));
        }

        // default olarak member rol bligisi atandi
        Role role = roleService.findByType(RoleType.ROLE_MEMBER);

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        //DB ye gitmeden once password encode edildi
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        String encodedResetPassword = passwordEncoder.encode(registerRequest.getResetPasswordCode());


        // !! yeni kullanicinin gerkli bilgilerini setleyip DB ye gonderiyoruz.

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setPhone(registerRequest.getPhone());
        user.setAddress(registerRequest.getAddress());
        user.setResetPasswordCode(encodedResetPassword);
        user.setBirthDate(registerRequest.getBirthDate());

        user.setRoles(roles);

        userRepository.save(user);
    }


    // Sisteme giris yapan kullanici bilgisi
    public UserDTO getPrincipal() {

        User user = getCurrentUser();
        UserDTO userDTO = userMapper.userToUserDTO(user);
        return userDTO;
    }

    public User getCurrentUser() {
        String email = SecurityUtils.getCurrentUserLogin().orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.PRINCIPAL_FOUND_MESSAGE));
        User user = getUserByEmail(email);

        return user;
    }


}