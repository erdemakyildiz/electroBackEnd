package com.electro.dto.streak;

import com.electro.models.Category;
import lombok.Data;

@Data
public class StreakRequestDTO {

    private long id;
    private String title;
    private String content;
    private String categoryName;

}
