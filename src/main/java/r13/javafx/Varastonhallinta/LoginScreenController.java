package r13.javafx.Varastonhallinta;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;


/**
 * The Class LoginScreenController.
 * Controller for loginscreen.fxml.
 * Class includes methods for logging in and for changing language (locale).
 * @author Juuso Lahtinen
 */
public class LoginScreenController {
	
	/** The locale. */
	Locale locale;
    
    /** The bundle. */
    ResourceBundle bundle = ResourceBundle.getBundle("bundles/TextResources"); 
    
    /** The dao. */
    private UserAccessObject dao = new UserAccessObject();	
    
    /** The current user. */
    public String currentUser;
    
    /** The login label. */
    @FXML
    private Label loginLabel;

    /** The username. */
    @FXML
    private TextField username;

    /** The password. */
    @FXML
    private PasswordField password;

    /** The login button. */
    @FXML
    private Button loginButton;

    /** The username label. */
    @FXML
    private Label usernameLabel;

    /** The password label. */
    @FXML
    private Label passwordLabel;

    /** The login error. */
    @FXML
    private Label loginError;

    /** The fi locale button. */
    @FXML
    private ImageView fiLocaleButton;

    /** The en locale button. */
    @FXML
    private ImageView enLocaleButton;
	
    /**
     * Check login.
     * Method compares user input data (username + password) to the data in the database, which is UserAccessObject in this case.
     * If the data is ok, switch to next Scene, otherwise error message pops up.
     * Method also stores the given username to a Singleton, which can be used on other Scenes.
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
  
	@FXML
	public void checkLogin(ActionEvent event) throws IOException {

		if (dao.checkLogin(username.getText().toString(), password.getText().toString())) {
					
			currentUser = username.getText().toString();
			System.out.println("Login successful, logged in as: " + currentUser);	
			Singleton.getInstance().setUsername(username.getText().toString());
			
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(bundle);
	        loader.setLocation(getClass().getResource("mainWindow.fxml"));

	        Parent mainWindowParent = loader.load();
	        
	        Scene mainWindowScene = new Scene(mainWindowParent);
	        
	        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        
	        window.setScene(mainWindowScene);
	        window.show(); 
	        
		} else {
			loginError.setText(bundle.getString("loginErrorText"));
		}		
	}
	
	/**
	 * Change locale to FI.
	 * Method changes the locale and sets it to a Singleton so it can be used in other Scenes.
	 * This changes the whole applications locale.
	 * setText's are used to also immediately change the active Scene's locale.
	 */
	
	@FXML
	public void changeLocaleToFI() {
		locale = new Locale("fi", "FI");
		bundle = ResourceBundle.getBundle("bundles/TextResources", locale);
		System.out.println("locale changed to FI");
		Singleton.getInstance().setBundle(bundle);
		
		loginLabel.setText(bundle.getString("loginLabel"));
		usernameLabel.setText(bundle.getString("username"));
		passwordLabel.setText(bundle.getString("password"));
		loginButton.setText(bundle.getString("loginButton"));
		username.setPromptText(bundle.getString("username"));
		password.setPromptText(bundle.getString("password"));
	}
	
	/**
	 * Change locale to EN.
	 * Method changes the locale and sets it to a Singleton so it can be used in other Scenes.
	 * This changes the whole applications locale.
	 * setText's are used to also immediately change the active Scene's locale.
	 */
	@FXML
	public void changeLocaleToEN() {
		locale = new Locale("en", "US");
		bundle = ResourceBundle.getBundle("bundles/TextResources", locale);
		System.out.println("locale changed to EN");
		Singleton.getInstance().setBundle(bundle);
		
		loginLabel.setText(bundle.getString("loginLabel"));
		usernameLabel.setText(bundle.getString("username"));
		passwordLabel.setText(bundle.getString("password"));
		loginButton.setText(bundle.getString("loginButton"));
		username.setPromptText(bundle.getString("username"));
		password.setPromptText(bundle.getString("password"));
	}
	
}
