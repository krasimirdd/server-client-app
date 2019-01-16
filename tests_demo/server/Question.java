package testove_kontrolno2.server;

import java.util.List;

public class Question {
    private String text;
    private List<String> answers;
    private Integer correctAnswer;

    public Question() {
    }

    public Question(String text, List<String> answers, Integer correctAnswer) {
        this.text = text;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return getText() + System.lineSeparator() + "A:" + answers.toString() + System.lineSeparator();
    }
}
