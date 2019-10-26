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

public class CourtierAgent extends GuiAgent{
    //private ClientGui gui;
    public static String pieceDemande="";
    public static String nbrpiece="";
    public static String pieceEnregistre="piece";
    public static String AgentEnregistre="VendeurX";
    private AID[] listVendeurs;
    public boolean passage = false;
    public boolean trouve = false;
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
           //System.out.println("Demarrage de l'agent courtier : ");
          // MessageTemplate msgTemp1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),MessageTemplate.MatchOntology("achat-vent"));           
          // MessageTemplate msgTemp = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),MessageTemplate.MatchOntology("vent"));
           
           
           ACLMessage msg = receive();       
           if(msg!=null){     
               String[] tmp = null; 
               
               //---------------------------------------------------------------------------------------
               //-------------- On reçoit la demande du client et on transfert aux vendeurs ------------
               //---------------------------------------------------------------------------------------
               
               if(msg.getPerformative()==ACLMessage.REQUEST){
                   try {
                       tmp = (String[]) msg.getContentObject();
                       pieceDemande=tmp[0];
                       nbrpiece =tmp[1];
                       System.out.println("Demande d'achat de "+nbrpiece+" pièce(s) de type : "+pieceDemande+" par l'agent"+msg.getSender().getLocalName() + "\n");
                      
                       if(pieceDemande.equals(pieceEnregistre)){
                        System.out.println("Je me rappelle bien que cette pièce est chez le vendeur : "+AgentEnregistre);
                       } 
                       else
                        {  
                           try {
                               
                               //-----Envoi du message aux 2 vendeurs-----------
                               
                               ACLMessage msgCFP = new ACLMessage(ACLMessage.CFP);
                               msgCFP.setContentObject(new String[]{pieceDemande,nbrpiece});
                               msgCFP.setOntology("DemandeDuClient");                               
                              
                               msgCFP.addReceiver(new AID("Vendeur1Agent",AID.ISLOCALNAME));
                               msgCFP.addReceiver(new AID("Vendeur2Agent",AID.ISLOCALNAME));
                               send(msgCFP);     
                                                              
                               //-----Envoi de la notification au client--------
                               
                               ACLMessage Notif= new ACLMessage(ACLMessage.INFORM);
                               Notif.setOntology("Achat-vente");
                               Notif.setLanguage("Francais");
                               Notif.addReceiver(msg.getSender()); //on récupère l'adresse du client pour lui envoyé la notif
                               Notif.setContent("Votre demande a été bien reçue, traitement en cours ...");    
                               send(Notif);
                               
                           } catch (IOException ex) {
                               Logger.getLogger(CourtierAgent.class.getName()).log(Level.SEVERE, null, ex);
                           }       
                        }
                       
                   } catch (UnreadableException ex) {
                       Logger.getLogger(CourtierAgent.class.getName()).log(Level.SEVERE, null, ex);
                   }
                }
              
               
              //---------------------------------------------------------------------------------------
              //----------------------- On traite les réponses des vendeurs ---------------------------
              //---------------------------------------------------------------------------------------

              
              if(msg.getPerformative() == ACLMessage.PROPOSE){
                  String[] Porposed = null;
                   try {
                       Porposed = (String[]) msg.getContentObject();
                   } catch (UnreadableException ex) {
                       Logger.getLogger(CourtierAgent.class.getName()).log(Level.SEVERE, null, ex);
                   }
                       String piecePropose  = Porposed[0];
                       double priceProposed = Double.parseDouble(Porposed[1]);
                       
                       System.out.println("Le "+msg.getSender().getLocalName()+" propose " +nbrpiece+" pièce(s) "+piecePropose+" au prix total de "+priceProposed);
                       
                       //============== le vendeur a l'objet que le client recherche ==============
                       
                       if(piecePropose.equals(pieceDemande)){
                           System.out.println("TROUVEE : le " + msg.getSender().getLocalName() +" peut nour fournir la(les) " + pieceDemande + "s\n");
                           trouve = true;
                           
                           //---------- message validation au bon vendeur ----------
                                                     
                           ACLMessage NotifAccept= new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                           NotifAccept.addReceiver(msg.getSender());
                           NotifAccept.setContent("La pièce a éte bien trouvée. Confirmation d'achat chez le vendeur "+msg.getSender().getLocalName());
                           send(NotifAccept);
                           
                           //---------- on avertit le client ----------
                           
                           ACLMessage Notif2= new ACLMessage(ACLMessage.INFORM);
                           Notif2.addReceiver(new AID("ClientAgent", AID.ISLOCALNAME));
                           Notif2.setContent("Pièce(s) trouvée(s). Confirmation d'achat chez le vendeur "+msg.getSender().getLocalName() + " pour un total de " + priceProposed+ "€");
                           Notif2.setOntology("Validation");
                           send(Notif2);                           
                           
                           
                           fin =5;
                           pieceEnregistre=pieceDemande;
                           AgentEnregistre=msg.getSender().getLocalName();
                       }
                       
                       
                       //============== le vendeur N'a PAS l'objet que le client recherche ==============
                       
                       else{
                           ACLMessage NotifRefu= new ACLMessage(ACLMessage.REFUSE);
                           NotifRefu.setOntology("Achat refuse");
                           NotifRefu.setLanguage("Francais");
                           NotifRefu.addReceiver(msg.getSender());
                           NotifRefu.setContent("la proposition de "+msg.getSender().getName()+" est refuse");
                           send(NotifRefu);
                       }
                       passage = true;
                                        
               }                      
           }
           
           /*       
           if (trouve == false && passage == true){
                ACLMessage Notif3= new ACLMessage(ACLMessage.INFORM);
                Notif3.addReceiver(new AID("ClientAgent", AID.ISLOCALNAME));
                Notif3.setContent("Aucun vendeur ne dispose de cet article");
                Notif3.setOntology("Néant");
                send(Notif3);
            }*/
           
           else block();
        }
        });
        
    }
    @Override
    protected void onGuiEvent(GuiEvent arg0){
        
    }
}
