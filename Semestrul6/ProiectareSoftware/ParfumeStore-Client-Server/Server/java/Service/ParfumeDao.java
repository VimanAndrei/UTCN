package Service;

import Connection.ConnectionFactory;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParfumeDao {
    private String table = "parfumes";
    protected Logger LOGGER = Logger.getLogger(table);

    private String createFindAllQuery() {
        StringBuilder interogare = new StringBuilder();
        interogare.append("SELECT ");
        interogare.append(" * ");
        interogare.append(" FROM ");
        interogare.append(table);
        return interogare.toString();
    }

    public String findAllParfumes() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String interogare = createFindAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(interogare);
            resultSet = statement.executeQuery();
            String parfumeStoreList = "";
            while (resultSet.next()) {
                String storeName = resultSet.getObject("storname").toString();
                String parfumeName = resultSet.getObject("parfumename").toString();
                String manufacturerName = resultSet.getObject("manufacturername").toString();
                String numberOfCopies = resultSet.getObject("numberofcopies").toString();
                String barCode = resultSet.getObject("barcode").toString();
                String price = resultSet.getObject("price").toString();
                String parfumeAmount = resultSet.getObject("parfumeamount").toString();
                String numberOfSoldCopies = resultSet.getObject("numberofsoldcopies").toString();

                String parfumeStore = storeName + " " + parfumeName + " " + manufacturerName + " " + numberOfCopies + " " + barCode +
                        " " + price + " " + parfumeAmount + " " + numberOfSoldCopies;
                parfumeStoreList += parfumeStore + " ";
            }

            return parfumeStoreList;

        } catch (SQLException throwables) {
            LOGGER.log(Level.WARNING, table + " DAO:findAll " + throwables.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;

    }

    public void insert (String[] list) {
        Connection connection = null;
        PreparedStatement statement = null;
        String interogare = "insert into parfumes value ( '";

        interogare = interogare + list[1] + "', '";
        interogare = interogare + list[2] + "', '";
        interogare = interogare + list[3] + "', ";
        interogare = interogare + list[4] + ", ";
        interogare = interogare + list[5] + ", ";
        interogare = interogare + list[6] + ",";
        interogare = interogare + list[7] + ",";
        interogare = interogare + list[8] + ")";

        connection = ConnectionFactory.getConnection();

        try {
            statement = connection.prepareStatement(interogare);
            statement.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(new JFrame(), "DAO: INSERT ERROR in class: UserDao " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public void delete(String selectedStoreName, String selectedParfumeName, String selectedManufacturerName, String selectedParfumeAmount) {
        Connection connection = null;
        PreparedStatement statement = null;
        String interogare = "delete from parfumes where storname = '"+selectedStoreName;
        interogare=interogare+"' and parfumename='"+ selectedParfumeName;
        interogare=interogare+"' and manufacturername='"+ selectedManufacturerName;
        interogare=interogare+"' and parfumeamount="+selectedParfumeAmount;

        connection = ConnectionFactory.getConnection();
        try {
            statement = connection.prepareStatement(interogare);
            statement.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(new JFrame(),  "DAO: DELETE ERROR in class: ParfumeDao " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public String filterByStoreName(String sName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String interogare = createFindAllQuery()+" WHERE storname='"+sName+"'ORDER BY price, parfumename ASC";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(interogare);
            resultSet = statement.executeQuery();
            String parfumeStoreList = "";
            while (resultSet.next()) {
                String storeName = resultSet.getObject("storname").toString();
                String parfumeName = resultSet.getObject("parfumename").toString();
                String manufacturerName = resultSet.getObject("manufacturername").toString();
                String numberOfCopies = resultSet.getObject("numberofcopies").toString();
                String barCode = resultSet.getObject("barcode").toString();
                String price = resultSet.getObject("price").toString();
                String parfumeAmount = resultSet.getObject("parfumeamount").toString();
                String numberOfSoldCopies = resultSet.getObject("numberofsoldcopies").toString();

                String parfumeStore = storeName + " " + parfumeName + " " + manufacturerName + " " + numberOfCopies + " " + barCode +
                        " " + price + " " + parfumeAmount + " " + numberOfSoldCopies;
                parfumeStoreList += parfumeStore + " ";
            }

            return parfumeStoreList;

        } catch (SQLException throwables) {
            LOGGER.log(Level.WARNING, table + " DAO:findAll " + throwables.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;

    }
}
