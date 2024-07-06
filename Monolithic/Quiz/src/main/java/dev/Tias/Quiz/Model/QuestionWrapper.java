package dev.Tias.Quiz.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionWrapper {
    private Integer id;
    private String questionDescription;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public QuestionWrapper(Question question) {
        this.id = question.getId();
        this.questionDescription = question.getQuestionDescription();
        this.option1 = question.getOption1();
        this.option2 = question.getOption2();
        this.option3 = question.getOption3();
        this.option4 = question.getOption4();
    }
}
