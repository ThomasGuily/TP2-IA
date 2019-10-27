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
            // Crée le profil d'un nouveau conteneur personnel appelé 'Client' en local
            ProfileImpl profileClient= new ProfileImpl();
            profileClient.setParameter(ProfileImpl.CONTAINER_NAME, "Client");
            profileClient.setParameter(ProfileImpl.MAIN_HOST, "localhost");
          
            //crée le nouveau conteneur 'client'
            AgentContainer agentContainerClient = runtime.createAgentContainer(profileClient);  
            //crée un 1er agent appartenant au conteneur 'client' 
            AgentController agentClient = agentContainerClient.createNewAgent("ClientAgent", ClientAgent.class.getName() , new Object[]{});
            agentClient.start();
            
            //courtier
            ProfileImpl profileVendeurs= new ProfileImpl();
            profileVendeurs.setParameter(ProfileImpl.CONTAINER_NAME, "Vendeurs");
            profileVendeurs.setParameter(ProfileImpl.MAIN_HOST, "localhost");
          
            //crée le nouveau conteneur 'client'
            AgentContainer agentContainerVendeurs = runtime.createAgentContainer(profileVendeurs);  
            //crée un 1er agent appartenant au conteneur 'client' 
            AgentController agentVendeur1 = agentContainerVendeurs.createNewAgent("Vendeur1Agent", Vendeur1Agent.class.getName() , new Object[]{});
            AgentController agentVendeur2 = agentContainerVendeurs.createNewAgent("Vendeur2Agent", Vendeur2Agent.class.getName() , new Object[]{});
            AgentController agentVendeur3 = agentContainerVendeurs.createNewAgent("Vendeur3Agent", Vendeur3Agent.class.getName() , new Object[]{});
            agentVendeur1.start();
            agentVendeur2.start();
            agentVendeur3.start();
          
            
            // ..............
            ProfileImpl profileCourtier= new ProfileImpl();
            profileCourtier.setParameter(ProfileImpl.CONTAINER_NAME, "Courtier");
            profileCourtier.setParameter(ProfileImpl.MAIN_HOST, "localhost");
          
            //crée le nouveau conteneur 'client'
            AgentContainer agentContainerCourtier = runtime.createAgentContainer(profileCourtier);  
            //crée un 1er agent appartenant au conteneur 'client' 
            AgentController agentCourtier = agentContainerCourtier.createNewAgent("courtierAgent", courtierAgent.class.getName() , new Object[]{});
            agentCourtier.start();
           
            // ..............
      
            
       
        } catch (ControllerException ex) {
               ex.printStackTrace();
    
    }
    
}

}