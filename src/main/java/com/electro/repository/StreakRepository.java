package com.electro.repository;

import com.electro.models.Headline;
import com.electro.models.Streak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreakRepository extends JpaRepository<Streak, Long> {

    List<Streak> findAllByHeadlineEqualsAndActiveEquals(Headline headline, boolean active);

}
