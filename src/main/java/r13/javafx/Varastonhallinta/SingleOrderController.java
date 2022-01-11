package r13.javafx.Varastonhallinta;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import r13.javafx.Varastonhallinta.models.Order;
import r13.javafx.Varastonhallinta.models.OrderItem;
import r13.javafx.Varastonhallinta.models.dao.OrderAccessObject;
import r13.javafx.Varastonhallinta.models.dao.OrderItemAccessObject;
import r13.javafx.Varastonhallinta.models.dao.ProductAccessObject;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The Class SingleOrderController.
 * Controller for singleOrder.fxml
 * Class contains necessary methods to show relevant information of an order
 * @author Olli Kolkki, Severi Reivinen, Juuso Lahtinen
 */
public class SingleOrderController {

    /** The order id. */
    @FXML
    private Label orderId;

    /** The customer name. */
    @FXML
    private Label customerName;

    /** The customer email. */
    @FXML
    private Label customerEmail;

    /** The customer phone. */
    @FXML
    private Label customerPhone;

    /** The customer address. */
    @FXML
    private Label customerAddress;

    /** The customer city. */
    @FXML
    private Label customerCity;

    /** The customer postal. */
    @FXML
    private Label customerPostal;

    /** The order status. */
    @FXML
    private Label orderStatus;

    /** The process btn. */
    @FXML
    private Button processBtn;

    /** The order items table. */
    @FXML
    private TableView<OrderItem> orderItemsTable;

    /** The order item product. */
    @FXML
    private TableColumn<OrderItem, String> orderItemProduct;

    /** The order item price. */
    @FXML
    private TableColumn<OrderItem, Double> orderItemPrice;

    /** The order item quantity. */
    @FXML
    private TableColumn<OrderItem, Integer> orderItemQuantity;

    /** The order item subtotal. */
    @FXML
    private TableColumn<OrderItem, Double> orderItemSubtotal;

    /** The order item stock. */
    @FXML
    private TableColumn<OrderItem, Integer> orderItemStock;

    /** The order total. */
    @FXML
    private Label orderTotal;

    /** The order tax. */
    @FXML
    private Label orderTax;

    /** The order shipping fee. */
    @FXML
    private Label orderShippingFee;

    /** The order subtotal. */
    @FXML
    private Label orderSubtotal;

    /**
     * Process order. Show appropriate alerts if processing cannot be done.
     *
     * @param event the event
     */
    @FXML
    void processOrder(ActionEvent event) {
        Order order = orderDao.getOrderByOrderId(selectedOrder.getId());
        AtomicBoolean invalidStock = new AtomicBoolean(false);

        if (!alreadyProcessed(order)) {
            orderItemsTable.getItems().stream().forEach(item -> {
                if (item.getQuantity() > item.getProduct().getStock()) {
                    invalidStock.set(true);
                }
            });

            if (!invalidStock.get()) {
                orderItemsTable.getItems().stream().forEach(item -> {
                    productDao.decreaseStock(item.getProduct().getId(), item.getQuantity());
                });
                orderDao.setOrderProcessed(order.getId());
                orderStatus.setText(order.getOrderStatusCode().getDescription());
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Order processed", ButtonType.OK);
                a.showAndWait();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Invalid stock", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Order already processed", ButtonType.OK);
            a.showAndWait();

        }
    }

    /** The selected order. */
    private Order selectedOrder = null;

    /** The dao. */
    private OrderItemAccessObject dao = new OrderItemAccessObject();
    
    /** The order dao. */
    private OrderAccessObject orderDao = new OrderAccessObject();
    
    /** The product dao. */
    private ProductAccessObject productDao = new ProductAccessObject();

    /**
     * Initializes the data.
     *
     * @param order the order
     */
    public void initData(Order order) {
        this.selectedOrder = order;
        orderId.setText(selectedOrder.getId());
        customerName.setText(selectedOrder.getCustomer().getFirstName() + " " + selectedOrder.getCustomer().getLastName());
        customerEmail.setText(selectedOrder.getCustomer().getEmail());
        customerPhone.setText(selectedOrder.getCustomer().getPhone());
        customerAddress.setText(selectedOrder.getCustomer().getAddress().getAddress());
        customerCity.setText(selectedOrder.getCustomer().getAddress().getCity());
        customerPostal.setText(selectedOrder.getCustomer().getAddress().getPostal());
        orderStatus.setText(selectedOrder.getOrderStatusCode().getDescription());
        populateItemsTable();
        setTotalValues();
    }

    /**
     * Gets data from database and fills tables with the data.
     */
    private void populateItemsTable() {
        orderItemProduct.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        orderItemPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProduct().getPrice()).asObject());
        orderItemQuantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        orderItemSubtotal.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        orderItemStock.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct().getStock()).asObject());

        orderItemsTable.setItems(FXCollections.observableArrayList(dao.getOrderItemsByOrderId(selectedOrder.getId())));

        // Update bg-color
        orderItemsTable.setRowFactory(row -> new TableRow<>() {
            @Override
            protected void updateItem(OrderItem item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || item.getProduct() == null) {
                    setStyle("");
                } else if (item.getProduct().getStock() < item.getQuantity()) {
                    setStyle("-fx-background-color: red;");
                } else {
                    setStyle("-fx-background-color: green;");
                }
            }
        });
    }

    /**
     * Sets the total values.
     */
    private void setTotalValues() {
        double subTotal = orderDao.getOrderByOrderId(selectedOrder.getId()).getOrderItems().stream().mapToDouble(i -> i.getProduct().getPrice()).sum();
        double tax = Math.round(((subTotal * 0.10) * 100.0) / 100.0);
        double shipping = 0.0;
        double total = subTotal - tax - shipping;
        orderTax.setText(Double.toString(tax) + "€");
        orderShippingFee.setText(Double.toString(shipping) + "€");
        orderSubtotal.setText(Double.toString(subTotal) + "€");
        orderTotal.setText(Double.toString(total) + "€");
    }

    /**
     * Already processed.
     *
     * @param o the o
     * @return the boolean
     */
    private Boolean alreadyProcessed(Order o) {
        return o.getOrderStatusCode().getDescription().equals("Order has been processed") ? true : false;
    }
}
