package com.electro.dto.user;

import lombok.Data;

@Data
public class UserRequestDTO {

    private long id;
    private String nickName;
    private String password;
    private String mail;

}
