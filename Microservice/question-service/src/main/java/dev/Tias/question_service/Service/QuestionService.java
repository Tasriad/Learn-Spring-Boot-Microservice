package dev.Tias.question_service.Service;
import dev.Tias.question_service.Model.Question;
import dev.Tias.question_service.Model.QuestionWrapper;
import dev.Tias.question_service.Model.Response;
import dev.Tias.question_service.Repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, int numQ) {
        List<Integer> questions = questionRepo.findRandomQuestionsByCategory(category, numQ);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionFromIds(List<Integer> ids) {
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Integer id: ids)
        {
            Question q = questionRepo.findById(id).get();
            QuestionWrapper questionWrapper = new QuestionWrapper(q);
            questionsForUser.add(questionWrapper);
        }
        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for(Response r: responses)
        {
            Question q = questionRepo.findById(r.getId()).get();
            if(q.getRightAnswer().equals(r.getResponse()))
            {
                right++;
            }
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
