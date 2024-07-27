package com.Quiz.Quiz.Controller;

import com.Quiz.Quiz.Models.Score;
import com.Quiz.Quiz.Services.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/topThree")
    public List<Score> getTopThreeScores() {
        return scoreService.getTopThreeScores();
    }

    @PostMapping("/save")
    public Score saveScore(@RequestBody Score score) {
        return scoreService.saveScore(score);
    }
}
