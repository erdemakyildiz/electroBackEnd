package com.electro.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Erdem Akyıldız on 4.12.2018.
 */
@Entity
@Data
public class Comment {

    public Comment() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String comment;
    private Date createDate;
    private Date modifiedDate;
    private int up;
    private int down;
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    private Streak streak;

    @OneToOne(fetch = FetchType.LAZY)
    private Comment parentComment;

    @ManyToOne
    private User createdUser;

}
