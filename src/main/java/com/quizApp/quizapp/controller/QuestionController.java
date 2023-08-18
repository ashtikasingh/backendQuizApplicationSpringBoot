package com.quizApp.quizapp.controller;


import com.quizApp.quizapp.model.Question;
import com.quizApp.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("lang/{lang}")
    public ResponseEntity<List<Question>> getQuestionByLang(@PathVariable String lang){
        return questionService.getQuestionByLang(lang);

    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

}
