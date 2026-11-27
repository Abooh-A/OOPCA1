/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.ActJulieta;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 *
 * @author Julieta Marina Sorrentino
 */
public final class PlaceList{ //Singleton Class
    //data members
    private ArrayList<Place> placesArrayList;

    
    //CONSTRUCTORS 
    private PlaceList(){
        //a private constructor that reads places.dat into that field (reading code), with an empty list if it fails
        placesArrayList = new ArrayList<>();

        //read the file:
        try{
            FileInputStream inputPStream;
            ObjectInputStream objPStream;
            File f = new File("places.dat");
            inputPStream = new FileInputStream(f);
            objPStream = new ObjectInputStream(inputPStream);
            
            // store it in the arrayList
            placesArrayList = (ArrayList<Place>)objPStream.readObject();
            objPStream.close();
            
            System.out.println("Read");
            
        }catch(ClassNotFoundException | IOException er){
            System.out.println("Error" + er);
            placesArrayList.clear(); 
        }
        
    }
    
  
    // INSTANCE
    private static PlaceList INSTANCE = new PlaceList();

    public static PlaceList getInstance(){
        return INSTANCE;
    }

    // Other Methods
    //SEARCH: typePlace and district, typePlace
    public ArrayList<Place> searchPlaces(ArrayList<String> typePlace, String district) {
        
        ArrayList<Place> resultSearch = new ArrayList<>();
        
        for (Place p: placesArrayList){
            if(typePlace.contains(p.getPlaceType()) && p.getDistrict().equals(district)){
                resultSearch.add(p);
            }
        } // end for loop
        
        return resultSearch;
    } // searchPlaces by typePlace and district
    
    
    public ArrayList<Place> searchPlaces(ArrayList<String> typePlace) {
        
        ArrayList<Place> resultSearch = new ArrayList<>();
        
        for (Place p: placesArrayList){
            if(typePlace.contains(p.getPlaceType())){
                resultSearch.add(p);
            }
        } // end for loop
        
        return resultSearch;
    } // searchPlaces by typePlace and district

    
        
}
