package com.electro.dto.streak;

import lombok.Data;

import java.util.Date;

@Data
public class StreakResponseDTO {

    public StreakResponseDTO(long id, String title, Integer likeCount, Integer dissLikeCount, Date createDate, Date modifiedDate, String content) {
        this.id = id;
        this.title = title;
        this.likeCount = likeCount;
        this.dissLikeCount = dissLikeCount;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.content = content;
    }

    private long id;

    private String title;
    private Integer likeCount;
    private Integer dissLikeCount;
    private Date createDate;
    private Date modifiedDate;
    private String content;

    private String headlineName;
    private String nickName;

}
