package bll;

import dao.DataTable;
import dao.ProductsDAO;

import model.Products;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductsBLL {
    private ProductsDAO prod;

    public ProductsBLL(){
        this.prod=new ProductsDAO();
    }

    public Products findClientById(int id){
        Products p = prod.findById(id);
        if(p == null){
            throw new NoSuchElementException("The student with id =" + id + " was not found!");
        }
        return p;
    }

    public void insert(Products p){
        prod.insert(p);

    }

    public void update(Products p){
        prod.update(p);

    }

    public void delete(Products p){
        prod.delete(p);
    }

    public List<Products> selectAll(){

        return prod.findAll();
    }

    public DataTable getDataForTable(){
        return prod.getDataForTable(prod.findAll());
    }
}
