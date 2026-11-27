/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.TrackEesha;
import java.time.LocalDate;
/**
 *
 * @author Eesha Penkey <your.name at your.org>
 */
public abstract class LogEntry {

    protected int id;
    protected String category;
    protected String itemType;
    protected double weightKg;
    protected LocalDate date;

    protected LogEntry(int id, String category, String itemType, double weightKg, LocalDate date) {
        this.id = id;
        this.category = category;
        this.itemType = itemType;
        this.weightKg = weightKg;
        this.date = date;
    }

    public int getId() { return id; } // get unique id
    public String getCategory() { return category; } // category per entry
    public String getItemType() { return itemType; } // specific item type
    public double getWeightKg() { return weightKg; } // entry weight
    public LocalDate getDate() { return date; } //associated date
    public abstract String getStatus(); // Text shown in the status column: "new", "second hand" or "discarded". 
    public abstract double getFootprintKg(); // Material footprint in kg. 
    public double getWasteKg() { return 0.0; } //only discarded items create waste
    public boolean isAcquisition() { return true; } // True if the entry is something the user acquired (used for circularity). 
    public boolean isCircular() { return false; } // True if the acquired item was second-hand or repaired. 
    public abstract String toFileString(); //One line for the data file: TYPE|id|category|itemType|weightKg|date|extra 
    
    //The fields shared by every entry, in file format. 
    protected String baseFields() { 
        return id + "|" + clean(category) + "|" + clean(itemType) + "|" + weightKg + "|" + date;
    }

    //Stops '|' in text from breaking the file format. 
    protected static String clean(String text) {
        return text.replace("|", "/");
    }
    
    //sumamary
    public String getSummary() {
        return category + " / " + itemType + " - " + getStatus()
                + String.format(", %.2f kg on %s", weightKg, date);
    }
}
