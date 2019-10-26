/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maincontainer;

import jade.gui.GuiEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 *
 * @author Ir. M. El Adoui
 * @author Guily Thomas
 * @author Mistri Pierre-François
 */
public class ClientGui extends JFrame {
    String courtiers[]= { "CourtierAgent","Courtier2Agent","Courtier3Agent"};
    String pieces[]= {"plaquettes","suspensions","boîte"};
    String quantitie[]= {"1","2","3","4","5","7"};
   /*1:  plaquettes, 2 : suspensions, 3:  boite*/
    private JLabel ClientLab = new JLabel ("Selectionnez le courtier:");
    JComboBox jtextCourtiertLab = new JComboBox (courtiers);
    
    private JLabel PieceLab = new JLabel ("ID de la piece :");
    private JComboBox jtextPieceLab = new JComboBox(pieces);
    
    private JLabel QuantLab = new JLabel ("Quantitié :");
    private JComboBox jtextQuantLab = new JComboBox(quantitie);
    //private JTextField jtextPieceLab = new JTextField (12);
    
    private JButton ButtonEnvoyer = new JButton("Valider");
    private JTextArea TextArea = new JTextArea();
    //déclaration de l'agent Client
    private ClientAgent Client;
    public ClientGui (){
        JPanel Panel = new JPanel();
        Panel.setLayout(new FlowLayout());
        
        Panel.add(ClientLab);
        Panel.add(jtextCourtiertLab);
        
        Panel.add(PieceLab);
        Panel.add(jtextPieceLab);
        
        Panel.add(QuantLab);
        Panel.add(jtextQuantLab);
        Panel.add(ButtonEnvoyer);
        TextArea.setFont(new Font("Serif", Font.BOLD, 14));
        
       // Panel.add(TextArea);
        this.setTitle("Interface Client");
        this.setLayout(new BorderLayout());
        this.add(Panel, BorderLayout.NORTH);
        this.add(new JScrollPane(TextArea), BorderLayout.CENTER);
        this.setSize(1600,400);
        this.setVisible(true);
        ButtonEnvoyer.addActionListener(new ActionListener(){
        
        @Override
        public void actionPerformed(ActionEvent e){
            
            String agentName=(String) jtextCourtiertLab.getSelectedItem();
            String piece=(String) jtextPieceLab.getSelectedItem();
            String quantitie = (String) jtextQuantLab.getSelectedItem();
            GuiEvent gev  = new GuiEvent(this,1);
            Map<String, Object> params = new HashMap<>();
            params.put("CourtierAgent",agentName);
            params.put("piece", piece);
            params.put("quantitie", quantitie);
           
            gev.addParameter(params);
            Client.onGuiEvent(gev);
       
        } 
    });
    }
public ClientAgent getClientAgent(){
    return Client;
}

public void setClientAgent(ClientAgent Client){
    this.Client=Client;
}
public void showMessage(String msg, boolean append){
    if(append==true){
        Font font = new Font("Dialog", Font.BOLD, 16);
        TextArea.setFont(font);
        TextArea.setForeground(Color.WHITE);
        TextArea.setBackground(Color.BLUE);
        TextArea.append(msg+"\n");
      
    }
    else{
        TextArea.setText(msg);
    }
}

}
    
