package r13.javafx.Varastonhallinta;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.dao.OrderAccessObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Class OrderController.
 * Controller for orders.fxml
 * Opens up the order view.
 * Ability to look up a single order.
 * @author Olli Kolkki, Severi Reivinen, Juuso Lahtinen
 */
public class OrderController implements Initializable {
	
	/** The bundle. */
	ResourceBundle bundle = Singleton.getInstance().getBundle();	

    /** The dao. */
    private OrderAccessObject dao = new OrderAccessObject();

    /** The search bar. */
    @FXML
    private TextField searchBar;

    /** The open order. */
    @FXML
    private Button openOrder;

    /** The order table. */
    @FXML
    private TableView<Order> orderTable;

    /** The orderid col. */
    @FXML
    private TableColumn<Order, String> orderidCol;

    /** The customer col. */
    @FXML
    private TableColumn<Order, String> customerCol;

    /** The date col. */
    @FXML
    private TableColumn<Order, String> dateCol;

    /** The status col. */
    @FXML
    private TableColumn<Order, String> statusCol;

    /**
     * Open a single order to be viewed.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    private void openOrder() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("singleOrder.fxml"), bundle);

        Stage stage = new Stage();
        stage.setTitle(bundle.getString("manageOrderTitle"));
        stage.setScene(new Scene(loader.load(), 800, 600));

        // Pass selected order
        SingleOrderController controller = loader.getController();
        controller.initData(orderTable.getSelectionModel().getSelectedItem());

        stage.show();
    }

    /**
     * Initialize.
     *
     * @param location the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderidCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        customerCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getFirstName() + " " + cellData.getValue().getCustomer().getLastName()));
        dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderedAt().toString()));
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderStatusCode().getDescription()));

        // Add order filtering
        FilteredList<Order> filteredData = new FilteredList(FXCollections.observableArrayList(dao.getOrders()), p -> true);
        searchBar.textProperty().addListener((observable, oldVal, newVal) -> {
            filteredData.setPredicate(order -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newVal.toLowerCase();

                if (order.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (order.getCustomer().getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (order.getCustomer().getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (order.getOrderedAt().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (order.getOrderStatusCode().getDescription().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Order> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(orderTable.comparatorProperty());
        orderTable.setItems(sortedData);
    }
}
