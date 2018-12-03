package com.electro.service;

import com.electro.dto.user.UserRequestDTO;
import com.electro.dto.user.UserResponseDTO;
import com.electro.exception.UserNotFoundException;
import com.electro.models.User;
import com.electro.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(UserRequestDTO userRequestDTO){
        final User user = new User().fromDTO(userRequestDTO);
        user.setPassword(encode(user.getPassword()));

        userRepository.save(user);
    }

    public boolean login(String id, String pw){
        final User user = userRepository.findFirstByMailEqualsAndPasswordEquals(id, pw);

        return (user != null);
    }

    public User findUserNickNameOrMail(String key){
        final User user = userRepository.findFirstByNickNameEqualsOrMailEquals(key, key);

        return user;
    }

    public void updateUser(UserRequestDTO userRequestDTO) throws UserNotFoundException {
        final User user = getUserFromDB(userRequestDTO.getId());

        if (user != null){
            if (!StringUtils.isEmpty(userRequestDTO.getNickName()))
                user.setNickName(userRequestDTO.getNickName());

            if (!StringUtils.isEmpty(userRequestDTO.getPassword()))
                user.setPassword(encode(userRequestDTO.getPassword()));

            if (!StringUtils.isEmpty(userRequestDTO.getMail()))
                user.setMail(userRequestDTO.getMail());

            userRepository.save(user);
        }

    }

    public UserResponseDTO getMe(User user){
        return user.toDTO();
    }

    public UserResponseDTO getUser(long id) throws UserNotFoundException {
        return getUserFromDB(id).toDTO();
    }

    public List<UserResponseDTO> findUser(String key){
        final List<User> result = userRepository.findAllByNickNameContains(key);
        final List<UserResponseDTO> dtos = new ArrayList<>();

        result.forEach(user -> dtos.add(user.toDTO()));
        return dtos;
    }

    private User getUserFromDB(long id) throws UserNotFoundException {
        final Optional<User> optional = userRepository.findById(id);
        final User user = optional.orElse(null);

        if (user == null)
            throw new UserNotFoundException("kullanıcı bulunamadı");

        return user;
    }

    private String encode(String pass){
        return DigestUtils.sha256Hex(pass);
    }

}
