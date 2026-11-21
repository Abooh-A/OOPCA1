/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.TrackEesha;

/**
 *
 * @author Eesha Penkey <your.name at your.org>
 */
public class SecondHandEntry extends LogEntry {

    private static final double SECOND_HAND_FACTOR = 0.10;
    private static final double REPAIRED_FACTOR = 0.25;

    private boolean wasRepaired;

    public SecondHandEntry(int id, String category, String itemType, double weightKg,
            LocalDate date, boolean wasRepaired) {
        super(id, category, itemType, weightKg, date);
        this.wasRepaired = wasRepaired;
    }

    public boolean wasRepaired() { return wasRepaired; }

    @Override
    public String getStatus() { return wasRepaired ? "repaired" : "second hand"; }

    @Override
    public double getFootprintKg() {
        return weightKg * (wasRepaired ? REPAIRED_FACTOR : SECOND_HAND_FACTOR);
    }

    @Override
    public boolean isCircular() { return true; }

    @Override
    public String toFileString() { return "SECONDHAND|" + baseFields() + "|" + wasRepaired; }
}
