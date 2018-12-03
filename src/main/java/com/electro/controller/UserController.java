package com.electro.controller;

import com.electro.dto.user.UserRequestDTO;
import com.electro.dto.user.UserResponseDTO;
import com.electro.exception.AuthenticationException;
import com.electro.exception.UserNotFoundException;
import com.electro.models.User;
import com.electro.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value( "${secret.key}" )
    private String secretKey;

    @Autowired
    private UserService userService;

    @PostMapping("/auth")
    @ResponseBody
    public String checkAuth(@RequestParam("id") String id, @RequestParam("pw") String pw) throws AuthenticationException {
        User user = userService.findUserNickNameOrMail(id);

        if (!user.getPassword().equals(DigestUtils.sha256Hex(pw))){
            throw new AuthenticationException();
        }else {

            String jwtToken = Jwts.builder().setSubject(user.getMail()).claim("roles", "user").setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, secretKey).compact();

            return jwtToken;
        }
    }



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
