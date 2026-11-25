/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.ActJulieta;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import java.io.FileReader;
import java.io.BufferedReader;


/**
 *
 * @author Julieta Marina Sorrentino
 * This is a file to get a file with Place objects from a text file extracted from a excel.
 * 
 */

public class PlacesConverter {
    
    public static void main(String[] args) {
        // Convert the places.txt in a object file 
    
        ArrayList<Place> fPlaces = new ArrayList<Place>();
        Place item;
        
        File f;
  
        //Getting the information from the .txt file
        try{
            f = new File("places.txt");
            
            FileReader fr = new FileReader (f);
            BufferedReader bReader = new BufferedReader(fr);
            
            String line;
            
            while ((line = bReader.readLine()) != null){
                
                
                String[] info = line.split("\t", -1);
                //What the -1 does: it's the limit. 
                //A negative limit tells split: "don't throw away anything, keep the empty pieces at the end too"
                
                // Place(String name, String address, String placeType, String district, String info, String link) 
                item = new Place(info[0], info[1], info[2], info[3], info[4], info[5]);
                fPlaces.add(item);
            }
            
            bReader.close();
            
            System.out.println(fPlaces.size());
            JOptionPane.showMessageDialog(null, "Success reading from File: " + f);
        
        }catch(IOException er){
            JOptionPane.showMessageDialog(null, "Error reading from file " + er);
        }
        
        
        //Convert it to an object file
        
        
    
    }
    
}
