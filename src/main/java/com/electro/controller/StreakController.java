package com.electro.controller;

import com.electro.dto.streak.StreakRequestDTO;
import com.electro.dto.streak.StreakResponseDTO;
import com.electro.exception.CategoryNotFoundException;
import com.electro.exception.StreakAuthorizeException;
import com.electro.exception.StreakNullException;
import com.electro.models.Streak;
import com.electro.models.User;
import com.electro.service.StreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/streaks/{categoryName}")
    public List<StreakResponseDTO> streaksByCategory(@RequestParam("categoryName") String categoryName) throws CategoryNotFoundException {
        return streakService.getStreaksByCategory(categoryName);
    }

    @GetMapping("/categories")
    public List<String> getCategories() {
        return streakService.getCategories();
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
    public void likeStreak(@RequestParam("streakId") long streakId, User user) throws StreakAuthorizeException, StreakNullException {
        streakService.like(streakId, user);
    }

    @PostMapping("/dissLike")
    @ResponseStatus(HttpStatus.OK)
    public void dissLikeStreak(@RequestParam("streakId") long streakId, User user) throws StreakAuthorizeException, StreakNullException {
        streakService.dissLike(streakId, user);
    }

}
