package com.quizApp.quizapp.dao;

import com.quizApp.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findByLang(String lang);

    @Query(value = "SELECT * FROM Question q WHERE q.lang = :lang ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionsByLang(String lang, int numQ);
}
