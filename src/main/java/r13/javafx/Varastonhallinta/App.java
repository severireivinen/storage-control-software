package r13.javafx.Varastonhallinta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import r13.javafx.Varastonhallinta.models.Singleton;


/**
 * The "main" class. 
 * This class starts the program.
 * Localization bundle is first set here to default, then can be later modified.
 * @author Olli Kolkki, Severi Reivinen, Juuso Lahtinen
 */
public class App extends Application {

    /** The scene. */
    private static Scene scene;
    
    /** The bundle. */
    static ResourceBundle bundle = ResourceBundle.getBundle("bundles/TextResources");    

    /**
     * Start.
     *
     * @param stage the stage
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void start(Stage stage) throws IOException {	
        scene = new Scene(loadFXML("loginscreen"), 800, 600);
        stage.setTitle(bundle.getString("mainTitle"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the root.
     *
     * @param fxml the new root
     * @throws IOException Signals that an I/O exception has occurred.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Load FXML.
     *
     * @param fxml the fxml
     * @return the parent
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setResources(bundle);
        return fxmlLoader.load();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
    	Singleton.getInstance().setBundle(bundle);
        launch();
    }

}
