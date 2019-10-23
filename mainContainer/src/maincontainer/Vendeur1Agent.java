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
import static maincontainer.Vendeur2Agent.priceTot;

/**
 *
 * @author Ir. M. El Adoui
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
                   switch(msg.getPerformative()){
                   case ACLMessage.CFP:   
                       tmp = (String[]) msg.getContentObject();
                       String piece = tmp[0];
                       nbr= Integer.parseInt(tmp[1]);
                       gui.showMessage("Demande d'achat de "+nbr+" piece(s)  de type : "+piece, true);
                      
                       gui.showMessage("Vendeur 1 : Type de message reçu CFP, je propose mon service ...", true);  
                       ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
                       message.addReceiver(new AID("courtierAgent", AID.ISLOCALNAME));
                       try 
                       {
                           message.setContentObject(new String[]{porp,priceTot+""});
                           // Envoyer le message avec l'ontologie "Vente"
                           // ........
                           // .........
                       } 
                       catch (IOException ex) {
                           Logger.getLogger(Vendeur1Agent.class.getName()).log(Level.SEVERE, null, ex);
                       }
                       break;
                       
                   case ACLMessage.ACCEPT_PROPOSAL:
                       gui.showMessage("Notification : offre accepté par le courtier", true);
                       // Calcul du prix total;
                       double priceA=priceTot;
                       if(nbr>2){
                       // Appliquer la réduction de 30% au prix total ....
                       // Afichage un message de prmotion et le prix a payer
                       // .......
                       // .........
                       }  
                       // Aficcher le prix total obtenu (sans promotion)
                       // Afficher des messages confimant la fin de la vente 
                       fin =5;
                       break;
                   case ACLMessage.REFUSE:
                       // Notifier via l'interface le refus de l'offre

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
