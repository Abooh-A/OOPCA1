/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.ActJulieta;

/**
 *
 * @author macbook
 */
public class Recycle extends EcoAction{
    private String wasteType;
    private Place place;
    private String district;

    //CONSTRUCTORS
    
    public Recycle() {
    }

    public Recycle(String wasteType, String district, String placeType) {
        super(placeType);
        this.wasteType = wasteType;
        this.district = district;
    }

    public Recycle(String wasteType, String placeType) {
        super(placeType);
        this.wasteType = wasteType;
    }
    
    
    // SETTERS
    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
    
    // GETTERS

    public String getWasteType() {
        return wasteType;
    }

    public Place getPlace() {
        return place;
    }
    
    
}
