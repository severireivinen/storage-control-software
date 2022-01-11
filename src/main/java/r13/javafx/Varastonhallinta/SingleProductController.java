package r13.javafx.Varastonhallinta;

import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import r13.javafx.Varastonhallinta.models.OrderItem;
import r13.javafx.Varastonhallinta.models.Product;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.dao.OrderItemAccessObject;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;

/**
 * Controller for the details view of a single product. Contains product editing functions and data of the products that are a part of an order.
 * @author Olli Kolkki
 */
public class SingleProductController {
	
	/** The bundle used for localization. */
	ResourceBundle bundle = Singleton.getInstance().getBundle();

    /** The the product of which details are shown. */
    private Product selectedProduct;

    /** The database access object for products */
    private ProductAccessObject dao = new ProductAccessObject();
    
    /** The database access object for order items. */
    private OrderItemAccessObject orderItemDao = new OrderItemAccessObject();
    
    /** The search field. */
    @FXML
    private TextField searchField;
    
    /** The open edit btn. */
    @FXML
    private Button openEditBtn;
    
    /** The search btn. */
    @FXML
    private Button searchBtn;

    /** The back btn. */
    @FXML
    private Button backBtn;
    
    /** The cancel btn. */
    @FXML
    private Button cancelBtn;

    /** The save btn. */
    @FXML
    private Button saveBtn;
    
    /** The edit btn. */
    @FXML
    private Button editBtn;
    
    /** The reset btn. */
    @FXML
    private Button resetBtn;
    
    /** The order item table. */
    @FXML
    private TableView<OrderItem> orderItemTable;

    /** The order id. */
    @FXML
    private TableColumn<OrderItem, String> orderId;

    /** The quantity. */
    @FXML
    private TableColumn<OrderItem, Integer> quantity;

    /** The price. */
    @FXML
    private TableColumn<OrderItem, Double> price;


    /** The product tab back btn. */
    @FXML
    private Button productTabBackBtn;

    /** The id label. */
    @FXML
    private Label idLabel;

    /** The name label. */
    @FXML
    private Label nameLabel;

    /** The description label. */
    @FXML
    private Label descriptionLabel;

    /** The location label. */
    @FXML
    private Label locationLabel;

    /** The price label. */
    @FXML
    private Label priceLabel;

    /** The stock label. */
    @FXML
    private Label stockLabel;

    /** The general tab back btn. */
    @FXML
    private Button generalTabBackBtn;
    
    /** The name text field. */
    @FXML
    private TextField nameTextField;
    
    /** The description text field. */
    @FXML
    private TextField descriptionTextField;
    
    /** The location text field. */
    @FXML
    private TextField locationTextField;
    
    /** The price text field. */
    @FXML
    private TextField priceTextField;
    
    /** The stock text field. */
    @FXML
    private TextField stockTextField;

