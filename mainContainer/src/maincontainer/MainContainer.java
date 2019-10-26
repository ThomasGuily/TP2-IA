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
 */
public class MainContainer {
  /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //crée l'instance de Runtime
        Runtime runtime = Runtime.instance();
        //Définir la propriété du conteneur
        Properties properties = new ExtendedProperties();
        //Afficher l'interface de JADE 
        properties.setProperty(Profile.GUI, "true");
        ProfileImpl profileImpl =new ProfileImpl(properties);
        //Créé l'agent principal
        AgentContainer agentContainer1 = runtime.createMainContainer(profileImpl);       
        try {
            //Démmarer l'agent principal
            agentContainer1.start();
        } catch (ControllerException ex) {  
            Logger.getLogger(MainContainer.class.getName()).log(Level.SEVERE, null, ex); 
        }
        
      
        try {
            
            //--------------------------Client----------------------------------
            
            ProfileImpl profileImpl2= new ProfileImpl();
            profileImpl2.setParameter(ProfileImpl.CONTAINER_NAME, "Client");
            profileImpl2.setParameter(ProfileImpl.MAIN_HOST, "localhost");
                      
            AgentContainer agentClient = runtime.createAgentContainer(profileImpl2);  
            
            AgentController agentController1 = agentClient.createNewAgent("ClientAgent", ClientAgent.class.getName() , new Object[]{});
            agentController1.start();
           
            
            //--------------------------Courtier--------------------------------
            
            ProfileImpl profileImpl3= new ProfileImpl();
            profileImpl3.setParameter(ProfileImpl.CONTAINER_NAME, "Courtier");
            profileImpl3.setParameter(ProfileImpl.MAIN_HOST, "localhost");
                      
            AgentContainer agentCourtier = runtime.createAgentContainer(profileImpl3);  
            
            AgentController agentController2 = agentCourtier.createNewAgent("CourtierAgent", CourtierAgent.class.getName() , new Object[]{});
            agentController2.start();
            
            //--------------------------Vendeur---------------------------------
            
            ProfileImpl profileImpl4= new ProfileImpl();
            profileImpl4.setParameter(ProfileImpl.CONTAINER_NAME, "Vendeur");
            profileImpl4.setParameter(ProfileImpl.MAIN_HOST, "localhost");
                      
            AgentContainer agentVendeur = runtime.createAgentContainer(profileImpl4);  
            
            AgentController agentController3 = agentVendeur.createNewAgent("Vendeur1Agent", Vendeur1Agent.class.getName() , new Object[]{});
            agentController3.start();
            
            AgentController agentController4 = agentVendeur.createNewAgent("Vendeur2Agent", Vendeur2Agent.class.getName() , new Object[]{});
            agentController4.start();
            
            
            
        } catch (ControllerException ex) {
               ex.printStackTrace();
    
    }
    
}

}