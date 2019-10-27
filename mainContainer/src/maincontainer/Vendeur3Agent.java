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
 */

public class Vendeur3Agent extends GuiAgent {
   private Vendeur3Gui gui;
   String porp= "boite";
   double priceUnite=99.99;
   static double priceTot=0;
   String piece;
   int nbr;
    @Override
    protected void setup(){
        gui = new Vendeur3Gui();
        //association d'une interface à l'agent
        gui.setVendeur3Agent(this);
        gui.showMessage("Démarrage de l'agent Vendeur 3 - Je suis en écoute", true);
        
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
                   switch(msg.getPerformative()){
                   case ACLMessage.CFP:   
                       
                       tmp = (String[]) msg.getContentObject();
                       piece = tmp[0];
                       nbr= Integer.parseInt(tmp[1]);                            
                       gui.showMessage("Vendeur 3 : Type de message reçu CFP, je propose mon service ...", true);  
                       ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
                       message.addReceiver(new AID("courtierAgent", AID.ISLOCALNAME));
                       try {
                           
                       // A compléter 
                       // ...........
                       
                           priceTot = priceUnite * nbr;
                           
                           if (nbr > 2) {
                                priceTot = priceTot - priceTot*(5/10);                             
                           }
                           message.setContentObject(new String[]{porp,priceTot+""});
                           message.setOntology("Vente3");
                           send(message); 
                      
                       } catch (IOException ex) {
                           Logger.getLogger(Vendeur2Agent.class.getName()).log(Level.SEVERE, null, ex);
                       }
                       break;
                       
                   case ACLMessage.ACCEPT_PROPOSAL:
                       // A compléter 
                       gui.showMessage("Offre acceptée par le courtier", true);
                       priceTot = priceUnite * nbr;
                       gui.showMessage("Prix total : " + priceTot, true);
                       if(nbr>2){
                           gui.showMessage("Le nombre d'article étant >3, il y a une réduction de 50%", true);
                           priceTot = priceTot - priceTot*(5/10);
                       }  
                       gui.showMessage("Vente terminée", true);
                       fin =5;
                       break;
                       
                   case ACLMessage.REFUSE:
                       // A compléter 
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
                   Logger.getLogger(Vendeur2Agent.class.getName()).log(Level.SEVERE, null, ex);
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