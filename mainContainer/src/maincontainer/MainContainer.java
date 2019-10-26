/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maincontainer;
import jade.wrapper.AgentContainer;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Sidi Ahmed Mahmoudi
 * @author Guily Thomas
 * @author Mistri Pierre-François
 */
public class MainContainer {
  /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //crée l'instance de Runtime
        Runtime runtime = Runtime.instance();
        //Définir la propriété du conteneur
        Properties Options = new ExtendedProperties();
        //Afficher l'interface de JADE 
        Options.setProperty(Profile.GUI, "true");
        ProfileImpl Interface =new ProfileImpl(Options);
        //Créé l'agent principal
        AgentContainer AgentInterface = runtime.createMainContainer(Interface);       
        try {
            //Démmarer l'agent principal
            AgentInterface.start();
        } catch (ControllerException ex) {  
            Logger.getLogger(MainContainer.class.getName()).log(Level.SEVERE, null, ex); 
        }
        
      
        try {
            
            //____________________________Client________________________________
            
            ProfileImpl Client= new ProfileImpl();
            Client.setParameter(ProfileImpl.CONTAINER_NAME, "Client");
            Client.setParameter(ProfileImpl.MAIN_HOST, "localhost");
                      
            AgentContainer AgentClient = runtime.createAgentContainer(Client);  
            
            AgentController AgentClientController = AgentClient.createNewAgent("ClientAgent", ClientAgent.class.getName() , new Object[]{});
            AgentClientController.start();
           
            //___________________________COURTIER_______________________________
            
            ProfileImpl Courtier= new ProfileImpl();
            Courtier.setParameter(ProfileImpl.CONTAINER_NAME, "Courtier");
            Courtier.setParameter(ProfileImpl.MAIN_HOST, "localhost");
                      
            AgentContainer AgentCourtier = runtime.createAgentContainer(Courtier);  
            
            AgentController AgentCourtierController = AgentCourtier.createNewAgent("CourtierAgent", courtierAgent.class.getName() , new Object[]{});
            AgentCourtierController.start();
            
            //___________________________VENDEUR________________________________
            
            ProfileImpl Vendeur = new ProfileImpl();
            Vendeur.setParameter(ProfileImpl.CONTAINER_NAME, "Vendeur");
            Vendeur.setParameter(ProfileImpl.MAIN_HOST, "localhost");
                      
            AgentContainer agentVendeur = runtime.createAgentContainer(Vendeur);  
            
            AgentController AgentVendeurController1 = agentVendeur.createNewAgent("Vendeur1Agent", Vendeur1Agent.class.getName() , new Object[]{});
            AgentVendeurController1.start();
            
            AgentController AgentVendeurController2 = agentVendeur.createNewAgent("Vendeur2Agent", Vendeur2Agent.class.getName() , new Object[]{});
            AgentVendeurController2.start();
            
            
            
        } catch (ControllerException ex) {
               ex.printStackTrace();
    
    }
    
}

}