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
            // Crée le profil d'un nouveau conteneur personnel appelé 'Fils' en local
            ProfileImpl profileImpl2= new ProfileImpl();
            profileImpl2.setParameter(ProfileImpl.CONTAINER_NAME, "Client");
            profileImpl2.setParameter(ProfileImpl.MAIN_HOST, "localhost");
          
            //crée le nouveau conteneur 'client'
            AgentContainer agentContainer2 = runtime.createAgentContainer(profileImpl2);  
            //crée un 1er agent appartenant au conteneur 'client' 
            AgentController agentController1 = agentContainer2.createNewAgent("ClientAgent", ClientAgent.class.getName() , new Object[]{});
            agentController1.start();
            /*
            
            Créer les autres agents selon les question du protocole
            // ..............
            // ..............
            // ..............
            // ..............
            // ..............
            // ..............
            
            */  
        } catch (ControllerException ex) {
               ex.printStackTrace();
    
    }
    
}

}