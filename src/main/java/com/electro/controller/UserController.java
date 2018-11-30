package com.electro.controller;

import com.electro.dto.user.UserRequestDTO;
import com.electro.dto.user.UserResponseDTO;
import com.electro.exception.UserNotFoundException;
import com.electro.models.User;
import com.electro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserRequestDTO userRequestDTO) {
        userService.createUser(userRequestDTO);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody UserRequestDTO userRequestDTO) throws UserNotFoundException {
        userService.updateUser(userRequestDTO);
    }

    @GetMapping("/me")
    public UserResponseDTO getUser(User user) {
        return userService.getMe(user);
    }

    @GetMapping("/get")
    public UserResponseDTO getUser(@RequestParam("id") long id) throws UserNotFoundException {
        return userService.getUser(id);
    }

    @GetMapping("/find")
    public List<UserResponseDTO> findUser(@RequestParam("key") String key) {
        return userService.findUser(key);
    }

}
