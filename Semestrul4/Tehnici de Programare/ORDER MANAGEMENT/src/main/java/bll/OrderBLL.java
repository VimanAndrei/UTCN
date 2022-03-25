package bll;

import dao.DataTable;
import dao.OrderDAO;
import model.Clients;
import model.Orders;
import model.Products;

import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {
    private OrderDAO order;

    public OrderBLL(){
        this.order = new OrderDAO();
    }
    public Orders findClientById(int id){
        Orders o = order.findById(id);
        if(o == null){
            throw new NoSuchElementException("The student with id =" + id + " was not found!");
        }
        return o;
    }

    public void insert(Orders o){
        order.insert(o);

    }

    public void update(Orders o){
        order.update(o);

    }


    public void delete(Orders o){
        order.delete(o);
    }

    public List<Orders> selectAll(){

        return order.findAll();
    }


    public DataTable getDataForTable(){
        return order.getDataForTable(order.findAll());
    }
}
