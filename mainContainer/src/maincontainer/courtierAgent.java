/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maincontainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ir. M. El Adoui
 */

public class courtierAgent extends GuiAgent{
    //private ClientGui gui;
    public static String pieceDemande="";
    public static String nbrpiece="";
    public static String pieceEnregistre="piece";
    public static String AgentEnregistre="VendeurX";
    private AID[] listVendeurs;
    @Override
    protected void setup(){
        //association d'une interface à l'agent
        //gui = new ClientGui();
        System.out.println("Démarrage de l'agent Courtier");
        
        
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(parallelBehaviour);
      
        parallelBehaviour.addSubBehaviour(new Behaviour(){
            int fin;
        @Override
        public boolean done(){
            if(fin ==4){ fin =0;return true;} // le courtier doit être toujours attentif condition à enlever!
            else return false;
        }
       
        @Override
        public void action(){
           System.out.println("Demarrage de l'agent courtier : ");
          // MessageTemplate msgTemp1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),MessageTemplate.MatchOntology("achat-vent"));
           
          // MessageTemplate msgTemp = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),MessageTemplate.MatchOntology("vent"));
           
           
           ACLMessage msg = receive();       
           if(msg!=null){     
               String[] tmp = null; // l'object qui contient la demande du client 0: piece, 1: quantitie
               if(msg.getPerformative()==ACLMessage.REQUEST){
                   try {
                       tmp = (String[]) msg.getContentObject();
                       pieceDemande=tmp[0];
                       nbrpiece =tmp[1];
                       System.out.println("demande d'achat de "+nbrpiece+" piece(s) de type : "+pieceDemande+" par l'agent"+msg.getSender().getLocalName());
                      
                       if(pieceDemande.equals(pieceEnregistre)){
                        System.out.println("je me rappelle bien que cette piece est chez le vendeur : "+AgentEnregistre);
                       } 
                       else
                        {  
                           try {
                               ACLMessage msgCFP = new ACLMessage(ACLMessage.CFP);
                               msgCFP.setContentObject(new String[]{pieceDemande,nbrpiece});
                               // Envoi du message aux deux vendeurs
                               // .....
                               // .....
                               ACLMessage Notif= new ACLMessage(ACLMessage.INFORM);
                               Notif.setOntology("Achat-vente");
                               Notif.setLanguage("Francais");
                               Notif.addReceiver(msg.getSender());
                               Notif.setContent("Votre demande a été bien reçu, traitement en cours ...");    
                               send(Notif);  //envoie de la notif au client !
                               // envoie des Call of Proposals !
                           } catch (IOException ex) {
                               Logger.getLogger(courtierAgent.class.getName()).log(Level.SEVERE, null, ex);
                           }       
                        }
                       
                   } catch (UnreadableException ex) {
                       Logger.getLogger(courtierAgent.class.getName()).log(Level.SEVERE, null, ex);
                   }
                }
                
              if(msg.getPerformative() == ACLMessage.PROPOSE){
                  String[] Porposed = null;
                   try {
                       Porposed = (String[]) msg.getContentObject();
                   } catch (UnreadableException ex) {
                       Logger.getLogger(courtierAgent.class.getName()).log(Level.SEVERE, null, ex);
                   }
                       String piecePropose  = Porposed[0];
                       double priceProposed = Double.parseDouble(Porposed[1]);
                       
                       System.out.println("le vendeur "+msg.getSender()+" propose la piece "+piecePropose+" au prix total de "+priceProposed);
                       if(piecePropose.equals(pieceDemande)){
                           System.out.println("TROUVEE : PieceDemande=  "+pieceDemande);
                           ACLMessage NotifAccept= new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                           NotifAccept.addReceiver(msg.getSender());
                           NotifAccept.setContent("La piece a éte bien trouvé Confirmation d'achat chez le vendeur"+msg.getSender().getName());
                           // Envoyer la notification;
                           
                           ACLMessage Notif2= new ACLMessage(ACLMessage.INFORM);
                           Notif2.addReceiver(new AID("ClientAgent", AID.ISLOCALNAME));
                           Notif2.setContent("La piece a éte bien trouvé Confirmation d'achat chez le vendeur"+msg.getSender().getName());
                           // Envoyer la notification au client
                           // ............
                           fin =5;
                           pieceEnregistre=pieceDemande;
                           AgentEnregistre=msg.getSender().getLocalName();
                       }
                       else{
                           ACLMessage NotifRefu= new ACLMessage(ACLMessage.REFUSE);
                           NotifRefu.setOntology("Achat refuse");
                           NotifRefu.setLanguage("Francais");
                           NotifRefu.addReceiver(msg.getSender());
                           NotifRefu.setContent("la proposition de "+msg.getSender().getName()+" est refuse");
                           // Envoyer le message;
                           // ......;
                       }
                 
               }                      
           }
           else block();
        }
        });
        
    }
    @Override
    protected void onGuiEvent(GuiEvent arg0){
        
    }
}
