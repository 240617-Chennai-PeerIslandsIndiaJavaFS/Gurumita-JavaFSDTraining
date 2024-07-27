package com.Quiz.Quiz.Controller;

import com.Quiz.Quiz.Models.Answer;
import com.Quiz.Quiz.Services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("/validate")
    public boolean validateAnswer(@RequestParam Long questionId, @RequestParam Long answerId) {
        return answerService.validateAnswer(questionId, answerId);
    }

    @GetMapping("/byQuestion/{questionId}")
    public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerService.getAnswersByQuestionId(questionId);
    }
}
