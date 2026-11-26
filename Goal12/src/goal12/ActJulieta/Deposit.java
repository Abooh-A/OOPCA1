/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.ActJulieta;

/**
 *
 * @author Julieta Marina Sorrentino
 */
public class Deposit extends Recycle implements SavingInterface{
    
    //data members
    
    // the amount of DRS per bottle/can 
    private final double LESS_HALF_LITRE = 0.15; 
    private final double OVER_HALF_LITRE = 0.25; //up to 3 litres
    
    private int qLess, qOver;
    private double depositLess, depositOver, savingsDRS;
    
    //CONSTRUCTORS
    public Deposit() {
    }

    public Deposit(int qLess, int qOver) {
        this.qLess = qLess;
        this.qOver = qOver;
        //add the super? 
    }
    
    //SETTERS


    //GETTERS

    public double getSavingsDRS() {
        return savingsDRS;
    }

    public double getDepositLess() {
        return depositLess;
    }

    public double getDepositOver() {
        return depositOver;
    }
    
    
    //from Interface
    @Override
    public void calculateSavings(){
        depositLess = Math.floor((qLess * LESS_HALF_LITRE) * 100) / 100;
        depositOver = Math.floor((qOver * OVER_HALF_LITRE) * 100) / 100;
        savingsDRS = depositLess + depositOver;
    }
    
    
}
