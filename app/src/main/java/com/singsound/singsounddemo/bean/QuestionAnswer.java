package com.singsound.singsounddemo.bean;

import java.util.ArrayList;

/**
 * Created by wang on 2016/9/5.
 */
public class QuestionAnswer {

    String question;
    String[] keyWords;

    ArrayList<Answer> answers;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String[] keyWords) {
        this.keyWords = keyWords;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public static class Answer {

        String answer;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }


}
