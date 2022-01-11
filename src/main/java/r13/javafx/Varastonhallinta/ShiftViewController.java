package r13.javafx.Varastonhallinta;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Shift;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.User;
import r13.javafx.Varastonhallinta.models.dao.ShiftAccessObject;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The Class ShiftViewController.
 * Controller for shifts.fxml.
 * Shows the user work days in a table view.
 * @author Olli Kolkki, Severi Reivinen, Juuso Lahtinen
 */
public class ShiftViewController implements Initializable {
	
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

    /** The from date. */
    @FXML
    private DatePicker fromDate;

    /** The till date. */
    @FXML
    private DatePicker tillDate;

    /** The shift table. */
    @FXML
    private TableView<User> shiftTable;

    /** The employee col. */
    @FXML
    private TableColumn<User, String> employeeCol;

    /**
     * Open new shift creation window.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void openNewShift(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newShift.fxml"), bundle);

        Stage stage = new Stage();
        stage.setTitle(bundle.getString("newShiftTitle"));
        stage.setScene(new Scene(loader.load()));
        // Pass controller if needed
        //SingleOrderViewController controller = loader.getController();
        //controller.initData(orderTable.getSelectionModel().getSelectedItem());
        stage.show();
    }

    
    /**
     * Updates the shifts view with possibly new shifts.
     *
     * @param event the event
     */
    @FXML
    void updateShifts(ActionEvent event) {
        LocalDate newStart = fromDate.getValue();
        LocalDate newTill = tillDate.getValue();

        // Check if there are too many days
        if (!tooManyDays(newStart, newTill)) {


            // Clear old values
            int dayCount = (int) ChronoUnit.DAYS.between(newStart, newTill);
            shiftTable.getItems().clear();
            shiftTable.getColumns().remove(1, shiftTable.getColumns().size());
            columns.clear();

            // Create new columns
            for (int i = 0; i < dayCount; i++) {
                columns.add(new TableColumn<>(newStart.plusDays(i).toString()));
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
        } else {
            Platform.runLater(() -> {
            	Alert dialog = new Alert(Alert.AlertType.ERROR, bundle.getString("tooManyDaysTxt") + " " + MAX_DAYS + " " + bundle.getString("tooManyDaysTxt2"), ButtonType.OK);
                dialog.setTitle(bundle.getString("tooManyDaysTitle"));
                dialog.showAndWait();
            });
        }
    }


    /**
     * Initialize.
     *
     * @param location the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Default date values
        LocalDate now = LocalDate.now();
        LocalDate weekFromNow = LocalDate.now().plusDays(MAX_DAYS);
        fromDate.setValue(now);
        tillDate.setValue(weekFromNow);

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
     * Sets a maximum value of days shown in table view.
     *
     * @param startDate the start date
     * @param tillDate the till date
     * @return true, if successful
     */
    private boolean tooManyDays(LocalDate startDate, LocalDate tillDate) {
        if (ChronoUnit.DAYS.between(startDate, tillDate) > MAX_DAYS) {
            return true;
        }
        return false;
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