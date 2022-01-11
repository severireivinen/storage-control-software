package r13.javafx.Varastonhallinta;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Product;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;

/**
 * Controller for the New product window. Used to create new Products.
 * @author Olli Kolkki
 */
public class NewProductController {
	
	/** The bundle used for localization. */
	ResourceBundle bundle = Singleton.getInstance().getBundle();	
	
    /** The data access object used for accessing the Product table in the database. */
    private ProductAccessObject dao = new ProductAccessObject();

    /** The add btn. */
    @FXML
    private Button addBtn;
    
    /** The product id field. */
    @FXML
    private TextField productIdField;
    
    /** The product name field. */
    @FXML
    private TextField productNameField;
    
    /** The product description field. */
    @FXML
    private TextField productDescriptionField;
    
    /** The product price field. */
    @FXML
    private TextField productPriceField;
    
    /** The product location field. */
    @FXML
    private TextField productLocationField;
    
    
    /** The back button. */
    @FXML
    private Button backButton;
    
    
    /** The grid pane holding all the textfields and labels */
    @FXML
    private GridPane gridPane;
    
    
    /**
     * Called when a product is added. Returns alert messages if data entered is insufficient, incorrect or contains duplicate values.
     */
    @FXML
    private void addProduct() {
    	if(productNameField.getText().trim().equals("") || productPriceField.getText().trim().equals("") || productLocationField.getText().trim().equals(""))	{
    		Platform.runLater(() -> {
    	        Alert dialog = new Alert(AlertType.ERROR, bundle.getString("fillAll"), ButtonType.OK);
    	        dialog.showAndWait();
    	    });
    		
    	} else	{
    		Product product = new Product ("123", productNameField.getText(), Double.parseDouble(productPriceField.getText()), productDescriptionField.getText(), 0, productLocationField.getText());
    		if(dao.addProduct(product) != null)	{
        		Platform.runLater(() -> {
        	        Alert dialog = new Alert(AlertType.INFORMATION, bundle.getString("addSuccessful"), ButtonType.OK);
        	        dialog.showAndWait();
        	        clear();
        	    });
        	} else	{
        		Platform.runLater(() -> {
        	        Alert dialog = new Alert(AlertType.ERROR, bundle.getString("addError"), ButtonType.OK);
        	        dialog.showAndWait();
        	    });
        	}

    	}
    }


    
    /**
     * Clears all the text fields.
     */
    private void clear() {
    	for (Node node : gridPane.getChildren()) {
    	    if (node instanceof TextField) {
    	        ((TextField)node).setText("");
    	    } else if (node instanceof TextArea)	{
    	    	((TextArea)node).setText("");
    	    }
    	}
	}


	/**
	 * Switches to product management window.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
    private void switchToProductManagementWindow(ActionEvent event) throws IOException {
    	Parent mainViewParent = FXMLLoader.load(getClass().getResource("productManagement.fxml"));
        Scene newProductViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(newProductViewScene);
        window.show();
    }
    

}
