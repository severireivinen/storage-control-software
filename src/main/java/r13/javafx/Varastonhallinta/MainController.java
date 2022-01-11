package r13.javafx.Varastonhallinta;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.Shift;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.User;
import r13.javafx.Varastonhallinta.models.dao.OrderAccessObject;
import r13.javafx.Varastonhallinta.models.dao.ShiftAccessObject;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;


/**
 * The Class MainController.
 * Controller for mainWindow.fxml
 * Class contains:
 * The side bar navigation and its features to switch between tabs, without opening a new window for every scene.
 * Functionality of home-tab; shows logged in user and the user's shifts. And active orders.
 * @author Olli Kolkki, Severi Reivinen, Juuso Lahtinen
 */
public class MainController implements Initializable {


    /** The bp. */
    @FXML
    private BorderPane bp;

    /** The ap. */
    @FXML
    private AnchorPane ap;


    /** The bundle. */
    ResourceBundle bundle = Singleton.getInstance().getBundle();


    /** The shift dao. */
    private ShiftAccessObject shiftDao = new ShiftAccessObject();
    
    /** The user dao. */
    private UserAccessObject userDao = new UserAccessObject();
    
    /** The columns. */
    private List<TableColumn<User, String>> columns = new ArrayList<>();
    
    /** The username. */
    private String username = Singleton.getInstance().getUsername();
    
    /** The Constant MAX_DAYS. */
    final static int MAX_DAYS = 60;
    
    /** The shift table. */
    @FXML
    private TableView<User> shiftTable;
    
    /** The employee col. */
    @FXML
    private TableColumn<User, String> employeeCol;

    /** The dao. */

    private OrderAccessObject dao = new OrderAccessObject();
    
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

    /** The username field. */
    @FXML
    private Text usernameField;


    /**
     * Open home view
     *
     * @param event the event
     */
    @FXML
    private void home(MouseEvent event) {
        bp.setCenter(ap);
    }

    /**
     * Open order view
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    private void orders(MouseEvent event) throws IOException {
        loadPage("orders");
    }

    /**
     * Open product view
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    private void products(MouseEvent event) throws IOException {
        loadPage("products");

    }

    /**
     * Open admin view
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    private void admin(MouseEvent event) throws IOException {
        loadPage("shiftsAdmin");
    }

    /**
     * Open shift view
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    private void shifts(MouseEvent event) throws IOException {
        loadPage("shifts");
    }

    /**
     * Log out.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    private void logOut(MouseEvent event) throws IOException {
        Singleton.getInstance().setUsername(null);
        loadPage("loginscreen");
    }

    /**
     * Loads the clicked page/tab.
     *
     * @param page the page
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void loadPage(String page) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource(page + ".fxml"), bundle);
        bp.setCenter(root);
    }


    /**
     * Initialize.
     *
     * @param location the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillShiftTable();
        fillOrderTable();
        usernameField.setText(Singleton.getInstance().getUsername());
    }

    /**
     * Fill order table.
     */
    private void fillOrderTable() {

        orderidCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        customerCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getFirstName() + " " + cellData.getValue().getCustomer().getLastName()));
        dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderedAt().toString()));
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderStatusCode().getDescription()));
        FilteredList<Order> filteredData = new FilteredList(FXCollections.observableArrayList(dao.getOrders()), p -> true);
        SortedList<Order> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(orderTable.comparatorProperty());
        orderTable.setItems(sortedData);

    }


    /**
     * Fill shift table.
     */
    private void fillShiftTable() {
        //Default date values
        LocalDate now = LocalDate.now();
        LocalDate weekFromNow = LocalDate.now().plusDays(MAX_DAYS);

        //Create columns
        int dayCount = (int) ChronoUnit.DAYS.between(now, weekFromNow);
        for (int i = 0; i < dayCount; i++) {
            columns.add(new TableColumn<>(now.plusDays(i).toString()));
        }
        shiftTable.getColumns().addAll(columns);

        // Fill cells
        employeeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName()));
        for (TableColumn<User, String> tc : columns) {
            tc.setCellValueFactory(cellData -> {
                Optional<Shift> shift = cellData.getValue().getShifts().stream().filter(s -> s.getDate().equals(LocalDate.parse(tc.getText()))).findAny();
                if (shift.isPresent()) {
                    return new SimpleStringProperty(shift.get().getStart().toString() + "-" + shift.get().getEnd().toString());
                } else {
                    return new SimpleStringProperty("");
                }
            });
        }

        fetchShifts();
        shiftTable.getSelectionModel().setCellSelectionEnabled(true);
    }


    /**
     * Fetch shifts.
     */
    private void fetchShifts() {
        ObservableList<User> shifts = FXCollections.observableArrayList(userDao.getUser(username));
        shiftTable.setItems(shifts);

        shiftTable.getItems().forEach(u -> u.setShifts(shiftDao.getShiftsByUserId(u.getId())));
    }


}
