package presentationLayer;

import businessLayer.BaseProduct;
import businessLayer.CompositeProduct;
import businessLayer.DeliveryService;
import businessLayer.Order;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ControllerAdministratorFrame {
    private AdministratorFrame aF;
    private DeliveryService dS;
    protected List<BaseProduct> prodCompuse = new ArrayList<>();

    public ControllerAdministratorFrame(AdministratorFrame aF, DeliveryService dS) {
        this.aF = aF;
        this.dS = dS;
        aF.importDataListener(new importDataActionListener());
        aF.editProductListener(new editProductActionListener());
        aF.deleteProductListener(new deleteProductActionListener());
        aF.addBaseProductListener(new addBaseProductActionListener());
        aF.addToListLisener(new addToListActionLisener());
        aF.composeLisener(new composeActionLisener());
        aF.raport1Lisener(new raport1ActionListener());
        aF.raport2Lisener(new raport2ActionListener());
        aF.raport3Lisener(new raport3ActionListener());
        aF.raport4Lisener(new raport4ActionListener());
        String total="";
        List<Order> list = dS.getOrders();
        for(Order b : list){
            total+=b.toString();
            total+="\n";
        }
        aF.setTotalOrderTP(total);
    }


    public class importDataActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //dS.readDataFromFile();
            String total="";
            List<BaseProduct> list = dS.getProducts();
            for(BaseProduct b : list){
                total+=b.toString();
                total+="\n";
            }
            aF.setTotalData(total);
        }
    }

    public class editProductActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            List<BaseProduct> list = dS.getProducts();
            String titlu = aF.getTitelTf();
            double rating,price;
            int protein,fat,sodium,calories;
            rating=Double.parseDouble(aF.getRatingTf());
            price=Double.parseDouble(aF.getPriceTf());
            protein=Integer.parseInt(aF.getProteinTf());
            fat=Integer.parseInt(aF.getFatTf());
            sodium=Integer.parseInt(aF.getSodiumTf());
            calories=Integer.parseInt(aF.getCaloriesTf());
            int gasit = 0;
            for(BaseProduct b : list){
                if(b.getTitle().equals(titlu)){
                    b.setCalories(calories);
                    b.setSodium(sodium);
                    b.setFat(fat);
                    b.setPrice(price);
                    b.setProtein(protein);
                    b.setRating(rating);
                    gasit=1;
                }
            }

            if(gasit==1) {
                String total="";
                for(BaseProduct b : list){

                    total+=b.toString();
                    total+="\n";

                }
                dS.setProducts(list);
                aF.setTotalData(total);
            }else{
                JOptionPane.showMessageDialog(aF, "Nu am gasit produsul");
            }
        }
    }

    public class deleteProductActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            List<BaseProduct> list = dS.getProducts();
            String titlu = aF.getTitelTf();
            int gasit = 0;
            BaseProduct deSters=null;
            for(BaseProduct b : list){
                if(b.getTitle().equals(titlu)){
                    deSters=b;
                    gasit=1;
                }
            }

            if(gasit==1) {
               dS.deleteProduct(deSters);
                list = dS.getProducts();
                String total="";
                for(BaseProduct b : list){

                    total+=b.toString();
                    total+="\n";

                }
                aF.setTotalData(total);
            }else{
                JOptionPane.showMessageDialog(aF, "Nu am gasit produsul");
            }
        }
    }

    public class addBaseProductActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {


            List<BaseProduct> list = dS.getProducts();
            String titlu = aF.getTitelTf();
            double rating,price;
            int protein,fat,sodium,calories;
            rating=Double.parseDouble(aF.getRatingTf());
            price=Double.parseDouble(aF.getPriceTf());
            protein=Integer.parseInt(aF.getProteinTf());
            fat=Integer.parseInt(aF.getFatTf());
            sodium=Integer.parseInt(aF.getSodiumTf());
            calories=Integer.parseInt(aF.getCaloriesTf());
            BaseProduct deAdaugat=new BaseProduct(titlu,rating,calories,protein,fat,sodium,price);

                String total="";
                for(BaseProduct b : list){

                    total+=b.toString();
                    total+="\n";

                }
                total+= deAdaugat.toString()+"\n";
                dS.addProdus(deAdaugat);
                aF.setTotalData(total);

        }
    }

    public class addToListActionLisener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            List<BaseProduct> list = dS.getProducts();
            String titlu = aF.getTitelTf();
            int gasit = 0;
            BaseProduct deGasit=null;
            for(BaseProduct b : list){
                if(b.getTitle().equals(titlu)){
                    deGasit=b;
                    gasit=1;
                }
            }

            if(gasit==1) {

                prodCompuse.add(deGasit);
                String total="";
                for(BaseProduct b : prodCompuse){
                    total+=b.toString();
                    total+="\n";
                }
                aF.setTotalCompozit(total);
            }else{
                JOptionPane.showMessageDialog(aF, "Nu am gasit produsul!");
            }

        }
    }

    public class composeActionLisener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            String titlu = aF.getTitleCompozitTf();
            CompositeProduct compus = new CompositeProduct(titlu,prodCompuse);
            List<BaseProduct> list = dS.getProducts();
            BaseProduct b = new BaseProduct(titlu,0.0,compus.computeCalories(),compus.computeProtein(),compus.computeFat(),compus.computeSodium(),compus.computePrice());
            String total="";
            for(BaseProduct c : list){
                total+=c.toString();
                total+="\n";
            }
            total+=b.toString()+"\n";
            dS.addProdus(b);
            aF.setTotalData(total);
            prodCompuse= new ArrayList<>();
        }
    }


    private class raport1ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String startH = aF.getStartHour(),endH = aF.getEndHour();
            int sH,eH;
            sH=Integer.parseInt(startH);
            eH=Integer.parseInt(endH);
            List<Order> comenzi = dS.getOrders();
            List<Order> deAfisat;
            deAfisat = comenzi.stream().filter(p -> p.getOrderDate().getHours()>sH && p.getOrderDate().getHours()<eH ).collect(Collectors.toList());
            File file = new File("raport1.txt");

            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write(deAfisat.toString());
                writer.flush();
                writer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


        }
    }
    private class raport2ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int nrDeOri = Integer.parseInt(aF.getProductNo());
            Map<Order, List<BaseProduct>> lista = dS.getOrderListMap();
            Map<String,Long> map =  lista.entrySet().stream()
                    .map(p -> p.getValue())
                    .flatMap(Collection::stream)
                    .collect(Collectors.groupingBy(BaseProduct::getTitle, Collectors.counting()));
            AtomicReference<String> total = new AtomicReference<>("");
            map.entrySet().stream()
                    .filter(p -> p.getValue()>=nrDeOri)
                    .forEach(stringLongEntry -> total.set(total + stringLongEntry.getKey() + "\n"));
            File file = new File("raport2.txt");

            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write(total.toString());
                writer.flush();
                writer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }
    private class raport3ActionListener implements ActionListener {
        public  double calculatePrice(List<BaseProduct> baseP){
            double price =0;
            for (BaseProduct b:
                 baseP) {
                price+=b.getPrice();
            }
            return price;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            int specNo = Integer.parseInt(aF.getSpecificNo());
            double amount = Double.parseDouble(aF.getAmount());
            List<Order> comenzi = dS.getOrders();
            List<Order> deAfisat = comenzi.stream().filter(order -> order.getPrice() >= amount).collect(Collectors.toList());

            Map<Integer,Long> deAfisat2 = deAfisat.stream()
                    .collect(Collectors.groupingBy(Order::getClientId,Collectors.counting()));
           AtomicReference<String> total = new AtomicReference<>("");

            deAfisat2.entrySet().stream()
                    .filter(integerLongEntry -> integerLongEntry.getValue()>=specNo)
                    .forEach(stringLongEntry -> total.set(total + "Client id:" + stringLongEntry.getKey() + " Number of times: " + stringLongEntry.getValue()+"\n"));
            File file = new File("raport3.txt");

            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write(total.toString());
                writer.flush();
                writer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }




        }
    }
    private class raport4ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int data = Integer.parseInt(aF.getSpecificDate());
           Map<String,Long> lista =  dS.getOrderListMap().entrySet().stream()
                    .filter(orderListEntry -> orderListEntry.getKey().getOrderDate().getDate() == data)
                    .map(p -> p.getValue())
                    .flatMap(Collection::stream)
                    .collect(Collectors.groupingBy(BaseProduct::getTitle, Collectors.counting()));
            System.out.println(lista);
            AtomicReference<String> total = new AtomicReference<>("");

            lista.entrySet().stream()
                    .forEach(stringLongEntry -> total.set(total +  stringLongEntry.getKey() + " Number of times: " + stringLongEntry.getValue()+"\n"));
            File file = new File("raport4.txt");

            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write(total.toString());
                writer.flush();
                writer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }
}
