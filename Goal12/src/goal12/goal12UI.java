/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goal12;

//import goal12.ActJulieta.TestClassA;
import goal12.ActJulieta.ActMenuGUI;
import goal12.LearnAbooh.LearnUI;
import goal12.TrackEesha.TestClassT;
import javax.swing.JOptionPane;

/**
 *
 * @author Obaapanin Abooh Agyekum <your.name at your.org>
 */
public class goal12UI {

    public goal12UI() {
    }
    
    public void mainMenu(){
        int choice = Integer.parseInt(JOptionPane.showInputDialog(null, "1: Learn, 2: Track 3: Act"));

        switch (choice){
            case 1:
                LearnUI L = new LearnUI();
                L.testPrint();
                break;
            case 2:
                TestClassT T = new TestClassT();
                T.testPrint();
                break;
            case 3:
                ActMenuGUI actGUI = new ActMenuGUI();
                actGUI.setVisible(true);
                // TestClassA A = new TestClassA();
                // A.testPrint();
                break;
        }
    }
}
