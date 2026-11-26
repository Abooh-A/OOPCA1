/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.LearnAbooh;

/**
 *
 * @author Obaapanin Abooh Agyekum <your.name at your.org>
 */
public abstract class Question {

    protected String text;
    protected int points;

    protected Question(String text, int points) {
        this.text = text;
        this.points = points;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPoints() {
        return points;
    }

    public abstract String[] getOptions();

    public abstract boolean checkAnswer(String answer);

}
