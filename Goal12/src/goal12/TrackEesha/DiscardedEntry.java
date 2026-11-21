/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.TrackEesha;

/**
 *
 * @author Eesha Penkey <your.name at your.org>
 */
public class DiscardedEntry extends LogEntry {

    private String wasteReason;

    public DiscardedEntry(int id, String category, String itemType, double weightKg,
            LocalDate date, String wasteReason) {
        super(id, category, itemType, weightKg, date);
        this.wasteReason = wasteReason;
    }

    public String getWasteReason() { return wasteReason; }

    @Override
    public String getStatus() { return "discarded"; }

    @Override
    public double getFootprintKg() { return 0.0; }

    @Override
    public double getWasteKg() { return weightKg; }

    @Override
    public boolean isAcquisition() { return false; }

    @Override
    public String toFileString() { return "DISCARDED|" + baseFields() + "|" + clean(wasteReason); }

    @Override
    public String getSummary() { return super.getSummary() + " (" + wasteReason + ")"; }
}