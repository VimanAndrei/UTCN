package presentationLayer;

import businessLayer.BaseProduct;
import businessLayer.DeliveryService;
import businessLayer.Order;
import dataLayer.FileWriter;
import model.LoggingAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerClientFrame {
    private final ClientFrame cF;
    private final DeliveryService dS;
    private final LoggingAccount client;
    protected List<BaseProduct> prodComanda ;

    public ControllerClientFrame(ClientFrame cF,DeliveryService dS,LoggingAccount c){
        this.cF=cF;
        this.dS=dS;
        this.client=c;
        prodComanda=new ArrayList<>();
        cF.afiseazaProduseListener(new afiseazaProduseActionListener());
        cF.addToTFListener(new addToTFActionListener());
        cF.cautaDupaTitluListener(new cautaDupaTitluActionListener());
        cF.cautaDupaRatingListener(new cautaDupaRatingActionListener());
        cF.cautaDupaPretListener(new cautaDupaPretActionListener());
        cF.cautaDupaProteineListener (new cautaDupaProteineActionListener() );
        cF.cautaDupaGrasimiListener(new cautaDupaGrasimiActionListener());
        cF.cautaDupaCaloriiListener(new cautaDupaCaloriiActionListener());
        cF.cautaDupaSodiuListener(new cautaDupaSodiuActionListener());
        cF.placeOrderListener(new placeOrderActionListener());
        cF.cautaListener(new cautaActionListener());
    }

    public String tranString(List<BaseProduct> list){
        StringBuilder total= new StringBuilder();
        for(BaseProduct b : list){
            total.append(b.toString());
            total.append("\n\n");
        }
        return total.toString();
    }
    private class afiseazaProduseActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<BaseProduct> list = dS.getProducts();
            String total=tranString(list);
            cF.setTotalProduse(total);
        }
    }

    private class addToTFActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<BaseProduct> list = dS.getProducts();
            List<BaseProduct> deAfisat;
            String title = cF.getTitelTf();
            deAfisat =  list.stream().filter((p) -> p.getTitle().equals(title)).collect(Collectors.toList());
            deAfisat.forEach(p->prodComanda.add(p));
            String total=tranString(prodComanda);
            cF.setTotalComanda(total);

        }
    }

    private class cautaDupaTitluActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<BaseProduct> list = dS.getProducts();
            List<BaseProduct> deAfisat;
            String title = cF.getTitelTf();
            deAfisat =  list.stream().filter((p) -> p.getTitle().equals(title)).collect(Collectors.toList());
            String total=tranString(deAfisat);
            cF.setTotalProduse(total);

        }
    }

    private class cautaDupaRatingActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<BaseProduct> list = dS.getProducts();
            List<BaseProduct> deAfisat;
            double rating = Double.parseDouble(cF.getRatingTf());
            deAfisat = list.stream().filter(p -> p.getRating() == rating).collect(Collectors.toList());
            String total=tranString(deAfisat);
            cF.setTotalProduse(total);
        }
    }

    private class cautaDupaPretActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<BaseProduct> list = dS.getProducts();
            List<BaseProduct> deAfisat;
            double pret = Double.parseDouble(cF.getPriceTf());
            deAfisat = list.stream().filter(p -> p.getPrice() == pret).collect(Collectors.toList());
            String total=tranString(deAfisat);
            cF.setTotalProduse(total);
        }
    }

    private class cautaDupaProteineActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<BaseProduct> list = dS.getProducts();
            List<BaseProduct> deAfisat;
            int protein = Integer.parseInt(cF.getProteinTf());
            deAfisat = list.stream().filter(p -> p.getProtein() == protein).collect(Collectors.toList());
            String total=tranString(deAfisat);
            cF.setTotalProduse(total);
        }
    }

    private class cautaDupaGrasimiActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<BaseProduct> list = dS.getProducts();
            List<BaseProduct> deAfisat;
            int grasimi = Integer.parseInt(cF.getFatTf());
            deAfisat = list.stream().filter(p -> p.getFat() == grasimi).collect(Collectors.toList());
            String total=tranString(deAfisat);
            cF.setTotalProduse(total);
        }
    }

    private class cautaDupaCaloriiActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<BaseProduct> list = dS.getProducts();
            List<BaseProduct> deAfisat;
            int sodiu = Integer.parseInt(cF.getSodiumTf());
            deAfisat = list.stream().filter(p -> p.getCalories() == sodiu).collect(Collectors.toList());
            String total=tranString(deAfisat);
            cF.setTotalProduse(total);
        }
    }

    private class cautaDupaSodiuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<BaseProduct> list = dS.getProducts();
            List<BaseProduct> deAfisat;
            int sodium = Integer.parseInt(cF.getSodiumTf());
            deAfisat = list.stream().filter(p -> p.getSodium() == sodium).collect(Collectors.toList());
            String total=tranString(deAfisat);
            cF.setTotalProduse(total);
        }
    }

    private class placeOrderActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0,clientId=0;
            List<LoggingAccount> accounts = dS.getAccounts();
            for(i=0;i<accounts.size();i++){
                LoggingAccount a = accounts.get(i);
                if(a.getPassword().equals(client.getPassword()) && a.getUserName().equals(client.getUserName())){
                    clientId = i;
                }
            }
            double price = 0;
            for(BaseProduct p : prodComanda){
                price += p.getPrice();
            }
            Order o = new Order(dS.getOrders().size(),clientId,new Date(),price);
            dS.addOrderListMap(o,prodComanda);
            dS.addOrder(o);
            dS.addObservator(o);
            FileWriter fR = new FileWriter();
            fR.createBill(o,prodComanda,client,price);
            cF.setTotalComanda("");
        }
    }

    private class cautaActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<BaseProduct> list = dS.getProducts();
            List<BaseProduct> deAfisat;
            String title;
            double rating,price;
            int protein,fat,sodium,calories;
            title = cF.getTitelTf();
            if(cF.getRatingTf().equals("")) {
                rating = Double.MIN_VALUE;
            }else{
                rating = Double.parseDouble(cF.getRatingTf());
            }
            if(cF.getPriceTf().equals("")) {
                price = Double.MIN_VALUE;
            }else{
                price = Double.parseDouble(cF.getPriceTf());
            }
            if(cF.getProteinTf().equals("")) {
                protein = Integer.MIN_VALUE;
            }else{
                protein = Integer.parseInt(cF.getProteinTf());
            }

            if(cF.getFatTf().equals("")) {
                fat = Integer.MIN_VALUE;
            }else{
                fat = Integer.parseInt(cF.getFatTf());
            }
            if(cF.getSodiumTf().equals("")) {
                sodium = Integer.MIN_VALUE;
            }else{
                sodium = Integer.parseInt(cF.getSodiumTf());
            }
            if(cF.getCaloriesTf().equals("")) {
                calories = Integer.MIN_VALUE;
            }else{
                calories = Integer.parseInt(cF.getCaloriesTf());
            }
            deAfisat = list.stream().filter(p -> ( title.equals("") || p.getTitle().equals(title) ) )
                                    .filter(p ->( price == Double.MIN_VALUE || p.getPrice() == price ) )
                                    .filter(p ->( sodium == Integer.MIN_VALUE || p.getSodium() == sodium ) )
                                    .filter(p ->( rating == Double.MIN_VALUE || p.getRating() == rating ) )
                                    .filter(p ->( protein == Integer.MIN_VALUE || p.getProtein() == protein ) )
                                    .filter(p ->( calories == Integer.MIN_VALUE || p.getCalories() == calories ) )
                                    .filter(p ->(fat == Integer.MIN_VALUE|| p.getFat() == fat ) )
                                    .collect(Collectors.toList());
            String total=tranString(deAfisat);
            cF.setTotalProduse(total);
        }
    }
}
