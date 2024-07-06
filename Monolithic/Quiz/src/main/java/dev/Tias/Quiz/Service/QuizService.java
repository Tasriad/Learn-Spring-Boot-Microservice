package dev.Tias.Quiz.Service;

import dev.Tias.Quiz.Model.Question;
import dev.Tias.Quiz.Model.QuestionWrapper;
import dev.Tias.Quiz.Model.Quiz;
import dev.Tias.Quiz.Model.Response;
import dev.Tias.Quiz.Repository.QuestionRepo;
import dev.Tias.Quiz.Repository.QuizRepo;
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
    QuestionRepo questionRepo;

    public ResponseEntity<String> addQuiz(String category, int numQ, String title) {
        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category, numQ);
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
        Optional<Quiz> quiz = quizRepo.findById(id);
        List<Question> questions = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q: questions)
        {
            QuestionWrapper questionWrapper = new QuestionWrapper(q);
            questionsForUser.add(questionWrapper);
        }
        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> quizResults(int id, List<Response> responses) {
        Optional<Quiz> quiz = quizRepo.findById(id);
        List<Question> questions = quiz.get().getQuestions();
        int right = 0;
        int i = 0;
        for(Response r: responses)
        {
            if(questions.get(i).getRightAnswer().equals(r.getResponse()))
            {
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
