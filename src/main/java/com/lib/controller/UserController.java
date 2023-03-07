package com.lib.controller;

import com.lib.dto.UserDTO;
import com.lib.dto.request.AdminCreateByUserRequest;
import com.lib.dto.request.UserUpdateRequest;
import com.lib.dto.response.LibResponse;
import com.lib.dto.response.ResponseMessage;
import com.lib.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user" )
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/auth/pages")
 //   @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<Page<UserDTO>> getAllUsersByPage(@RequestParam("page") int page,
                                                           @RequestParam("size") int size,
                                                           @RequestParam("sort") String prop,
                                                           @RequestParam(value = "direction",
                                                                   required = false, defaultValue = "DESC")
                                                           Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));

        Page<UserDTO> userDTOPage = userService.getUserPage(pageable);

        return ResponseEntity.ok(userDTOPage);
    }


    @GetMapping("/users/{id}")
    //   @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){

        UserDTO userDTO = userService.getUserById(id);

        return ResponseEntity.ok(userDTO);
    }


    @PostMapping("/users")
    //   @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<LibResponse> UserCreatedByAdmin(@Valid @RequestBody AdminCreateByUserRequest request){

        userService.UserCreatedByAdmin(request);

        LibResponse response = new LibResponse(ResponseMessage.USER_CREATED_RESPONSE, true);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/users/{id}")
    //   @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<LibResponse> updateUser(@PathVariable Long id,
                                                  @Valid @RequestBody UserUpdateRequest userUpdateRequest){

        userService.updateUser(id, userUpdateRequest);

        LibResponse response = new LibResponse();
        response.setMessage(ResponseMessage.USER_UPDATE_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/users/{id}")
    //   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> deleteUser(@PathVariable Long id){

        userService.deleteUser(id);

        LibResponse response = new LibResponse();
        response.setMessage(ResponseMessage.USER_DELETE_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }


    /// authenticated user  currentUser methodu yazilacak






}
