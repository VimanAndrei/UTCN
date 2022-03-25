package bll;

import bll.validators.NameValidator;
import dao.*;
import model.*;

import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private ClientsDAO client;

    public ClientBLL() {
        this.client = new ClientsDAO();
    }

    public Clients findClientById(int id){
        Clients c = client.findById(id);
        if(c == null){
            throw new NoSuchElementException("The student with id =" + id + " was not found!");
        }
        return c;
    }

    public List<Clients> selectAll(){

        return client.findAll();
    }


    public void insert(Clients c){
        NameValidator validator = new NameValidator();
        validator.validate(c);
        client.insert(c);

    }

    public void update(Clients c){
        client.update(c);

    }

    public void delete(Clients c){
        client.delete(c);
    }

    public DataTable getDataForTable(){
        return client.getDataForTable(client.findAll());
    }

}
