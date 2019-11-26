import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private Pane paneWriteByHand, paneImportIBANFromFile;

    @FXML
    private TextField textfieldIBANInputByHand, textfieldImportIBANFromFile;

    @FXML
    private RadioButton radiobuttonWriteByHand, radiobuttonImportFromFile;

    @FXML
    private Label labelFalse, labelTrue;

    @FXML
    private Button buttonIBANWrittenByHandClear, buttonImportIBANFileCheck, buttonImportIBANFileCancel, buttonIBANWrittenByHandCheck;

    @FXML
    void isSelectedWriteByHand(ActionEvent event) {
        if (radiobuttonWriteByHand.isSelected()) {
            radiobuttonImportFromFile.setSelected(false);
        }
        paneImportIBANFromFile.setVisible(false);
        paneWriteByHand.setVisible(true);
        labelTrue.setVisible(false);
        labelFalse.setVisible(false);
    }
    @FXML
    void isSelectedImportFromFile(ActionEvent event) {
        if (radiobuttonImportFromFile.isSelected()) {
            radiobuttonWriteByHand.setSelected(false);
        }
        paneWriteByHand.setVisible(false);
        paneImportIBANFromFile.setVisible(true);
    }

    @FXML
    void onclickIBANWrittenByHandCheck(ActionEvent event) throws SQLException {
        if (!textfieldIBANInputByHand.getText().equals("")) {
            String[] array = textfieldIBANInputByHand.getText().split("");
            String countryCode = String.join("", array[0], array[1]);
            Connection connection = ConnectionToDB.init();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM u838202579_123456789.IBAN WHERE Kodas = ?");
            try {
                PreparedStatement statement = stmt;
                statement.setString(1, countryCode);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {

                    if (rs.getInt(4) == array.length) {
                        Connection connectionCountryCode1 = ConnectionToDB.init();
                        PreparedStatement stmtCountryCode1 = connectionCountryCode1.prepareStatement("SELECT Skaicius FROM u838202579_123456789.Raides WHERE Raide = ?");
                        try {
                            PreparedStatement statementCountryCode1 = stmtCountryCode1;
                            statementCountryCode1.setString(1, array[0]);
                            ResultSet rsCountryCode1 = statementCountryCode1.executeQuery();
                            if (rsCountryCode1.next()) {
                                array[0] = rsCountryCode1.getString(1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        connectionCountryCode1.close();
                        Connection connectionCountryCode2 = ConnectionToDB.init();
                        PreparedStatement stmtCountryCode2 = connectionCountryCode2.prepareStatement("SELECT Skaicius FROM u838202579_123456789.Raides WHERE Raide = ?");
                        try {
                            PreparedStatement statementCountryCode2 = stmtCountryCode2;
                            statementCountryCode2.setString(1, array[1]);
                            ResultSet rsCountryCode2 = statementCountryCode2.executeQuery();
                            if (rsCountryCode2.next()) {
                                array[1] = rsCountryCode2.getString(1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        connectionCountryCode2.close();
                        for (int i = 0; i < 4; i++) {
                            String x = array[0];
                            for (int j = 0; j < array.length - 1; ++j)
                                array[j] = array[j + 1];
                            array[array.length - 1] = x;
                        }
                        int[] arrayint = new int[array.length];
                        for (int i = 0; i < array.length; i++) {
                            arrayint[i] = Integer.parseInt(array[i]);
                        }
                        String joined = Arrays.stream(arrayint).mapToObj(String::valueOf).collect(Collectors.joining());
                        BigInteger reallyBig = new BigInteger(joined);
                        if (reallyBig.mod(BigInteger.valueOf(97)).equals(BigInteger.valueOf(1))) {
                            labelTrue.setVisible(true);
                        } else {
                            labelFalse.setVisible(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "The IBAN does not match the country's standard IBAN length.", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Bad country code provided.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            connection.close();
        } else {
            JOptionPane.showMessageDialog(null, "Please insert IBAN number.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    @FXML
    void onclickIBANWrittenByHandClear(ActionEvent event) {
        textfieldIBANInputByHand.setText("");
        labelTrue.setVisible(false);
        labelFalse.setVisible(false);
    }
    @FXML
    void onclickImportIBANFileCheck(ActionEvent event) throws SQLException, FileNotFoundException {
        List lines = new ArrayList();
        String inputfilename = textfieldImportIBANFromFile.getText().substring(0, textfieldImportIBANFromFile.getText().length() - 4);
        PrintStream out = new PrintStream(new FileOutputStream(inputfilename + ".out.txt"));
        int i;
        File file = new File(textfieldImportIBANFromFile.getText());
        ArrayList<String> IBANlist = new ArrayList<String>();
        Scanner in = new Scanner(file);
        while (in.hasNextLine()) {
            IBANlist.add(in.nextLine());
        }
        for (int a = 0; a < IBANlist.size(); a++) {
            String[] array = IBANlist.get(a).split("");
            String countryCode = String.join("", array[0], array[1]);
            Connection connection = ConnectionToDB.init();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM u838202579_123456789.IBAN WHERE Kodas = ?");
            try {
                PreparedStatement statement = stmt;
                statement.setString(1, countryCode);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    if (rs.getInt(4) == array.length) {
                        Connection connectionCountryCode1 = ConnectionToDB.init();
                        PreparedStatement stmtCountryCode1 = connectionCountryCode1.prepareStatement("SELECT Skaicius FROM u838202579_123456789.Raides WHERE Raide = ?");
                        try {
                            PreparedStatement statementCountryCode1 = stmtCountryCode1;
                            statementCountryCode1.setString(1, array[0]);
                            ResultSet rsCountryCode1 = statementCountryCode1.executeQuery();
                            if (rsCountryCode1.next()) {
                                array[0] = rsCountryCode1.getString(1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        connectionCountryCode1.close();
                        Connection connectionCountryCode2 = ConnectionToDB.init();
                        PreparedStatement stmtCountryCode2 = connectionCountryCode2.prepareStatement("SELECT Skaicius FROM u838202579_123456789.Raides WHERE Raide = ?");
                        try {
                            PreparedStatement statementCountryCode2 = stmtCountryCode2;
                            statementCountryCode2.setString(1, array[1]);
                            ResultSet rsCountryCode2 = statementCountryCode2.executeQuery();
                            if (rsCountryCode2.next()) {
                                array[1] = rsCountryCode2.getString(1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        connectionCountryCode2.close();
                        //CHECKING IBAN kk NUMBER
                        for (int numberOfK = 0; numberOfK < 4; numberOfK++) {
                            // Rotate array by 1.
                            String x = array[0];
                            for (int j = 0; j < array.length - 1; ++j)
                                array[j] = array[j + 1];
                            array[array.length - 1] = x;
                        }
                        int[] arrayint = new int[array.length];

                        for (int iasd = 0; iasd < array.length; iasd++) {
                            arrayint[iasd] = Integer.parseInt(array[iasd]);
                        }
                        System.out.println(" ");
                        String joined = Arrays.stream(arrayint).mapToObj(String::valueOf).collect(Collectors.joining());
                        System.out.print(joined);
                        BigInteger reallyBig = new BigInteger(joined);
                        if (reallyBig.mod(BigInteger.valueOf(97)).equals(BigInteger.valueOf(1))) {
                            out.println(IBANlist.get(a) + "; True.");
                        } else {
                            out.println(IBANlist.get(a) + "; False.");
                        }
                    } else {
                        out.println(IBANlist.get(a) + "; The IBAN does not match the country's standard IBAN length.");
                    }
                } else {
                    out.println(IBANlist.get(a) + "; Bad country code provided.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            connection.close();
        }
    }

    @FXML
    void onclickImportIBANFileCancel(ActionEvent event) {
        textfieldImportIBANFromFile.setText("");
    }
}

