/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.ActJulieta;

/**
 *
 * @author macbook
 */
public class Place {
    private String name, address, placeType, district, info, link;
    
    // CONSTRUCTOR
    public Place(String name, String address, String placeType, String district, String info, String link) {
        this.name = name;
        this.address = address;
        this.placeType = placeType;
        this.district = district;
        this.info = info;
        this.link = link;
    }
    
    // GETTERS

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPlaceType() {
        return placeType;
    }

    public String getDistrict() {
        return district;
    }

    public String getInfo() {
        return info;
    }

    public String getLink() {
        return link;
    }
    
}
