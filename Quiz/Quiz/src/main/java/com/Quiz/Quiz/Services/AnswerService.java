package com.Quiz.Quiz.Services;

import com.Quiz.Quiz.Models.Answer;
import com.Quiz.Quiz.Models.Question;
import com.Quiz.Quiz.Repository.AnswerRepository;
import com.Quiz.Quiz.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public boolean validateAnswer(Long questionId, Long answerId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (!question.isPresent()) {
            return false;
        }

        Optional<Answer> answer = answerRepository.findById(answerId);
        return answer.isPresent() && answer.get().isCorrect();
    }

    public List<Answer> getAnswersByQuestionId(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        return question.map(Question::getAnswers).orElseGet(ArrayList::new);
    }
}
