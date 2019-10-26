/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maincontainer;

import java.awt.BorderLayout;
import java.awt.Color;
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
public class Vendeur1Gui extends JFrame {
    private JTextArea TextArea = new JTextArea();
    //déclaration de l'agent Client
    private Vendeur1Agent Vendeur1;
    public Vendeur1Gui (){
        JPanel Panel = new JPanel();
        Panel.setLayout(new FlowLayout());

        TextArea.setFont(new Font("Serif", Font.ITALIC, 15));
        
       // Panel.add(TextArea);
        this.setTitle("Vendeur 1");
        this.setLayout(new BorderLayout());
        this.add(Panel, BorderLayout.NORTH);
        this.add(new JScrollPane(TextArea), BorderLayout.CENTER);
        this.setSize(510,500);
        this.setVisible(true);
      }
public Vendeur1Agent getVendeur2Agent(){
    return Vendeur1;
}

public void setVendeur1Agent(Vendeur1Agent Vendeur){
    this.Vendeur1=Vendeur;
}
public void showMessage(String msg, boolean append){
    if(append==true){
        TextArea.append(msg+"\n");
        TextArea.append("---------------------------------------------------------------------------------------------\n");    }
    else{
        TextArea.setText(msg);
    }
}

}