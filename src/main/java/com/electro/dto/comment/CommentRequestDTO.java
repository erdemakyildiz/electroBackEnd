package com.electro.dto.comment;

import lombok.Data;

/**
 * Created by Erdem Akyıldız on 4.12.2018.
 */
@Data
public class CommentRequestDTO {

    private long id;

    private String comment;
    private long streakId;
    private long parentCommentId;

}
