package com.Quiz.Quiz.Services;

import com.Quiz.Quiz.Models.Score;
import com.Quiz.Quiz.Repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    public List<Score> getTopThreeScores() {
        return scoreRepository.findTop3ByOrderByScoreDesc();
    }

    public Score saveScore(Score score) {
        if (score == null) {
            throw new IllegalArgumentException("Score cannot be null");
        }
        return scoreRepository.save(score);
    }


}
