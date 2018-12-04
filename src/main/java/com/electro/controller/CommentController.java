package com.electro.controller;

import com.electro.dto.comment.CommentRequestDTO;
import com.electro.exception.CommentNotFoundException;
import com.electro.exception.NotAuthorizedEvent;
import com.electro.exception.StreakNullException;
import com.electro.exception.UserNotFoundException;
import com.electro.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.electro.models.User;

/**
 * Created by Erdem Akyıldız on 4.12.2018.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public void createComment(User user, @RequestBody CommentRequestDTO requestDTO) throws StreakNullException, UserNotFoundException {
        commentService.createComment(user, requestDTO);
    }

    @PostMapping("/remove")
    @ResponseStatus(HttpStatus.OK)
    public void removeComment(User user, @RequestParam("commentId") long commentId) throws CommentNotFoundException, NotAuthorizedEvent {
        commentService.removeComment(user, commentId);
    }

    @PostMapping("/up")
    @ResponseStatus(HttpStatus.OK)
    public void upComment(User user, @RequestParam("commentId") long commentId) throws CommentNotFoundException {
        commentService.upComment(user, commentId);
    }

    @PostMapping("/down")
    @ResponseStatus(HttpStatus.OK)
    public void downComment(User user, @RequestParam("commentId") long commentId) throws CommentNotFoundException {
        commentService.downComment(user, commentId);
    }


}
