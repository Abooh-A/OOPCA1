/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.ActJulieta;

/**
 *
 * @author macbook
 */
public class User {
    private String name;
    private String eircode;
    private String district;
    private Recycle listRecycle;

    // CONSTRUCTORS
    public User() {
    }

    public User(String name, String eircode) {
        this.name = name;
        this.eircode = eircode;
    }

    public User(String name) {
        this.name = name;
    }
    
    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setEircode(String eircode) {
        this.eircode = eircode;
    }
    
    // GETTERS

    public String getName() {
        return name;
    }

    public String getEircode() {
        return eircode;
    }

    public String getDistrict() {
        return district;
    }

    public Recycle getListRecycle() {
        return listRecycle;
    }
    
    // Others:
    
    
}
