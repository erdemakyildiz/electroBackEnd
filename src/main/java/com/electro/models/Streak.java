package com.electro.models;

import com.electro.dto.streak.StreakRequestDTO;
import com.electro.dto.streak.StreakResponseDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Streak {

    public Streak() {
    }

    public Streak(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private Integer likeCount = 0;
    private Integer dissLikeCount = 0;
    private Date createDate;
    private Date modifiedDate;
    private String content;

    private boolean active = true;

    @ManyToOne
    private User createdUser;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    public Streak fromDTO(StreakRequestDTO dto) {
        final Streak streak = new Streak(dto.getId() ,dto.getTitle(), dto.getContent());
        return streak;
    }

    public StreakResponseDTO toDTO() {
        final StreakResponseDTO streakResponseDTO = new StreakResponseDTO(getId(), getTitle(), getLikeCount(), getDissLikeCount(), getCreateDate(), getModifiedDate(), getContent());
        return streakResponseDTO;
    }
}