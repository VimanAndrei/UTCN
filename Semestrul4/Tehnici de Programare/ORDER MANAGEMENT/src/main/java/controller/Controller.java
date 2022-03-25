package controller;

import bll.Bill;
import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductsBLL;
import dao.DataTable;
import model.*;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private View v;
    private ClientBLL cb;
    private OrderBLL ob;
    private ProductsBLL pb;
    private Bill b;
    public Controller(View view){
        this.v=view;
        this.cb = new ClientBLL();
        this.ob = new OrderBLL();
        this.pb = new ProductsBLL();
        this.b = new Bill();
        v.bonTotalListener(new bonTotalActionListener());

        v.insertClientListener(new insertClientActionListener());
        v.insertOrdersListener(new insertOrdersActionListener());
        v.insertProductsListener(new insertProductsActionListener());

        v.editOrdersListener(new editOrdersActionListener());
        v.editClientListener(new editClientActionListener());
        v.editProductsListener(new editProductsListener());

        v.deleteClientListener(new deleteClientActionListener());
        v.deleteOrdersListener(new deleteOrdersActionListener());
        v.deleteProductsListener(new deleteProductsActionListener());

        v.displayClientListener(new displayClientActionListener());
        v.displayOrdersListener(new displayOrdersActionListener());
        v.displayProductsListener(new displayProductsActionListener());

    }

    public class bonTotalActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            b.chitanta();
        }
    }

    public class insertClientActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
             int id;
             String name,address;

             id=Integer.parseInt(v.getIdClientsText());
             name=v.getNameClientsText();
             address=v.getAddressClientsText();

             Clients client = new Clients(id,name,address);
             cb.insert(client);
        }
    }
    public class insertOrdersActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int id,clientId,productId,quantity;


            id=Integer.parseInt(v.getIdOrdersText());
            clientId=Integer.parseInt(v.getClientIdOrdersText());
            productId=Integer.parseInt(v.getProductIdOrdersText());
            quantity=Integer.parseInt(v.getQuantityOrdersText());

            Object[][] data = ob.getDataForTable().getTable();
            int sum = 0;
            for(int i=0;i<data.length;i++) {
                for (int j = 0; j < data[i].length; j++) {
                    if (j == 3 && (int)data[i][2]==productId) {
                        sum += (int) data[i][j];
                    }
                }
            }
            sum+=quantity;

            Products produs = pb.findClientById(productId);
            int cantitate = produs.getQuantity();
            if(cantitate>=sum) {
                Orders order = new Orders(id, clientId, productId, quantity);
                ob.insert(order);
            }else{
                throw new IllegalArgumentException("Nu se poate doarece stocul nu mai ajunge!!!");
            }
        }
    }
    public class insertProductsActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int id,quantity;
            double price;
            String productName;

            id=Integer.parseInt(v.getIdProductsText());
            quantity=Integer.parseInt(v.getQuantityProductsText());
            price=Double.parseDouble(v.getPriceProductsText());
            productName=v.getProductNameProductsText();

            Products product = new Products(id,price,quantity,productName);
            pb.insert(product);
        }
    }

    public class editOrdersActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int id,clientId,productId,quantity;

            id=Integer.parseInt(v.getIdOrdersText());
            clientId=Integer.parseInt(v.getClientIdOrdersText());
            productId=Integer.parseInt(v.getProductIdOrdersText());
            quantity=Integer.parseInt(v.getQuantityOrdersText());

            Orders order =new Orders(id,clientId,productId,quantity);
            ob.update(order);
        }
    }
    public class editClientActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int id;
            String name,address;

            id=Integer.parseInt(v.getIdClientsText());
            name=v.getNameClientsText();
            address=v.getAddressClientsText();

            Clients client = new Clients(id,name,address);
            cb.update(client);

        }
    }
    public class editProductsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int id,quantity;
            double price;
            String productName;

            id=Integer.parseInt(v.getIdProductsText());
            quantity=Integer.parseInt(v.getQuantityProductsText());
            price=Double.parseDouble(v.getPriceProductsText());
            productName=v.getProductNameProductsText();

            Products product = new Products(id,price,quantity,productName);
            pb.update(product);
        }
    }

    public class deleteClientActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int id;
            String name,address;

            id=Integer.parseInt(v.getIdClientsText());
            name=v.getNameClientsText();
            address=v.getAddressClientsText();

            Clients client = new Clients(id,name,address);
            cb.delete(client);
        }
    }
    public class deleteOrdersActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int id,clientId,productId,quantity;

            id=Integer.parseInt(v.getIdOrdersText());
            clientId=Integer.parseInt(v.getClientIdOrdersText());
            productId=Integer.parseInt(v.getProductIdOrdersText());
            quantity=Integer.parseInt(v.getQuantityOrdersText());

            Orders order =new Orders(id,clientId,productId,quantity);
            ob.delete(order);
        }
    }
    public class deleteProductsActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int id,quantity;
            double price;
            String productName;

            id=Integer.parseInt(v.getIdProductsText());
            quantity=Integer.parseInt(v.getQuantityProductsText());
            price=Double.parseDouble(v.getPriceProductsText());
            productName=v.getProductNameProductsText();

            Products product = new Products(id,price,quantity,productName);
            pb.delete(product);

        }
    }

    public class displayClientActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            DataTable dT = cb.getDataForTable();
            Object[] antet= dT.getAntet();
            Object[][] data= dT.getTable();
            v.setTotal(data,antet);

        }
    }
    public class displayOrdersActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            DataTable dT = ob.getDataForTable();
            Object[] antet= dT.getAntet();
            Object[][] data= dT.getTable();
            v.setTotal(data,antet);
        }
    }
    public class displayProductsActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            DataTable dT = pb.getDataForTable();
            Object[] antet= dT.getAntet();
            Object[][] data= dT.getTable();
            v.setTotal(data,antet);
        }
    }

}
