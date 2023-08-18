package com.quizApp.quizapp.service;

import com.quizApp.quizapp.dao.QuestionDao;
import com.quizApp.quizapp.dao.QuizDao;
import com.quizApp.quizapp.model.Question;
import com.quizApp.quizapp.model.QuestionWrapper;
import com.quizApp.quizapp.model.Quiz;
import com.quizApp.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String lang, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByLang(lang, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q: questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQues(), q.getOption1(), q.getOption2(), q.getOption3(),q.getOption4());
            questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;

        for(Response response: responses){
            if(response.getResponse().equals(questions.get(i).getAns()))
                right++;
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
