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
import java.util.ArrayList;

/**
 *
 * @author Eesha Penkey <your.name at your.org>
 */
public class CommunityBenchmark {
    private static final String COMMUNITY_FILE = "community_benchmark.txt";
    public static final String ALL_CATEGORIES = "all categories";

    private ArrayList<HouseholdRecord> records;

    public CommunityBenchmark() {
        records = new ArrayList<>();
        loadCommunity();
    }

    public void addRecord(HouseholdRecord record) { records.add(record); }

    public int getRecordCount() { return records.size(); }

    /** Reads the file; if it is missing, creates it with sample data first. */
    public void loadCommunity() {
        records.clear();
        try (BufferedReader in = new BufferedReader(new FileReader(COMMUNITY_FILE))) {
            String line;
            while ((line = in.readLine()) != null) {
                try {
                    String[] p = line.split("\\|", -1);
                    records.add(new HouseholdRecord(Integer.parseInt(p[0]), p[1],
                            Double.parseDouble(p[2]), Double.parseDouble(p[3]),
                            Double.parseDouble(p[4])));
                } catch (RuntimeException e) {
                    // skip a bad line
                }
            }
        } catch (IOException e) {
            createDefaultData();
            saveCommunity();
        }
    }

    public void saveCommunity() {
        try (PrintWriter out = new PrintWriter(new FileWriter(COMMUNITY_FILE))) {
            for (HouseholdRecord record : records) {
                out.println(record.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Could not save community data: " + e.getMessage());
        }
    }

    /** Sample data: households of 1-5 people for every category. */
    private void createDefaultData() {
        String[] categories = TrackData.BENCHMARK_CATEGORIES;
        double[] perPerson = {40, 12, 8, 15, 6, 4, 5, 90};          // kg per person
        double[] wasteShare = {0.25, 0.05, 0.15, 0.10, 0.30, 0.20, 0.10, 0.18};
        double[] circularity = {5, 15, 25, 30, 10, 5, 35, 16};      // percent
        for (int c = 0; c < categories.length; c++) {
            for (int size = 1; size <= 5; size++) {
                double each = perPerson[c] * (1 - 0.06 * (size - 1));   // bigger households share more
                double total = Math.round(each * size * 10) / 10.0;
                double waste = Math.round(total * wasteShare[c] * 10) / 10.0;
                records.add(new HouseholdRecord(size, categories[c], total, waste, circularity[c]));
            }
        }
    }

    /** Records for this category and household size; falls back to any size if none match. */
    private ArrayList<HouseholdRecord> matching(int householdSize, String category) {
        ArrayList<HouseholdRecord> sameSize = new ArrayList<>();
        ArrayList<HouseholdRecord> anySize = new ArrayList<>();
        for (HouseholdRecord record : records) {
            if (record.getCategory().equalsIgnoreCase(category)) {
                anySize.add(record);
                if (record.getHouseholdSize() == householdSize) {
                    sameSize.add(record);
                }
            }
        }
        return sameSize.isEmpty() ? anySize : sameSize;
    }

    public double getAveragePerCapita(int householdSize, String category) {
        ArrayList<HouseholdRecord> list = matching(householdSize, category);
        double sum = 0;
        for (HouseholdRecord record : list) {
            sum += record.getPerCapitaKg();
        }
        return list.isEmpty() ? 0 : sum / list.size();
    }

    public double getAverageWastePerCapita(int householdSize, String category) {
        ArrayList<HouseholdRecord> list = matching(householdSize, category);
        double sum = 0;
        for (HouseholdRecord record : list) {
            sum += record.getPerCapitaWasteKg();
        }
        return list.isEmpty() ? 0 : sum / list.size();
    }

    public double getAverageCircularity(int householdSize, String category) {
        ArrayList<HouseholdRecord> list = matching(householdSize, category);
        double sum = 0;
        for (HouseholdRecord record : list) {
            sum += record.getCircularityPercent();
        }
        return list.isEmpty() ? 0 : sum / list.size();
    }

    /** Builds the text shown on the benchmark screen. */
    public String compare(HouseholdRecord mine) {
        int size = mine.getHouseholdSize();
        String category = mine.getCategory();
        if (matching(size, category).isEmpty()) {
            return "No community data found for \"" + category + "\".";
        }
        double commKg = getAveragePerCapita(size, category);
        double commWaste = getAverageWastePerCapita(size, category);
        double commCirc = getAverageCircularity(size, category);

        StringBuilder sb = new StringBuilder();
        sb.append("Household size: ").append(size).append("   Category: ").append(category).append("\n\n");
        sb.append(String.format("%-28s %10s %12s%n", "", "You", "Community"));
        sb.append(String.format("%-28s %10.1f %12.1f%n", "Footprint per person (kg)", mine.getPerCapitaKg(), commKg));
        sb.append(String.format("%-28s %10.1f %12.1f%n", "Waste per person (kg)", mine.getPerCapitaWasteKg(), commWaste));
        sb.append(String.format("%-28s %9.1f%% %11.1f%%%n", "Circularity", mine.getCircularityPercent(), commCirc));
        sb.append("\n");
        sb.append(describe("footprint", mine.getPerCapitaKg(), commKg)).append("\n");
        sb.append(describe("waste", mine.getPerCapitaWasteKg(), commWaste)).append("\n");
        if (mine.getCircularityPercent() >= commCirc) {
            sb.append("Your circularity is at or above the community average - nice work reusing and repairing.");
        } else {
            sb.append("Your circularity is below the community average - try more second-hand or repaired items.");
        }
        return sb.toString();
    }

    private String describe(String what, double mine, double community) {
        if (community <= 0) {
            return "Your " + what + " could not be compared.";
        }
        double diff = (mine - community) / community * 100;
        if (Math.abs(diff) < 1) {
            return "Your " + what + " is about the same as the community average.";
        }
        return String.format("Your %s is %.0f%% %s the community average.", what, Math.abs(diff),
                diff < 0 ? "below" : "above");
    }
}
