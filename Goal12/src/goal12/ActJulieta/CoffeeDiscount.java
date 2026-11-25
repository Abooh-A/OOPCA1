/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.ActJulieta;

/**
 *
 * @author Julieta Marina Sorrentino
 */


public class CoffeeDiscount extends CoffeeIdea implements SavingInterface{
    //data members
    private final double DISCOUNT_WCUP = 0.60;
    private double totalWithDiscount, percentLess;
    
    //CONSTRUCTORS

    public CoffeeDiscount() {
    }

    public CoffeeDiscount(int timesPerWeek, double avgPrice) {
        super(timesPerWeek, avgPrice);
    }

    //SETTERS

    
    //GETTERS - do I need them?
    
    public double getTotalWithDiscount() {
        return totalWithDiscount;
    }

    public double getPercentLess() {
        return percentLess;
    }
    
    
    
    //Others:
    
    //from Interface: 
    @Override
    public void calculateSavings(){
        calculateWeekly();
        
        totalSavings = timesPerWeek * DISCOUNT_WCUP;
        totalWithDiscount = spentWeekly - totalSavings;
        
        percentLess = Math.round((totalSavings * 100) / spentWeekly);
    }
    
    //from parent Class
    @Override
    public String[] displayDetails(){
        String msg[] = new String [4];
    
        msg[0] = "There is a €" + String.format("%.2f", DISCOUNT_WCUP) + " when you bring your own cup";
        msg[1] = "You spend €" + String.format("%.2f", spentWeekly) + " weekly on coffee.";
        msg[2] = "Bringing your own cup, you would save €" + String.format("%.2f", totalSavings) +" approx per week.";
        msg[3] = "That's a " + percentLess + "% less";
        
        return msg;
    }
    
}
