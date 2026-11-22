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
    public static final String[] CATEGORIES = {
        "food", "electronics", "clothing", "furniture", "plastic", "chemicals", "metals"
    };
    public static final String[] BENCHMARK_CATEGORIES = {
        "food", "electronics", "clothing", "furniture", "plastic", "chemicals", "metals", ALL_CATEGORIES
    };

    private TrackData() { }

    /** Item types offered for a category. */
    public static String[] getItemTypes(String category) {
        if ("electronics".equals(category)) {
            return new String[]{"phone", "laptop", "small appliance", "accessories"};
        } else if ("clothing".equals(category)) {
            return new String[]{"tops", "trousers", "shoes", "outerwear"};
        } else if ("furniture".equals(category)) {
            return new String[]{"tables / desks", "chairs / sofas", "beds / wardrobes", "shelving / small furniture"};
        } else if ("plastic".equals(category)){
            return new String[]{"bottles / bags", "food packaging", "reusable containers", "other plastic"};
        } else if ("chemicals".equals(category)){
            return new String[]{"household cleaners", "detergents", "paints / solvents", "garden chemicals"};
        } else if ("metals".equals(category)){
            return new String[]{"cans / tins", "foil / trays", "scrap metal / tools", "cookware"};
        }
        return new String[]{"produce", "meat/dairy", "bakery", "packaged goods"};
    }

    /** Packaged goods and packaging are treated as single-use items. */
    public static boolean isSingleUse(String itemType) {
        return "packaged goods".equals(itemType)
            || "food packaging".equals(itemType)
            || "bottles / bags".equals(itemType)
            || "foil / trays".equals(itemType);
    }
}
