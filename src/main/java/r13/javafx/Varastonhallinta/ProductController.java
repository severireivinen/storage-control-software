package r13.javafx.Varastonhallinta;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Product;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;

import java.io.IOException;
import java.util.ResourceBundle;


/**
 * Controller for the product management view, containing a table of all the products.
 * @author Olli Kolkki
 */
public class ProductController {
	
	/** The bundle used for localization. */
	ResourceBundle bundle = Singleton.getInstance().getBundle();	

    /** The data access object used for accessing the Product table in the database. */
    private ProductAccessObject dao = new ProductAccessObject();

    /** The add btn. */
    @FXML
    private Button addBtn;
    
    
    /** The back button. */
    @FXML
    private Button backButton;
    
    /** The refresh btn. */
    @FXML
    private Button refreshBtn;
    
    /** The new product button. */
    @FXML
    private Button newProductButton;
    
    /** The delete button. */
    @FXML
    private Button deleteButton;

    /** The product table. */
    @FXML
    private TableView<Product> productTable;

    /** The table id. */
    @FXML
    private TableColumn<Product, String> tableId;

    /** The table name. */
    @FXML
    private TableColumn<Product, String> tableName;

    /** The table price. */
    @FXML
    private TableColumn<Product, Double> tablePrice;

    /** The table description. */
    @FXML
    private TableColumn<Product, String> tableDescription;

    /** The table stock. */
    @FXML
    private TableColumn<Product, Integer> tableStock;

    /** The table location. */
    @FXML
    private TableColumn<Product, String> tableLocation;

    /** The table category. */
    @FXML
    private TableColumn<Product, String> tableCategory;

    /** The filter field. */
    @FXML
    private TextField filterField;
    

    
    
    /**
     * Initializes the table with all products and filters them if search field is used
     */
    @FXML
    public void initialize() {
        tableId.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        tableName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        tablePrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        tableDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        tableStock.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        tableLocation.setCellValueFactory(new PropertyValueFactory<Product, String>("location")); 
        productTable.setPlaceholder(new Label(bundle.getString("tablePlaceholder")));
        
        FilteredList<Product> filteredData = new FilteredList<>(getProducts(), p -> true);
        
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(product -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (product.getDescription().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (product.getLocation().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});
		
		SortedList<Product> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(productTable.comparatorProperty());

		productTable.setItems(sortedData);
    }

    /**
     * Gets the product data from the database.
     *
     * @return List of all the products
     */
    private ObservableList<Product> getProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList(dao.getProducts());
        return products;
    }
    

    /**
     * Called when product is deleted. Controls appropriate alert windows.
     */
    @FXML
    private void deleteProduct()	{
    	
    	Product p = productTable.getSelectionModel().getSelectedItem();
    	
	    if(p != null)	{
	    	if(dao.removeProduct(p.getId()))	{
	    		Platform.runLater(() -> {
	    	        Alert dialog = new Alert(AlertType.INFORMATION, bundle.getString("removedTxt"), ButtonType.OK);
	    	        dialog.showAndWait();
	    	    });
	    	} else	{
	    		Platform.runLater(() -> {
	    	        Alert dialog = new Alert(AlertType.ERROR, bundle.getString("errorRemovingTxt"), ButtonType.OK);
	    	        dialog.showAndWait();
	    	    });
	    	}
	    	initialize();
	    } else	{
	    	Platform.runLater(() -> {
		        Alert dialog = new Alert(AlertType.ERROR, bundle.getString("productSelectTxt"), ButtonType.OK);
		        dialog.showAndWait();
		    });
	    }
    }
    

    /**
     * Changes scene to main view.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    public void changeSceneToMainView(ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        Scene productManagementViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(productManagementViewScene);
        window.show();
    }
    

    /**
     * Switches scene to new product window.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    private void switchToNewProductWindow(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("newProduct.fxml"), bundle);

        Stage stage = new Stage();

        stage.setTitle(bundle.getString("titleTxt"));
        stage.setScene(new Scene(loader.load()));



        stage.show();
        
    }
   
    /**
     * Changes scene to product details view. Gives an alert if no product is selected or selection is invalid
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    private void changeSceneToProductDetailsView() throws IOException {
        
        
        Product p = productTable.getSelectionModel().getSelectedItem();
    	
	    if(p != null)	{
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("singleProduct.fxml"), bundle);

	        Stage stage = new Stage();
	        stage.setTitle(bundle.getString("productsTitle"));
	        stage.setScene(new Scene(loader.load()));

	        SingleProductController controller = loader.getController();
	        controller.initData(productTable.getSelectionModel().getSelectedItem());

	        stage.show();
	    	
	    } else	{
	    	Platform.runLater(() -> {
		        Alert dialog = new Alert(AlertType.ERROR, bundle.getString("productErrorTxt"), ButtonType.OK);
		        dialog.showAndWait();
		    });
	    }
        
        
    }
}
