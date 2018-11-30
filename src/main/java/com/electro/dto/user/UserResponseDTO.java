package com.electro.dto.user;

import com.electro.models.Streak;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {

    public UserResponseDTO() {
    }

    public UserResponseDTO(String nickName, String mail, List<Streak> streakList) {
        this.nickName = nickName;
        this.mail = mail;
        this.streakList = streakList;
    }

    private String nickName;
    private String mail;
    private List<Streak> streakList;

}
