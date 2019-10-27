/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maincontainer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Ir. M. El Adoui
 */
public class Vendeur2Gui extends JFrame {
    private JTextArea TextArea = new JTextArea();
    //déclaration de l'agent Client
    private Vendeur2Agent Vendeur2;
    public Vendeur2Gui (){
        // ... Acompléter
        // ............
        // ...........;
        // ............
        // ............
        // ............
        // ..........
      }
public Vendeur2Agent getVendeur2Agent(){
    return Vendeur2;
}

public void setVendeur2Agent(Vendeur2Agent Vendeur){
    this.Vendeur2=Vendeur;
}
public void showMessage(String msg, boolean append){
    if(append==true){
      // A compléter
      // ...........
      // ...........
      // ...........
      // ...........
    }
    else{
        // A compléter
        // ..........
    }
}

}