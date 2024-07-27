package com.Quiz.Quiz.Repository;

import com.Quiz.Quiz.Models.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findTop3ByOrderByScoreDesc();
}

