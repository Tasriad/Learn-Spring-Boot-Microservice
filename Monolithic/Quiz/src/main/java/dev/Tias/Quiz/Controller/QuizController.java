package dev.Tias.Quiz.Controller;

import dev.Tias.Quiz.Model.QuestionWrapper;
import dev.Tias.Quiz.Model.Quiz;
import dev.Tias.Quiz.Model.Response;
import dev.Tias.Quiz.Service.QuizService;
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
    public ResponseEntity<String> addQuiz(@RequestParam String category,@RequestParam int numQ,@RequestParam String title) {
        return quizService.addQuiz(category,numQ,title);
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
