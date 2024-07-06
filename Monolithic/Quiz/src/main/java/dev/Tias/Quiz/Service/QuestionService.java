package dev.Tias.Quiz.Service;

import dev.Tias.Quiz.Model.Question;
import dev.Tias.Quiz.Repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionRepo questionRepo;
    public ResponseEntity<List<Question>> getAllQuestions() {
       try{
           return new  ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
       }catch (Exception e) {
           e.printStackTrace();
       }
         return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionRepo.findByCategory(category), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionRepo.save(question);
            return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to add question",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
