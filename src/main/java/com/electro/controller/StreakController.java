package com.electro.controller;

import com.electro.dto.streak.StreakRequestDTO;
import com.electro.dto.streak.StreakResponseDTO;
import com.electro.exception.HeadlineNotFoundException;
import com.electro.exception.StreakAuthorizeException;
import com.electro.exception.StreakNullException;
import com.electro.models.User;
import com.electro.service.StreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/streak")
public class StreakController {

    @Autowired
    private StreakService streakService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStreak(@RequestBody StreakRequestDTO streakRequestDTO, User user) {
        streakService.createStreak(streakRequestDTO, user);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateStreak(@RequestBody StreakRequestDTO streakRequestDTO, User user) throws StreakAuthorizeException, StreakNullException {
        streakService.updateStreak(streakRequestDTO, user);
    }

    @GetMapping("/streaks")
    public List<StreakResponseDTO> streaksDesc() {
        return streakService.getStreaksDesc();
    }

    @GetMapping("/streaks/{headlineName}")
    public List<StreakResponseDTO> streaksByHeadline(@RequestParam("headlineName") String headlineName) throws HeadlineNotFoundException {
        return streakService.getStreaksByHeadline(headlineName);
    }

    @GetMapping("/headlines")
    public List<String> getHeadlines() {
        return streakService.getHadlines();
    }

    @PostMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public void activeStreak(@RequestParam("streakId") long streakId, User user) throws StreakAuthorizeException, StreakNullException {
        streakService.changeStatus(streakId, true, user);
    }

    @PostMapping("/passive")
    @ResponseStatus(HttpStatus.OK)
    public void passiveStreak(@RequestParam("streakId") long streakId, User user) throws StreakAuthorizeException, StreakNullException {
        streakService.changeStatus(streakId, false, user);
    }

    @PostMapping("/like")
    @ResponseStatus(HttpStatus.OK)
    public void likeStreak(@RequestParam("streakId") long streakId, User user) throws StreakNullException {
        streakService.like(streakId, user);
    }

    @PostMapping("/dissLike")
    @ResponseStatus(HttpStatus.OK)
    public void dissLikeStreak(@RequestParam("streakId") long streakId, User user) throws StreakNullException {
        streakService.dissLike(streakId, user);
    }

}
