package dev.Tias.quiz_service.Controller;
import dev.Tias.quiz_service.Model.QuestionWrapper;
import dev.Tias.quiz_service.Model.QuizDTO;
import dev.Tias.quiz_service.Model.Response;
import dev.Tias.quiz_service.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    QuizService quizService;
    @PostMapping("/add")
    public ResponseEntity<String> addQuiz(@RequestBody QuizDTO quizDTO) {
        return quizService.addQuiz(quizDTO.getCategory(),quizDTO.getNumQuestions(),quizDTO.getTitle());
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id) {
        return quizService.getQuizQuestions(id);
    }
    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<Response> responses) {
        return quizService.quizResults(id,responses);
    }
}
