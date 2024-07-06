package dev.Tias.quiz_service.Service;
import dev.Tias.quiz_service.Feign.QuizInterface;
import dev.Tias.quiz_service.Model.QuestionWrapper;
import dev.Tias.quiz_service.Model.Quiz;
import dev.Tias.quiz_service.Model.Response;
import dev.Tias.quiz_service.Repository.QuizRepo;
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
    QuizRepo quizRepo;
    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> addQuiz(String category, int numQ, String title) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        try{
            quizRepo.save(quiz);
            return new ResponseEntity<>("Quiz added successfully", HttpStatus.CREATED);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to add quiz",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Quiz quiz = quizRepo.findById(id).get();
        List<Integer> questions = quiz.getQuestions();
        ResponseEntity<List<QuestionWrapper>> questionsForUser = quizInterface.getQuestionFromIds(questions);
        return questionsForUser;
    }

    public ResponseEntity<Integer> quizResults(int id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