    /**
     * Initializes the JavaFX table for data of this product as an item in orders
     */
    public void initializeTable() {
        orderId.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("id"));
        quantity.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<OrderItem, Double>("price"));
        FilteredList<OrderItem> filteredData = new FilteredList<>(getOrderItems(), p -> true);
		SortedList<OrderItem> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(orderItemTable.comparatorProperty());
		orderItemTable.setItems(filteredData);	
		orderItemTable.setPlaceholder(new Label(bundle.getString("orderTablePlaceholder")));
		
    }

    /**
     * Gets the order items for the table
     *
     * @return the order items
     */
    private ObservableList<OrderItem> getOrderItems() {
    	if(selectedProduct != null) {
	        ObservableList<OrderItem> orderItems = FXCollections.observableArrayList(orderItemDao.getOrderItemsByProductId(selectedProduct.getId()));
	        return orderItems;
    	}
    	else	{
    		return null;
    	}
    }

    /**
     * Initializes the details of the product and defines element visibility.
     */
    private void initializeDetails() {
    	
    	saveBtn.setVisible(false);
    	cancelBtn.setVisible(false);
    	resetBtn.setVisible(false);
    	editBtn.setVisible(true);
    	
    	nameTextField.setVisible(false);
    	descriptionTextField.setVisible(false);
    	locationTextField.setVisible(false);
    	priceTextField.setVisible(false);
    	stockTextField.setVisible(false);
    	
    	UnaryOperator<Change> filter = change -> {
    	    String text = change.getText();

    	    if (text.matches("\\d{0,9}([\\.]\\d{0,9})?")) {
    	        return change;
    	    }

    	    return null;
    	};
    	TextFormatter<String> stockFormatter = new TextFormatter<>(filter);
    	TextFormatter<String> priceFormatter = new TextFormatter<>(filter);
    	stockTextField.setTextFormatter(stockFormatter);
    	priceTextField.setTextFormatter(priceFormatter);


    	
    	if(selectedProduct != null) {
    		idLabel.setText(selectedProduct.getId());
            nameLabel.setText(selectedProduct.getName());
            descriptionLabel.setText(selectedProduct.getDescription());
            locationLabel.setText(selectedProduct.getLocation());
            priceLabel.setText(Double.toString(selectedProduct.getPrice()));
            stockLabel.setText(Integer.toString(selectedProduct.getStock()));
    	}
    	
        
    }
   

    


    /**
     * Used to receive the selected product object from previous view.
     *
     * @param product the product
     */
    public void initData(Product product) {
        this.selectedProduct = product;
        initializeDetails();
        initializeTable();

    }

    
    /**
     * Called when edit button is pressed. Changes visibility of Textfield elements
     */
    @FXML
    public void startEdit()	{
    	if(selectedProduct != null) {
    		resetBtn.setVisible(true);
    		saveBtn.setVisible(true);
        	cancelBtn.setVisible(true);
        	editBtn.setVisible(false);
        	

    		nameTextField.setVisible(true);
        	descriptionTextField.setVisible(true);
        	locationTextField.setVisible(true);
        	priceTextField.setVisible(true);
        	stockTextField.setVisible(true);
        	
        	nameTextField.setText(selectedProduct.getName());
        	descriptionTextField.setText(selectedProduct.getDescription());
        	locationTextField.setText(selectedProduct.getLocation());
        	priceTextField.setText(Double.toString(selectedProduct.getPrice()));
        	stockTextField.setText(Integer.toString(selectedProduct.getStock()));
    	}
    }
    
    /**
     * Saves edited data and displays appropriate alert windows.
     */
    @FXML
    public void saveEdit()	{
    	
    	Product product = new Product(selectedProduct.getId(), nameTextField.getText(), Double.parseDouble(priceTextField.getText()), descriptionTextField.getText(), Integer.parseInt(stockTextField.getText()), locationTextField.getText());
    	Product editedProduct = dao.editProduct(product);
    	nameTextField.setVisible(false);
    	descriptionTextField.setVisible(false);
    	locationTextField.setVisible(false);
    	priceTextField.setVisible(false);
    	stockTextField.setVisible(false);
    	initData(dao.getProduct(selectedProduct.getId()));
    	if(dao.editProduct(product) != null)	{
    		Platform.runLater(() -> {
    	        Alert dialog = new Alert(AlertType.INFORMATION, bundle.getString("editSuccessfulTxt"), ButtonType.OK);
    	        dialog.showAndWait();
    	    });
    	} else	{
    		Platform.runLater(() -> {
    	        Alert dialog = new Alert(AlertType.INFORMATION, bundle.getString("editFailedTxt"), ButtonType.OK);
    	        dialog.showAndWait();
    	    });
    		
    	}
    }
    
    /**
     * Called when editing is cancelled.
     */
    @FXML
    public void cancelEdit()	{

    	nameTextField.setVisible(false);
    	descriptionTextField.setVisible(false);
    	locationTextField.setVisible(false);
    	priceTextField.setVisible(false);
    	stockTextField.setVisible(false);
    	saveBtn.setVisible(false);
    	cancelBtn.setVisible(false);
    	resetBtn.setVisible(false);
    	editBtn.setVisible(true);

    }
    
    /**
     * Called when reset button is pressed.
     */
    @FXML
	public void resetEdit()	{
		
    	nameTextField.setText(selectedProduct.getName());
    	descriptionTextField.setText(selectedProduct.getDescription());
    	locationTextField.setText(selectedProduct.getLocation());
    	priceTextField.setText(Double.toString(selectedProduct.getPrice()));
    	stockTextField.setText(Double.toString(selectedProduct.getStock()));
	}

}
