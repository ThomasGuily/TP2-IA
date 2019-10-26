/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maincontainer;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ir. M. El Adoui
 * @author Guily Thomas
 * @author Mistri Pierre-François
 */

public class Vendeur1Agent extends GuiAgent {
   private Vendeur1Gui gui;
   String porp= "suspensions";
   double priceUnite=29.99;
   double priceTot=0;
   int nbr;
    @Override
    protected void setup(){
        gui = new Vendeur1Gui();
        //association d'une interface à l'agent
        gui.setVendeur1Agent(this);
        gui.showMessage("Démarrage de l'agent Vendeur 1 - Je suis en écoute", true);
        
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(parallelBehaviour);

        parallelBehaviour.addSubBehaviour(new Behaviour(){
        int fin;
        @Override
        public boolean done(){
           if(fin ==5) {
               fin =0;
               return true;
           }
           else return false;
       }
       @Override
       public void action(){ 
           ACLMessage msg = receive();
           String[] tmp;
           if(msg!=null){
               try {                   
                   switch(msg.getPerformative()){ //on regarde le type du message
                       
                       //-----------------Message de demande d'informations du courtier----------------
                       
                   case ACLMessage.CFP:   
                       tmp = (String[]) msg.getContentObject();
                       String piece = tmp[0];
                       nbr= Integer.parseInt(tmp[1]);
                       gui.showMessage("Vendeur 1 : Type de message reçu CFP, je propose mes services", true);                         
                       gui.showMessage("Demande d'achat de "+nbr+" pièce(s)  de type : "+piece, true);  
                       ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
                       message.addReceiver(new AID("courtierAgent", AID.ISLOCALNAME));
                                             
                       priceTot = priceUnite * nbr;     //calcul du prix
                       if (nbr > 3) {
                           priceTot = priceTot - priceTot*(3/10); //prix réduit si > 3                           
                       }
                       try{
                           message.setContentObject(new String[]{porp,priceTot+""});
                           message.setOntology("Vente");
                           send(message);                           
                       } 
                       catch (IOException ex) {
                           Logger.getLogger(Vendeur1Agent.class.getName()).log(Level.SEVERE, null, ex);
                       }
                       break;
                       
                       
                       //-----------------Message de validation du courtier----------------
                                              
                   case ACLMessage.ACCEPT_PROPOSAL:
                       gui.showMessage("Offre acceptée par le courtier", true);
                       priceTot = priceUnite * nbr;
                       gui.showMessage("Prix total : " + priceTot, true);
                       if(nbr>2){
                           gui.showMessage("Le nombre d'article étant > 3, il y a une réduction de 30%", true);
                           priceTot = priceTot - priceTot*(3/10);
                       }
                       gui.showMessage("Vente terminée", true);
                       fin =5;
                       break;
                       
                       
                       //-----------------Message de refus du courtier----------------
                       
                   case ACLMessage.REFUSE:
                       gui.showMessage("Article non disponible", true);
                       gui.showMessage("Offre refusée par le courtier", true);
                       gui.showMessage("Fin de l'interaction", true);
                       fin =5;
                       //myAgent.doDelete();
                       break;
                   default : break; 
               } 
               } 
               catch (UnreadableException ex) {
                   Logger.getLogger(Vendeur1Agent.class.getName()).log(Level.SEVERE, null, ex);
               }   
               }          
            else block();
       }
          }); 
    }  
    @Override 
    protected void takeDown(){
    }
    @Override
    protected void onGuiEvent(GuiEvent arg0){      
    }
}
