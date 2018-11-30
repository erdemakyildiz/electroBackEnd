package com.electro.models;

import com.electro.dto.user.UserRequestDTO;
import com.electro.dto.user.UserResponseDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class User {

    public User() {
    }

    public User(long id,String nickName, String password, String mail) {
        this.id = id;
        this.nickName = nickName;
        this.password = password;
        this.mail = mail;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nickName;
    private String password;
    private String mail;

    @OneToMany
    private List<Streak> streaks;

    public User fromDTO(UserRequestDTO dto){
        final User user = new User(dto.getId(), dto.getNickName(), dto.getPassword(), dto.getMail());
        return user;
    }

    public UserResponseDTO toDTO() {
        final UserResponseDTO userResponseDTO = new UserResponseDTO(getNickName(), getMail(), getStreaks());
        return userResponseDTO;
    }
}
