/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.ActJulieta;

/**
 *
 * @author macbook
 */
public abstract class CoffeeIdea extends Idea {
    protected int timesPerWeek;
    protected double avgPrice;

    // CONSTRUCTORS
    protected CoffeeIdea() {
    }


    
    // SETTERS

    public void setTimesPerWeek(int timesPerWeek) {
        this.timesPerWeek = timesPerWeek;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }
    
    
    
}
