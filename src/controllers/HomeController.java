/*
 * Author: Voldymyr Flonts
 * 3rd course 6th semester
 * Methods of developing a graphical interface
 */
package controllers;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import utils.ConnectionUtil;


public class HomeController implements Initializable {

    @FXML
    private TextField txtTenderID;
    @FXML
    private TextField txtTitle;
    @FXML
    private DatePicker txtStartDate;
    @FXML
    private DatePicker txtEndDate;
    @FXML
    private TextField txtAmount;
    @FXML
    private ComboBox<String> txtCurrency;
    @FXML
    private Button btnSave;
    @FXML
    Label lblStatus;

    @FXML
    TableView tblData;

    /**
     * Initializes the controller class.
     */
    PreparedStatement preparedStatement;
    Connection connection;

    public HomeController() {
        connection = (Connection) ConnectionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // init currecny values
        txtCurrency.getItems().addAll("UAH", "USD", "EUR");
        txtCurrency.getSelectionModel().select("UAH");
        fetColumnList();
        fetRowList();

    }

    @FXML
    private void HandleEvents(MouseEvent event) {
        //check if not empty
        if (txtTenderID.getText().isEmpty() || txtTitle.getText().isEmpty() || 
                txtAmount.getText().isEmpty() || txtStartDate.getValue().equals(null)) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Будь ласка введіть всі необхідні дані");
        } else {
            saveData();
        }

    }

    private void clearFields() {
        txtTenderID.clear();
        txtTitle.clear();
        txtAmount.clear();
    }

    private String saveData() {

        try {
            String st = "INSERT INTO tenders (tenderID, title, startDate, endDate, amount, currency) VALUES (?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, txtTenderID.getText());
            preparedStatement.setString(2, txtTitle.getText());
            preparedStatement.setString(3, txtStartDate.getValue().toString());
            preparedStatement.setString(4, txtStartDate.getValue().toString());
            preparedStatement.setString(5, txtAmount.getText());
            preparedStatement.setString(6, txtCurrency.getValue().toString());

            preparedStatement.executeUpdate();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Added Successfully");

            fetRowList();
            //clear fields
            clearFields();
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());
            return "Exception";
        }
    }

    private ObservableList<ObservableList> data;
    String SQL = "SELECT * FROM tenders";

    //only fetch columns
    private void fetColumnList() {

        try {
            ResultSet rs = connection.createStatement().executeQuery(SQL);

            //SQL FOR SELECTING ALL OF CUSTOMER
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tblData.getColumns().removeAll(col);
                tblData.getColumns().addAll(col);

                System.out.println("Column [" + i + "] ");

            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }

    //fetches rows and data from the list
    private void fetRowList() {
        data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery(SQL);

            while (rs.next()) {
                //Iterate Row
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            tblData.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
