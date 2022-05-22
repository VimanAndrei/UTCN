package Model.User;

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

public class UserDAO {
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

    public List<User> filterUsers(String role){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String interogare = createFindAllQuery()+" WHERE role = '" + role+"'";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(interogare);
            resultSet = statement.executeQuery();

            List<User> list = new ArrayList<>();
            while (resultSet.next()){
                String username = resultSet.getObject("username").toString();
                String password = resultSet.getObject("password").toString();
                String r = resultSet.getObject("role").toString();
                User user = new UserBuilder().setUsername(username).setPassword(password).setRole(r).build();
                list.add(user);
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


    public List<User> findAllUsers() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String interogare = createFindAllQuery();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(interogare);
            resultSet = statement.executeQuery();

            List<User> list = new ArrayList<>();
            while (resultSet.next()){
                String username = resultSet.getObject("username").toString();
                String password = resultSet.getObject("password").toString();
                String role = resultSet.getObject("role").toString();
                User user = new UserBuilder().setUsername(username).setPassword(password).setRole(role).build();
                list.add(user);
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

    public void insert(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        String interogare = "insert into users value ( '";

        interogare += user.getUsername();
        interogare+="','";
        interogare+= user.getPassword();
        interogare+="','";
        interogare+= user.getRole();

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
