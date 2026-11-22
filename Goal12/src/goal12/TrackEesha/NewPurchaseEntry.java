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
public class NewPurchaseEntry extends LogEntry {

    private static final double SINGLE_USE_FACTOR = 1.25;

    private boolean isSingleUse;

    public NewPurchaseEntry(int id, String category, String itemType, double weightKg,
            LocalDate date, boolean isSingleUse) {
        super(id, category, itemType, weightKg, date);
        this.isSingleUse = isSingleUse;
    }

    public boolean isSingleUse() { return isSingleUse; }

    @Override
    public String getStatus() { return "new"; }

    @Override
    public double getFootprintKg() {
        return isSingleUse ? weightKg * SINGLE_USE_FACTOR : weightKg;
    }

    @Override
    public String toFileString() { return "NEW|" + baseFields() + "|" + isSingleUse; }

    @Override
    public String getSummary() {
        return super.getSummary() + (isSingleUse ? " (single-use)" : "");
    }
}
