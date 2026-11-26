/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.LearnAbooh;

/**
 *
 * @author Obaapanin Abooh Agyekum <your.name at your.org>
 */
public class TrueFalseQuestion extends Question {

    private boolean correctAnswer;

    public TrueFalseQuestion(String text, int points, boolean correctAnswer) {
        super(text, points);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String[] getOptions() {
        return new String[]{"True", "False"};
    }

    @Override
    public boolean checkAnswer(int selectedAnswer) {
        if (selectedAnswer == 0) {
            return true == correctAnswer;
        } else {
            return false == correctAnswer;
        }
    }
}
