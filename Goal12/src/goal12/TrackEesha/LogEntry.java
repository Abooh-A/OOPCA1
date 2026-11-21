/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.TrackEesha;

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

    public int getId() { return id; }
    public String getCategory() { return category; }
    public String getItemType() { return itemType; }
    public double getWeightKg() { return weightKg; }
    public LocalDate getDate() { return date; }

    /** Text shown in the status column: "new", "second hand" or "discarded". */
    public abstract String getStatus();

    /** Material footprint in kg. */
    public abstract double getFootprintKg();

    /** Waste in kg (only discarded items create waste). */
    public double getWasteKg() { return 0.0; }

    /** True if the entry is something the user acquired (used for circularity). */
    public boolean isAcquisition() { return true; }

    /** True if the acquired item was second-hand or repaired. */
    public boolean isCircular() { return false; }

    /** One line for the data file: TYPE|id|category|itemType|weightKg|date|extra */
    public abstract String toFileString();

    /** The fields shared by every entry, in file format. */
    protected String baseFields() {
        return id + "|" + clean(category) + "|" + clean(itemType) + "|" + weightKg + "|" + date;
    }

    /** Stops '|' in text from breaking the file format. */
    protected static String clean(String text) {
        return text.replace("|", "/");
    }

    public String getSummary() {
        return category + " / " + itemType + " - " + getStatus()
                + String.format(", %.2f kg on %s", weightKg, date);
    }
}
