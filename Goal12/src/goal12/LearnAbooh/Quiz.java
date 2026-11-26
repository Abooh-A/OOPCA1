/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.LearnAbooh;

import java.util.ArrayList;

/**
 *
 * @author Obaapanin Abooh Agyekum <your.name at your.org>
 */
public class Quiz {

    private ArrayList<Question> questionList;

    public Quiz() {
        questionList = new ArrayList<Question>();
    }

    public void addQuestion(Question question) {
        questionList.add(question);
    }

    public Question getQuestion(int index) {
        return questionList.get(index);
    }

    public int getQuestionCount() {
        return questionList.size();
    }

    public int getTotalPoints() {
        int totalPts = 0;
        for (Question question : questionList) {
            totalPts += question.getPoints();
        }
        return totalPts;
    }

}
