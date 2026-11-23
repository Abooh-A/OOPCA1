/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.ActJulieta;

/**
 *
 * @author macbook
 */
public class Idea extends EcoAction{
    // data members
    private String activity, description;
    
    // CONSTRUCTORS
    public Idea() {
    }

    public Idea(String activity, String description, String placeType) {
        super(placeType);
        this.activity = activity;
        this.description = description;
    }
    
    // GETTERS

    public String getActivity() {
        return activity;
    }

    public String getDescription() {
        return description;
    }

    public String getPlaceType() {
        return placeType;
    }
    
    
    // SETTERS 

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }
    
}
