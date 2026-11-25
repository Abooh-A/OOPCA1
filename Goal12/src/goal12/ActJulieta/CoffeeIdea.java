/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.ActJulieta;

/**
 *
 * @author macbook
 */
public abstract class CoffeeIdea extends Idea{
    protected int timesPerWeek;
    protected double avgPrice;
    protected double totalSavings;
    protected double spentWeekly;

    // CONSTRUCTORS
    protected CoffeeIdea() {
    }

    protected CoffeeIdea(int timesPerWeek, double avgPrice, String activity, String description, String placeType) {
        super(activity, description, placeType);
        this.timesPerWeek = timesPerWeek;
        this.avgPrice = avgPrice;
    }

    protected CoffeeIdea(int timesPerWeek, double avgPrice) {
        this.timesPerWeek = timesPerWeek;
        this.avgPrice = avgPrice;
    }
    
    
    // SETTERS

    public void setTimesPerWeek(int timesPerWeek) {
        this.timesPerWeek = timesPerWeek;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }
    
    // GETTERS

    public int getTimesPerWeek() {
        return timesPerWeek;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public double getTotalSavings() {
        return totalSavings;
    }
    
    
    
    
    //Others
    protected void calculateWeekly(){
        spentWeekly = timesPerWeek * avgPrice;
    }
    

    //Abstract
    public abstract String[] displayDetails();
    
}
