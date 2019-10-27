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
public class Vendeur3Gui extends JFrame {
    private JTextArea TextArea = new JTextArea();
    //déclaration de l'agent Client
    private Vendeur3Agent Vendeur3;
    public Vendeur3Gui (){
        // ... Acompléter
        JPanel Panel = new JPanel();
        Panel.setLayout(new FlowLayout());

        TextArea.setFont(new Font("Serif", Font.ITALIC, 15));
        
       // Panel.add(TextArea);
        this.setTitle("Vendeur 3");
        this.setLayout(new BorderLayout());
        this.add(Panel, BorderLayout.NORTH);
        this.add(new JScrollPane(TextArea), BorderLayout.CENTER);
        this.setSize(510,500);
        this.setVisible(true);
      }
public Vendeur3Agent getVendeur3Agent(){
    return Vendeur3;
}

public void setVendeur3Agent(Vendeur3Agent Vendeur){
    this.Vendeur3=Vendeur;
}
public void showMessage(String msg, boolean append){
    if(append==true){
        // A compléter
        TextArea.setBackground(Color.GRAY);
        TextArea.append(msg+"\n");
        TextArea.append("---------------------------------------------------------------------------------------------\n");
    }
    else{
        // A compléter
        TextArea.setText(msg);
    }
}

}
