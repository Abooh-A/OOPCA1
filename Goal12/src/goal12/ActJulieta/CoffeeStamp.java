/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.ActJulieta;

/**
 *
 * @author Julieta Marina Sorrentino
 */
public class CoffeeStamp extends CoffeeIdea implements SavingInterface{
    //data members
    private final int STAMPS_XFREE_COFFEE = 9; 
    private final int STAMPS_WITH_CUP = 2;
    private double freeCoffeeNoCup, freeCoffeeWithCup;
    private int totalStampsPerWeek, coffeesFree, stampsLeftover;
    
    //CONSTRUCTORS
    public CoffeeStamp(int timesPerWeek, double avgPrice) {
        super(timesPerWeek, avgPrice);
    }
    
    
    //Others
    
    //from Interface
    @Override
    public void calculateSavings(){
        calculateWeekly(); // use it later maybe
        
        freeCoffeeNoCup = STAMPS_XFREE_COFFEE * avgPrice;
        freeCoffeeWithCup = (STAMPS_XFREE_COFFEE / STAMPS_WITH_CUP) * avgPrice;
        
        totalStampsPerWeek = timesPerWeek * STAMPS_WITH_CUP;
        coffeesFree = totalStampsPerWeek / STAMPS_XFREE_COFFEE;
        stampsLeftover = totalStampsPerWeek % STAMPS_XFREE_COFFEE;
        
        totalSavings = freeCoffeeNoCup - freeCoffeeWithCup;
    
    }
    
    //from parent Class -- change to an array
    @Override
    public String[] displayDetails(){
        String msg[] = new String [4];
    
        msg[0] = "Having the app, with " + STAMPS_XFREE_COFFEE + " you get a coffee for free";
        msg[1] = "If you bring your own cup, you get per coffee " + STAMPS_WITH_CUP;
        msg[2] = "That means " + totalStampsPerWeek + " per week";
        msg[3] = "You could get " + coffeesFree + " per week, with " + stampsLeftover + " stamps leftover for the following week";
        
        return msg;
    
    } 
    
    
}
