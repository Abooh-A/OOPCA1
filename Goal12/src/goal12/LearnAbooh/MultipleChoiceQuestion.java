/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.LearnAbooh;

/**
 *
 * @author Obaapanin Abooh Agyekum <your.name at your.org>
 */
public class MultipleChoiceQuestion extends Question {

    private String[] options;
    private int correctIndex;

    public MultipleChoiceQuestion(String text, int points, String[] options, int correctIndex) {
        super(text, points);
        this.options = options;
        this.correctIndex = correctIndex;
    }

    @Override
    public String[] getOptions() {
        return options;
    }

    @Override
    public boolean checkAnswer(int selectedAnswer) {
        return correctIndex == selectedAnswer;
    }

}
