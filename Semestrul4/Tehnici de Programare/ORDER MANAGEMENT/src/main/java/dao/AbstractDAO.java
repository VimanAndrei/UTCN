package dao;

import connection.ConnectionFactory;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field) {
        StringBuilder interogare = new StringBuilder();
        interogare.append("SELECT ");
        interogare.append(" * ");
        interogare.append(" FROM ");
        interogare.append(type.getSimpleName());
        interogare.append(" WHERE " + field + " =?");
        return interogare.toString();
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }



    public DataTable getDataForTable(List<T> dataTable) {
        Field[] fields = type.getDeclaredFields();
        Object[][] table = new Object[dataTable.size()][fields.length];
        Object[] antet = new Object[fields.length];


        for (int i = 0; i < fields.length; i++) {
            antet[i] = fields[i].getName().toUpperCase();
        }
        try {
            for (int i = 0; i < dataTable.size(); i++) {
                 for (int j = 0; j < fields.length; j++) {
                    fields[j].setAccessible(true);

                    table[i][j] = fields[j].get(dataTable.get(i));

                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:getDataForTable " + e.getMessage());
            return null;
        }
        DataTable dT=new DataTable(antet,table);
        return dT;
    }

    private String createFindAllQuery() {
        StringBuilder interogare = new StringBuilder();
        interogare.append("SELECT ");
        interogare.append(" * ");
        interogare.append(" FROM ");
        interogare.append(type.getSimpleName());
        return interogare.toString();
    }

    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String interogare = createFindAllQuery();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(interogare);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);


        } catch (SQLException throwables) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + throwables.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }


    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String createInsertQuery() {
        StringBuilder interogare = new StringBuilder();
        interogare.append("INSERT ");
        interogare.append("INTO ");
        interogare.append(type.getSimpleName());
        interogare.append(" VALUES (");
        return interogare.toString();
    }
    public String verifInsert(Field field,Object value){
        String interogare="";
        if (value instanceof String) {

            interogare = interogare + "  '" + value.toString() + "',";

        } else {
            interogare = interogare + value.toString() + ",";
        }
        return interogare;
    }
    public void insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String interogare = createInsertQuery();

            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    interogare=interogare+verifInsert(field,field.get(t));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            interogare = interogare.substring(0, interogare.length() - 1) + ")";
            connection = ConnectionFactory.getConnection();
        try {
            statement = connection.prepareStatement(interogare);
            statement.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(new JFrame(),  "DAO: INSERT ERROR in class: "+type.getName()+" " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

    }

    private String createUpdateQuery() {
        StringBuilder interogare = new StringBuilder();
        interogare.append("UPDATE ");
        interogare.append(type.getSimpleName());
        interogare.append(" SET ");
        return interogare.toString();
    }

    public String verifUpdate(Field field,Object value){
        String interogare="";
        if (value instanceof String) {
            interogare = interogare + field.getName() + "='" + value.toString() + "',";
        } else {
            interogare = interogare + field.getName() + "=" + value.toString() + ",";
        }
        return interogare;
    }


    public void update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String interogare = createUpdateQuery();
        int id = 0;

            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (field.getName().equals("id")) {

                             id = Integer.parseInt(field.get(t).toString());

                     } else {
                            interogare=interogare+verifUpdate(field,field.get(t));
                     }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            interogare = interogare.substring(0, interogare.length() - 1) + " WHERE id=" + id;
            connection = ConnectionFactory.getConnection();
            try {
                statement = connection.prepareStatement(interogare);
                statement.execute();
            } catch ( SQLException e) {
                JOptionPane.showMessageDialog(new JFrame(),  "DAO: UPDATE ERROR in class: "+type.getName()+" " + e.getMessage());
            } finally {
                    ConnectionFactory.close(statement);
                    ConnectionFactory.close(connection);
            }
    }

    private String createDeleteQuery(String field) {
        StringBuilder interogare = new StringBuilder();
        interogare.append("DELETE ");
        interogare.append(" FROM ");
        interogare.append(type.getSimpleName());
        interogare.append(" WHERE " + field + " = ");
        return interogare.toString();
    }

    public void delete(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String interogare = createDeleteQuery("id");
        int id = 0;


            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getName().equals("id")) {
                    try {
                        id = Integer.parseInt( field.get(t).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            interogare = interogare + id;
            connection = ConnectionFactory.getConnection();
             try {
                    statement = connection.prepareStatement(interogare);
                    statement.execute();


            } catch ( SQLException e) {
                     JOptionPane.showMessageDialog(new JFrame(),  "DAO: DELETE ERROR in class: "+type.getName()+" " + e.getMessage());
            } finally {
                ConnectionFactory.close(statement);
                ConnectionFactory.close(connection);
            }
    }
}

