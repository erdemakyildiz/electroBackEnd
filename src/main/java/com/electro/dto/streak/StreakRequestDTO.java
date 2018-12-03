package com.electro.dto.streak;

import lombok.Data;

@Data
public class StreakRequestDTO {

    private long id;
    private String title;
    private byte[] content;
    private String headlineName;

}
