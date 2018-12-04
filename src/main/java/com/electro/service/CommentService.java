package com.electro.service;

import com.electro.dto.comment.CommentRequestDTO;
import com.electro.exception.CommentNotFoundException;
import com.electro.exception.NotAuthorizedEvent;
import com.electro.exception.StreakNullException;
import com.electro.exception.UserNotFoundException;
import com.electro.models.Comment;
import com.electro.models.Streak;
import com.electro.models.User;
import com.electro.repository.CommentRepository;
import com.electro.repository.StreakRepository;
import com.electro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Erdem Akyıldız on 4.12.2018.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private StreakService streakService;

    @Autowired
    private UserService userService;

    public void createComment(User user, CommentRequestDTO requestDTO) throws StreakNullException, UserNotFoundException {
        final Optional<Streak> streak = streakService.findById(requestDTO.getId());
        if (streak.isPresent()) {
            Comment comment = new Comment();
            comment.setComment(requestDTO.getComment());
            comment.setStreak(streak.get());
            comment.setCreateDate(new Date());
            comment.setCreatedUser(user);
            comment.setActive(true);


            final Optional<Comment> parentComment = commentRepository.findById(requestDTO.getParentCommentId());
            parentComment.ifPresent(comment::setParentComment);

            commentRepository.save(comment);
        } else
            throw new StreakNullException("streak bulunamadı");

    }

    public void removeComment(User user, long id) throws CommentNotFoundException, NotAuthorizedEvent {
        Optional<Comment> optional = commentRepository.findById(id);

        if (optional.isPresent()) {
            Comment comment = optional.get();

            if (user != comment.getCreatedUser())
                throw new NotAuthorizedEvent("bu işlemi yapmaya yetkiniz yok");

            comment.setActive(false);

            commentRepository.save(comment);
        } else
            throw new CommentNotFoundException("yorum bulunamadı");

    }

    public void upComment(User user, long id) throws CommentNotFoundException {
        Optional<Comment> optional = commentRepository.findById(id);
        if (optional.isPresent()) {
            Comment comment = optional.get();

            if (!user.getUpComment().contains(comment)) {
                comment.setUp(comment.getUp() + 1);

                user.getUpComment().add(comment);
            } else {
                comment.setUp((comment.getUp() > 0 ? comment.getUp() : 1) - 1);

                user.getUpComment().remove(comment);
            }

            userService.save(user);
            commentRepository.save(comment);
        } else
            throw new CommentNotFoundException("yorum bulunamadı");

    }

    public void downComment(User user, long id) throws CommentNotFoundException {
        Optional<Comment> optional = commentRepository.findById(id);
        if (optional.isPresent()) {
            Comment comment = optional.get();

            if (!user.getDownComment().contains(comment)) {
                comment.setDown(comment.getDown() + 1);

                user.getDownComment().add(comment);
            } else {
                comment.setDown((comment.getDown() > 0 ? comment.getDown() : 1) - 1);

                user.getDownComment().remove(comment);
            }

            userService.save(user);
            commentRepository.save(comment);
        } else
            throw new CommentNotFoundException("yorum bulunamadı");

    }

}
