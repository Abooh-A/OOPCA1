/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.TrackEesha;

/**
 *
 * @author Eesha Penkey <your.name at your.org>
 */
public class HouseholdRecord {

    private int householdSize;
    private String category;
    private double totalKg;
    private double wasteKg;
    private double circularityPercent;

    public HouseholdRecord(int householdSize, String category, double totalKg,
            double wasteKg, double circularityPercent) {
        this.householdSize = householdSize < 1 ? 1 : householdSize;
        this.category = category;
        this.totalKg = totalKg;
        this.wasteKg = wasteKg;
        this.circularityPercent = circularityPercent;
    }

    public int getHouseholdSize() { return householdSize; }
    public String getCategory() { return category; }
    public double getTotalKg() { return totalKg; }
    public double getWasteKg() { return wasteKg; }
    public double getCircularityPercent() { return circularityPercent; }

    public double getPerCapitaKg() { return totalKg / householdSize; }
    public double getPerCapitaWasteKg() { return wasteKg / householdSize; }

    /** size|category|totalKg|wasteKg|circularityPercent */
    public String toFileString() {
        return householdSize + "|" + category + "|" + totalKg + "|" + wasteKg + "|" + circularityPercent;
    }
}

