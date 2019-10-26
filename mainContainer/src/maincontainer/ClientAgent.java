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
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Sidi Ahmed Mahmoudi
 */

public class ClientAgent extends GuiAgent{
    private ClientGui gui;
    

    @Override
    protected void setup(){
        
        gui = new ClientGui();
        //association d'une interface à l'agent
        gui.setClientAgent(this);
        gui.showMessage("Démarrage de l'agent Client", true);
        gui.showMessage("ID : 1->  plaquettes, 2-> suspensions, 3->  boite", true);
        gui.showMessage("Promo : à l'achat de 3 pièces ou plus, une réduction de 30% vous sera réduite du prix total \n", true);
        
        this.addBehaviour(new CyclicBehaviour(){      
        @Override
        public void action(){
           //MessageTemplate msgTemp = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),MessageTemplate.MatchOntology("vente"));
           //gui.showMessage("Agent Client : les info reçues en temps réel sont : ",true);
           
           ACLMessage msg = receive();
           if(msg!=null){               
           gui.showMessage("Ontologie : \"" + msg.getOntology()+"\" -- "+" Message reçu du " + msg.getSender().getLocalName()+ " : \"" + msg.getContent()+ "\"", true);                    
           }
           else block();
        }
        
    
        });
    }

    @Override
    protected void onGuiEvent(GuiEvent ev){
         switch(ev.getType()){
            case 1:
                //gui.showMessage("Type d'évenement : 1", true);
                Map<String,Object> params= (Map<String,Object>) ev.getParameter(0);
                
                String piece=(String)params.get("piece");
                String CourtierAgent =(String)params.get("CourtierAgent");
                String quantitie =(String)params.get("quantitie");
                               
                               
         
            try { 
                ACLMessage aclMsg=new ACLMessage(ACLMessage.REQUEST);
                aclMsg.addReceiver(new AID(CourtierAgent,AID.ISLOCALNAME));
                aclMsg.setContentObject(new String[]{piece,quantitie});
                aclMsg.setOntology("Module1"); 
                send(aclMsg);
                gui.showMessage("La commande a été envoyée au Courtier \n", true);
                
                
             } 
             catch (IOException ex) {
                 Logger.getLogger(ClientAgent.class.getName()).log(Level.SEVERE, null, ex);
             }
         
                
                //aclMsg.setContent(piece);
         
                
                break;
            default :
                break;
        }
    }   
}
