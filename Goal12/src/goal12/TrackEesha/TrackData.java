/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.TrackEesha;

/**
 *
 * @author Eesha Penkey <your.name at your.org>
 */
public class TrackData {
     public static final String ALL_CATEGORIES = "all categories";
    public static final String[] CATEGORIES = {"food", "electronics", "clothing", "household"};
    public static final String[] BENCHMARK_CATEGORIES =
            {"food", "electronics", "clothing", "household", ALL_CATEGORIES};
    public static final String[] WASTE_REASONS = {"broken", "unwanted", "expired / spoiled", "packaging"};

    private TrackData() { }

    /** Item types offered for a category. */
    public static String[] getItemTypes(String category) {
        if ("electronics".equals(category)) {
            return new String[]{"phone", "laptop", "small appliance", "accessories"};
        } else if ("clothing".equals(category)) {
            return new String[]{"tops", "trousers", "shoes", "outerwear"};
        } else if ("household".equals(category)) {
            return new String[]{"furniture", "cleaning products", "kitchenware", "packaging"};
        }
        return new String[]{"produce", "meat/dairy", "bakery", "packaged goods"};
    }

    /** Packaged goods and packaging are treated as single-use items. */
    public static boolean isSingleUse(String itemType) {
        return "packaged goods".equals(itemType) || "packaging".equals(itemType);
    }
}
