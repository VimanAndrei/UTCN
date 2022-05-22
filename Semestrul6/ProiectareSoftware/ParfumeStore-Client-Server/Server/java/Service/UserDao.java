package Service;

import Connection.ConnectionFactory;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {
    private String table = "users";
    protected Logger LOGGER = Logger.getLogger(table);

    private String createFindAllQuery() {
        StringBuilder interogare = new StringBuilder();
        interogare.append("SELECT ");
        interogare.append(" * ");
        interogare.append(" FROM ");
        interogare.append(table);
        return interogare.toString();
    }

    public String findAllUsers() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String interogare = createFindAllQuery();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(interogare);
            resultSet = statement.executeQuery();

           String list = "";
            while (resultSet.next()){
                String username = resultSet.getObject("username").toString();
                String password = resultSet.getObject("password").toString();
                String role = resultSet.getObject("role").toString();
                String user = username+" "+password+" "+role;
                list+=user+" ";
            }
            return list;

        } catch (SQLException throwables) {
            LOGGER.log(Level.WARNING, table + " DAO:findAll " + throwables.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    public String filterUsers(String role){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String interogare = createFindAllQuery()+" WHERE role = '" + role+"'";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(interogare);
            resultSet = statement.executeQuery();

            String list = "";
            while (resultSet.next()){
                String username = resultSet.getObject("username").toString();
                String password = resultSet.getObject("password").toString();
                String r = resultSet.getObject("role").toString();
                String user = username+" "+password+" "+role;
                list+=user+" ";
            }
            return list;

        } catch (SQLException throwables) {
            LOGGER.log(Level.WARNING, table + " DAO:findAll " + throwables.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    public void insert(String username, String password, String role) {
        Connection connection = null;
        PreparedStatement statement = null;
        String interogare = "insert into users value ( '";

        interogare += username;
        interogare+="','";
        interogare+= password;
        interogare+="','";
        interogare+= role;

        interogare += "')";
        connection = ConnectionFactory.getConnection();
        try {
            statement = connection.prepareStatement(interogare);
            statement.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(new JFrame(),  "DAO: INSERT ERROR in class: UserDao " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

    }

    public void delete(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        String interogare = "delete from users where username = '";

        interogare += username;
        interogare+="'";
        connection = ConnectionFactory.getConnection();
        try {
            statement = connection.prepareStatement(interogare);
            statement.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(new JFrame(),  "DAO: DELETE ERROR in class: UserDao " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

    }

}
