/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12.LearnAbooh;

/**
 *
 * @author Obaapanin Abooh Agyekum <your.name at your.org>
 */
public abstract class Pledge {

    protected String description, category;

    protected Pledge(String description, String category) {
        this.description = description;
        this.category = category;
    }
''
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public abstract String getSummary();

    public abstract String toFileString();

}
