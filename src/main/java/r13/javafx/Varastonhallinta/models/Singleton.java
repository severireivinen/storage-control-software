package r13.javafx.Varastonhallinta.models;

import java.util.ResourceBundle;

/**
 * A Singleton class used to store the logged in User for use in Scenes
 * Also stores the properties file required by localization
 * @author Juuso Lahtinen
 */
public class Singleton {
	

	private static Singleton instance;
    
    /** The username. */
    private String username;
    
    /** The bundle used for localization. */
    private ResourceBundle bundle;
   
    /**
     * Instantiates a new singleton.
     */
    private Singleton() {}

    /**
     * Gets the single instance of Singleton.
     *
     * @return single instance of Singleton
     */
    public static Singleton getInstance() {      
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}
}
