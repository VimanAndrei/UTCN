package Model;

import Model.Connection.ConnectionFactory;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParfumeDAO {
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

    public List<ParfumeStore> findAllParfumes() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String interogare = createFindAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(interogare);
            resultSet = statement.executeQuery();

            List<ParfumeStore> parfumeStoreList = new ArrayList<>();
            while (resultSet.next()) {
                String storeName = resultSet.getObject("storname").toString();
                String parfumeName = resultSet.getObject("parfumename").toString();
                String manufacturerName = resultSet.getObject("manufacturername").toString();
                String numberOfCopies = resultSet.getObject("numberofcopies").toString();
                String barCode = resultSet.getObject("barcode").toString();
                String price = resultSet.getObject("price").toString();
                String parfumeAmount = resultSet.getObject("parfumeamount").toString();
                String numberOfSoldCopies = resultSet.getObject("numberofsoldcopies").toString();
                ParfumeInfo parfumeInfo = new ParfumeInfoBuilder().setManufacturerName(manufacturerName).setNumberOfCopies(Integer.parseInt(numberOfCopies)).setBarCode( Integer.parseInt(barCode)).setPrice( Double.parseDouble(price)).setParfumeAmount( Integer.parseInt(parfumeAmount)).setNumberOfSoldCopies( Integer.parseInt(numberOfSoldCopies)).build();
                Parfume parfume = new ParfumeBuilder().setParfumeName(parfumeName).setParfumeInfo(parfumeInfo).build();

                boolean gasit = false;
                int index = -1;
                for (ParfumeStore ps : parfumeStoreList) {
                    if (ps.getStoreName().equals(storeName)) {
                        gasit = true;
                        index = parfumeStoreList.indexOf(ps);
                    }
                }
                if (!gasit) {
                    ParfumeStore parfumeStore = new ParfumeStore(storeName);
                    parfumeStore.addParfumeToStore(parfume);
                    parfumeStoreList.add(parfumeStore);
                } else {
                    parfumeStoreList.get(index).addParfumeToStore(parfume);
                }
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

    public void insert (ParfumeStore parfumeStore) {
        Connection connection = null;
        PreparedStatement statement = null;
        String interogare = "insert into parfumes value ( '";

        interogare = interogare + parfumeStore.getStoreName() + "', '";
        interogare = interogare + parfumeStore.getParfumes().get(0).getParfumeName() + "', '";
        interogare = interogare + parfumeStore.getParfumes().get(0).getParfumeInfo().getManufacturerName() + "', ";
        interogare = interogare + parfumeStore.getParfumes().get(0).getParfumeInfo().getNumberOfCopies() + ", ";
        interogare = interogare + parfumeStore.getParfumes().get(0).getParfumeInfo().getBarCode() + ", ";
        interogare = interogare + parfumeStore.getParfumes().get(0).getParfumeInfo().getPrice() + ",";
        interogare = interogare + parfumeStore.getParfumes().get(0).getParfumeInfo().getParfumeAmount() + ",";
        interogare = interogare + parfumeStore.getParfumes().get(0).getParfumeInfo().getNumberOfSoldCopies() + ")";

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

    protected void delete(String selectedStoreName, String selectedParfumeName, String selectedManufacturerName, String selectedParfumeAmount) {
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
}
