/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.TrackEesha;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Eesha Penkey <your.name at your.org>
 */
public class TrackManager {
    private static final String ENTRY_FILE = "track_entries.txt";

    // one shared manager so the data survives moving between Track screens
    private static TrackManager shared;

    private ArrayList<LogEntry> entries;
    private int nextId;

    public TrackManager() {
        entries = new ArrayList<>();
        nextId = 1;
    }

    /** The manager used by all Track screens (loads the file the first time). */
    public static TrackManager getShared() {
        if (shared == null) {
            shared = new TrackManager();
            shared.loadFromFile();
        }
        return shared;
    }

    public int getNextId() { return nextId; }

    public boolean addEntry(LogEntry entry) {
        if (entry == null) {
            return false;
        }
        entries.add(entry);
        if (entry.getId() >= nextId) {
            nextId = entry.getId() + 1;
        }
        return true;
    }

    public ArrayList<LogEntry> getEntries() { return entries; }

    public int getEntryCount() { return entries.size(); }

    //Case-insensitive, partial matches allowed (e.g. "clo" finds clothing). 
    public ArrayList<LogEntry> searchByCategory(String category) {
        ArrayList<LogEntry> found = new ArrayList<>();
        String wanted = category.trim().toLowerCase();
        for (LogEntry entry : entries) {
            if (entry.getCategory().toLowerCase().contains(wanted)) {
                found.add(entry);
            }
        }
        return found;
    }

    /** Both dates are included in the range. */
    public ArrayList<LogEntry> searchByDateRange(LocalDate from, LocalDate to) {
        ArrayList<LogEntry> found = new ArrayList<>();
        for (LogEntry entry : entries) {
            if (!entry.getDate().isBefore(from) && !entry.getDate().isAfter(to)) {
                found.add(entry);
            }
        }
        return found;
    }

    public boolean deleteEntry(int id) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getId() == id) {
                entries.remove(i);
                return true;
            }
        }
        return false;
    }

    // ---------- totals ----------

    public double getTotalFootprintKg() { return getTotalFootprintKg(TrackData.ALL_CATEGORIES); }

    public double getTotalFootprintKg(String category) {
        double total = 0;
        for (LogEntry entry : entries) {
            if (inCategory(entry, category)) {
                total += entry.getFootprintKg();   // polymorphic call
            }
        }
        return total;
    }

    public double getTotalWasteKg() { return getTotalWasteKg(TrackData.ALL_CATEGORIES); }

    public double getTotalWasteKg(String category) {
        double total = 0;
        for (LogEntry entry : entries) {
            if (inCategory(entry, category)) {
                total += entry.getWasteKg();       // polymorphic call
            }
        }
        return total;
    }

    /** Share of acquired items (not discards) that were second-hand or repaired. */
    public double getCircularityPercent() { return getCircularityPercent(TrackData.ALL_CATEGORIES); }

    public double getCircularityPercent(String category) {
        int acquired = 0;
        int circular = 0;
        for (LogEntry entry : entries) {
            if (inCategory(entry, category) && entry.isAcquisition()) {
                acquired++;
                if (entry.isCircular()) {
                    circular++;
                }
            }
        }
        return acquired == 0 ? 0.0 : circular * 100.0 / acquired;
    }

    private boolean inCategory(LogEntry entry, String category) {
        return category == null
                || TrackData.ALL_CATEGORIES.equalsIgnoreCase(category)
                || entry.getCategory().equalsIgnoreCase(category);
    }

    // ---------- file handling ----------

    /** Writes every entry to track_entries.txt. Returns false if the write failed. */
    public boolean saveToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(ENTRY_FILE))) {
            for (LogEntry entry : entries) {
                out.println(entry.toFileString());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Could not save entries: " + e.getMessage());
            return false;
        }
    }

    /** Reads track_entries.txt (if it exists). Lines that cannot be read are skipped. */
    public void loadFromFile() {
        entries.clear();
        nextId = 1;
        try (BufferedReader in = new BufferedReader(new FileReader(ENTRY_FILE))) {
            String line;
            while ((line = in.readLine()) != null) {
                LogEntry entry = parseEntry(line);
                if (entry != null) {
                    addEntry(entry);
                }
            }
        } catch (IOException e) {
            // no file yet on the first run - start with an empty list
        }
    }

    /** Turns one file line back into the right kind of entry (or null if it is invalid). */
    private LogEntry parseEntry(String line) {
        try {
            String[] p = line.split("\\|", -1);
            int id = Integer.parseInt(p[1]);
            double weight = Double.parseDouble(p[4]);
            LocalDate date = LocalDate.parse(p[5]);
            switch (p[0]) {
                case "NEW":
                    return new NewPurchaseEntry(id, p[2], p[3], weight, date, Boolean.parseBoolean(p[6]));
                case "SECONDHAND":
                    return new SecondHandEntry(id, p[2], p[3], weight, date, Boolean.parseBoolean(p[6]));
                case "DISCARDED":
                    return new DiscardedEntry(id, p[2], p[3], weight, date, p[6]);
                default:
                    return null;
            }
        } catch (RuntimeException e) {   // bad number, bad date or missing field
            return null;
        }
    }
}
