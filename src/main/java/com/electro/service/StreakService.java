package com.electro.service;

import com.electro.dto.streak.StreakRequestDTO;
import com.electro.dto.streak.StreakResponseDTO;
import com.electro.exception.CategoryNotFoundException;
import com.electro.exception.StreakAuthorizeException;
import com.electro.exception.StreakNullException;
import com.electro.models.Category;
import com.electro.models.Streak;
import com.electro.models.User;
import com.electro.repository.StreakRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StreakService {

    @Autowired
    private StreakRepository streakRepository;

    @Autowired
    private CategoryService categoryService;

    public void createStreak(StreakRequestDTO streakRequestDTO, User user) {
        final Streak streak = new Streak().fromDTO(streakRequestDTO);
        streak.setCreateDate(new Date());
        streak.setModifiedDate(null);
        streak.setCreatedUser(user);

        final Category category = categoryService.findOrCreateCategory(streakRequestDTO.getCategoryName());
        streak.setCategory(category);

        streakRepository.save(streak);
    }

    public void updateStreak(StreakRequestDTO streakRequestDTO, User user) throws StreakAuthorizeException, StreakNullException {
        final Streak streak = checkAuthority(streakRequestDTO.getId(), user);
        streak.setModifiedDate(new Date());

        if (!StringUtils.isEmpty(streakRequestDTO.getTitle()))
            streak.setTitle(streakRequestDTO.getTitle());

        if (!StringUtils.isEmpty(streakRequestDTO.getContent()))
            streak.setContent(streakRequestDTO.getContent());

        streakRepository.save(streak);
    }

    public void like(long streakId, User user) throws StreakAuthorizeException, StreakNullException {
        final Streak streak = checkAuthority(streakId, user);

        streak.setLikeCount(streak.getLikeCount() + 1);

        streakRepository.save(streak);
    }

    public void dissLike(long streakId, User user) throws StreakAuthorizeException, StreakNullException {
        final Streak streak = checkAuthority(streakId, user);

        streak.setDissLikeCount(streak.getDissLikeCount() + 1);

        streakRepository.save(streak);
    }

    public void changeStatus(long streakId, boolean status, User user) throws StreakAuthorizeException, StreakNullException {
        final Streak streak = checkAuthority(streakId, user);

        streak.setActive(status);

        streakRepository.save(streak);
    }

    private Streak getStreak(long streakId) throws StreakNullException {
        final Optional<Streak> optional = streakRepository.findById(streakId);
        final Streak streak = optional.orElse(null);

        if (streak == null || !streak.isActive())
            throw new StreakNullException(String.format("streak bulunamdı id:%s", streakId));

        return streak;
    }

    private Streak checkAuthority(long streakId, User user) throws StreakNullException, StreakAuthorizeException {
        final Streak streak = getStreak(streakId);

        if (streak.getCreatedUser() != user)
            throw new StreakAuthorizeException(String.format("yetkisiz işlem id:%s, user: %s", streakId, user.getNickName()));

        return streak;
    }

    public List<StreakResponseDTO> getStreaksDesc() {
        List<Streak> all = streakRepository.findAll(sortByIdDesc());
        return convertDTO(all);
    }

    private Sort sortByIdDesc() {
        return new Sort(Sort.Direction.DESC, "id");
    }

    private List<StreakResponseDTO> convertDTO(List<Streak> streaks) {
        final List<StreakResponseDTO> dtos = new ArrayList<>();
        streaks.forEach((Streak streak) -> {
            if (!streak.isActive())
                return;

            StreakResponseDTO responseDTO = streak.toDTO();
            responseDTO.setCategoryName(streak.getCategory().getName());
            responseDTO.setNickName(streak.getCreatedUser().getNickName());

            dtos.add(responseDTO);

        });

        return dtos;
    }

    public List<StreakResponseDTO> getStreaksByCategory(String categoryName) throws CategoryNotFoundException {
        final Category category = categoryService.findCategory(categoryName);
        List<Streak> all = streakRepository.findAllByCategoryEqualsAndActiveEquals(category, true);

        return convertDTO(all);
    }
}
