package Controller;

import Model.Observer;
import Model.Parfume.*;
import View.EmployeeView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerEmployee implements Observer {
    private ModelParfumes model;
    private EmployeeView view;

    public ControllerEmployee(EmployeeView view) {
        this.view = view;
        this.model = view.getModel();
        this.addMultipleActionListeners();
    }

    private void addMultipleActionListeners() {
        this.view.getRomaniaButton().addActionListener(e -> {romania();});
        this.view.getEnglishButton().addActionListener(e -> {english();});
        this.view.getFranceButton().addActionListener(e -> {france();});
        this.view.getGermanButton().addActionListener(e -> {german();});
        this.view.getAfisareParfumuriButton().addActionListener(e -> {displayAllParfumes();});
        this.view.getAddButton().addActionListener(e -> {insertParfume();});
        this.view.getDeleteButton().addActionListener(e -> {deleteParfume();});
        this.view.getUpdateButton().addActionListener(e -> {updateParfume();});
        this.view.getAfisareProduseDintrUnButton().addActionListener(e -> {listProductFromStore();});
        this.view.getSalvareInMaiMulteButton().addActionListener(e -> {saveInMoreFormats();});
        this.view.getFiltrareDupaProducatorButton().addActionListener(e -> {filterByManufacturerName();});
        this.view.getFiltrareDupaDisponibilitateButton().addActionListener(e -> {filteringByDisponibility();});
        this.view.getCautare().addActionListener(e -> {filterByParfumeName();});
        this.view.getFiltrareDupaPretButton().addActionListener(e -> {filterByPrice();});
        this.view.getVizualizareStatisticiButton().addActionListener(e -> {statistics();});
    }

    private void statistics() {
        this.model.setOperation("statistici");
    }

    private void filterByPrice() {
        String price1 = view.getPrice().getText();
        try {
            Double pri1 = Double.parseDouble(price1);
            List<ParfumeStore> parfumeStoreList = model.getService().getParfumeStoreList();
            List<Parfume> deAfisat;
            List<ParfumeStore> parfumeToView = new ArrayList<>();
            for (ParfumeStore ps : parfumeStoreList) {
                Double finalPri = pri1;
                deAfisat = ps.getParfumes().stream().filter((p) -> p.getParfumeInfo().getPrice()<=finalPri ).collect(Collectors.toList());
                if (deAfisat.size() > 0){
                    ParfumeStore parf = new ParfumeStore(ps.getStoreName());
                    parf.setParfumes(deAfisat);
                    parfumeToView.add(parf);
                }
            }

            if (parfumeToView.size() == 0) {
                JOptionPane.showMessageDialog(null, "Nu s-a gasit nimic!");
            } else {
                Object[][] data = transform(parfumeToView);
                this.view.setData(data);
                this.model.setOperation("vizualizare");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Adaugati un numar real in campul Pret!");
        }
    }

    private void filterByParfumeName() {
        String parfumeName = this.view.getParfumeNameF().getText();
        if (parfumeName==null) {
            JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume Parfum pentru a realiza cautare dupa nume!");
        } else {
            List<ParfumeStore> parfumeStoreList = model.getService().getParfumeStoreList();

            List<ParfumeStore> parfumeToView = new ArrayList<>();

            for (ParfumeStore ps : parfumeStoreList) {
                List<Parfume> parfumes = ps.getParfumes();
                for (Parfume p : parfumes) {
                    if (p.getParfumeName().equals(parfumeName)) {
                        ParfumeStore parf = new ParfumeStore(ps.getStoreName());
                        parf.addParfumeToStore(p);
                        parfumeToView.add(parf);
                    }
                }
            }

            if (parfumeToView.size() == 0) {
                JOptionPane.showMessageDialog(null, "Nu s-a gasit nimic!");
            } else {
                Object[][] data = transform(parfumeToView);
                this.view.setData(data);
                this.model.setOperation("vizualizare");
            }
        }
    }

    private void filteringByDisponibility() {
        List<ParfumeStore> parfumeStoreList = model.getService().getParfumeStoreList();
        List<Parfume> deAfisat;
        List<ParfumeStore> parfumeToView = new ArrayList<>();
        for (ParfumeStore ps : parfumeStoreList) {
            deAfisat = ps.getParfumes().stream().filter((p) -> p.getParfumeInfo().getNumberOfCopies() > 0).collect(Collectors.toList());
            if (deAfisat.size() > 0){
                ParfumeStore parf = new ParfumeStore(ps.getStoreName());
                parf.setParfumes(deAfisat);
                parfumeToView.add(parf);
            }
        }

        if (parfumeToView.size() == 0) {
            JOptionPane.showMessageDialog(null, "Nu s-a gasit nimic!");
        } else {
            Object[][] data = transform(parfumeToView);
            this.view.setData(data);
            this.model.setOperation("vizualizare");
        }
    }

    private void filterByManufacturerName() {
        String manufacturerName = this.view.getManufacturerName().getText();
        if(manufacturerName.isEmpty()){
            JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume producator pentru a realiza filtrarea!");
        }else{

            List<ParfumeStore> parfumeStoreList = this.model.getService().getParfumeStoreList();
            List<Parfume> deAfisat;
            List<ParfumeStore> parfumeToView = new ArrayList<>();
            for (ParfumeStore ps : parfumeStoreList) {
                deAfisat = ps.getParfumes().stream().filter((p) -> p.getParfumeInfo().getManufacturerName().equals(manufacturerName)).collect(Collectors.toList());
                if (deAfisat.size() > 0){
                    ParfumeStore parf = new ParfumeStore(ps.getStoreName());
                    parf.setParfumes(deAfisat);
                    parfumeToView.add(parf);
                }
            }

            if (parfumeToView.size() == 0) {
                JOptionPane.showMessageDialog(null, "Nu s-a gasit nimic!");
            } else {
                Object[][] data = transform(parfumeToView);
                this.view.setData(data);
                this.model.setOperation("vizualizare");
            }
        }
    }

    private void saveInMoreFormats() {
        model.getService().saveAsCSV(model.getService().getParfumeStoreList());
        model.getService().saveAsJSON(model.getService().getParfumeStoreList());
        model.getService().saveAsXML(model.getService().getParfumeStoreList());
        model.setOperation("salvare_mai_multe_formate");
    }

    private void listProductFromStore() {
        String storeName = view.getStoreName().getText();
        if (storeName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume magazin pentru a realiza afisarea parfmurilor dintr-un magazin!");
        }else{
            List<ParfumeStore> toList = new ArrayList<>();
            toList = model.getService().filterByStoreName(storeName);

            if (toList.size() == 0) {
                JOptionPane.showMessageDialog(null, "Nu s-a gasit nimic!");
            } else {
                Object[][] data = transform(toList);
                view.setData(data);
                this.model.setOperation("vizualizare");
            }
        }
    }

    private void updateParfume() {
        String storeNameOld = this.view.getSelectedStoreName();
        String parfumeNameOld = this.view.getSelectedParfumeName();
        String manufacturerNameOld = this.view.getSelectedManufacturerName();
        String parfumeAmountOld = this.view.getSelectedParfumeAmount();

        if(storeNameOld == null||parfumeNameOld== null||manufacturerNameOld== null||parfumeAmountOld== null){
            JOptionPane.showMessageDialog(null, "Nu ati selectat nici o linie din tabela!");
        }else{

            ParfumeStore parfumeStoreOld = this.model.getService().getParfumeStoreByStrings(storeNameOld, parfumeNameOld, manufacturerNameOld,parfumeAmountOld);
            if (parfumeStoreOld == null) {
                JOptionPane.showMessageDialog(null, "Parfum negasit, nu poate avea loc actualizarea!");
            } else {
                String storeNameNew  = this.view.getStoreName().getText();
                String parfumeNameNew  = this.view.getParfumeNameF().getText();
                String manufacturerNameNew  = this.view.getManufacturerName().getText();
                String numberOfCopiesNew  = this.view.getNumOfCopies().getText();
                String barCodeNew  = this.view.getBarCode().getText();
                String priceNew  = this.view.getPrice().getText();
                String parfumeAmountNew  = this.view.getParfumeAmount().getText();
                String numberOfSoldCopiesNew  = this.view.getNumOfSoldCopies().getText();

                if (storeNameNew.isEmpty()) {
                    storeNameNew = parfumeStoreOld.getStoreName();
                }
                if (parfumeNameNew.isEmpty()) {
                    parfumeNameNew = parfumeStoreOld.getParfumes().get(0).getParfumeName();
                }
                if (manufacturerNameNew.isEmpty()) {
                    manufacturerNameNew = parfumeStoreOld.getParfumes().get(0).getParfumeInfo().getManufacturerName();
                }
                if (numberOfCopiesNew.isEmpty()) {
                    numberOfCopiesNew = parfumeStoreOld.getParfumes().get(0).getParfumeInfo().getNumberOfCopies().toString();
                }
                if (barCodeNew.isEmpty()) {
                    barCodeNew = parfumeStoreOld.getParfumes().get(0).getParfumeInfo().getBarCode().toString();
                }
                if (priceNew.isEmpty()) {
                    priceNew = parfumeStoreOld.getParfumes().get(0).getParfumeInfo().getPrice().toString();
                }
                if (parfumeAmountNew.isEmpty()) {
                    parfumeAmountNew = parfumeStoreOld.getParfumes().get(0).getParfumeInfo().getParfumeAmount().toString();
                }
                if (numberOfSoldCopiesNew.isEmpty()) {
                    numberOfSoldCopiesNew = parfumeStoreOld.getParfumes().get(0).getParfumeInfo().getNumberOfSoldCopies().toString();
                }

                Integer nofc = null, barC = null, parfAmo = null, nofsc = null;
                Double pri = null;
                try {
                    nofc = Integer.parseInt(numberOfCopiesNew);
                    barC = Integer.parseInt(barCodeNew);
                    pri = Double.parseDouble(priceNew);
                    parfAmo = Integer.parseInt(parfumeAmountNew);
                    nofsc = Integer.parseInt(numberOfSoldCopiesNew);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Adaugati un numar !");
                }
                if (nofc != null && barC != null && pri != null && parfAmo != null && nofsc != null) {

                    ParfumeInfo parfumeInfo = new ParfumeInfoBuilder().setManufacturerName(manufacturerNameNew).setNumberOfCopies(nofc).setBarCode(barC).setPrice(pri).setParfumeAmount(parfAmo).setNumberOfSoldCopies(nofsc).build();
                    Parfume parfume = new ParfumeBuilder().setParfumeName(parfumeNameNew).setParfumeInfo(parfumeInfo).build();
                    ParfumeStore parfumeStoreNew = new ParfumeStore(storeNameNew);
                    parfumeStoreNew.addParfumeToStore(parfume);

                    this.model.getService().updateParfume(parfumeStoreOld, parfumeStoreNew);
                    this.displayAllParfumes();

                }else{
                    JOptionPane.showMessageDialog(null, "Nu poate avea loc actualizarea!");
                }
            }
        }
    }

    private void deleteParfume() {
        if (this.view.getSelectedStoreName() == null || this.view.getSelectedParfumeName() == null ||
                this.view.getSelectedManufacturerName()==null || this.view.getSelectedParfumeAmount() == null){
            JOptionPane.showMessageDialog(null, "Nu ati selectat nici o linie din tabela!");
        }else{
            this.model.getService().deleteParfume(this.view.getSelectedStoreName(),this.view.getSelectedParfumeName(),this.view.getSelectedManufacturerName(),this.view.getSelectedParfumeAmount());
            this.displayAllParfumes();
        }
    }

    private void insertParfume() {
        if(this.view.getStoreName().getText().isEmpty() || this.view.getParfumeNameF().getText().isEmpty() ||
                this.view.getManufacturerName().getText().isEmpty() || this.view.getNumOfCopies().getText().isEmpty() ||
                this.view.getBarCode().getText().isEmpty() || this.view.getPrice().getText().isEmpty()||
                this.view.getParfumeAmount().getText().isEmpty() || this.view.getNumOfSoldCopies().getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Unul dintre campuri nu este completat!");
        }else{

            try {
                String storeName = this.view.getStoreName().getText();
                String parfumeName = this.view.getParfumeNameF().getText();
                String manufacturerName =  this.view.getManufacturerName().getText();
                Integer numberOfCopies = Integer.parseInt(this.view.getNumOfCopies().getText());
                Integer barCode = Integer.parseInt(this.view.getBarCode().getText());
                Double price = Double.parseDouble(this.view.getPrice().getText());
                Integer parfumeAmount = Integer.parseInt(this.view.getParfumeAmount().getText());
                Integer numberOfSoldCopies = Integer.parseInt(this.view.getNumOfSoldCopies().getText());
                ParfumeInfo parfumeInfo = new ParfumeInfoBuilder().setManufacturerName(manufacturerName).setNumberOfCopies(numberOfCopies).setBarCode(barCode).setPrice(price).setParfumeAmount(parfumeAmount).setNumberOfSoldCopies(numberOfSoldCopies).build();
                Parfume parfume = new ParfumeBuilder().setParfumeName(parfumeName).setParfumeInfo(parfumeInfo).build();
                ParfumeStore parfumeStore = new ParfumeStore(storeName);
                parfumeStore.addParfumeToStore(parfume);
                if(!this.model.getService().insertParfume(parfumeStore)){
                    JOptionPane.showMessageDialog(null, "Nu poate fi inserat acest parfum!");
                }
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Adaugati un numar !");
            }
            this.displayAllParfumes();
        }
    }

    private Object[][] transform(List <ParfumeStore> toList){
        List<ParfumeStore> parfumes = new ArrayList<>();
        for (ParfumeStore ps : toList) {
            for (Parfume p : ps.getParfumes()) {
                ParfumeStore parfumeStore = new ParfumeStore(ps.getStoreName());
                parfumeStore.addParfumeToStore(p);
                parfumes.add(parfumeStore);
            }
        }
        Object[][] data = new Object[parfumes.size()][8];

        for (int i = 0; i < parfumes.size() ; i++) {
            data[i][0] =parfumes.get(i).getStoreName();
            data[i][1] =parfumes.get(i).getParfumes().get(0).getParfumeName();
            data[i][2] =parfumes.get(i).getParfumes().get(0).getParfumeInfo().getManufacturerName();
            data[i][3] =parfumes.get(i).getParfumes().get(0).getParfumeInfo().getNumberOfCopies();
            data[i][4] =parfumes.get(i).getParfumes().get(0).getParfumeInfo().getBarCode();
            data[i][5] =parfumes.get(i).getParfumes().get(0).getParfumeInfo().getPrice();
            data[i][6] =parfumes.get(i).getParfumes().get(0).getParfumeInfo().getParfumeAmount();
            data[i][7] =parfumes.get(i).getParfumes().get(0).getParfumeInfo().getNumberOfSoldCopies();

        }
        return data;
    }

    public void displayAllParfumes() {
        List<ParfumeStore> parfumeStoreList = this.model.getService().getParfumeStoreList();
        Object[][] data = transform(parfumeStoreList);
        this.view.setData(data);
        this.model.setOperation("vizualizare");
    }

    private void romania(){
        this.model.setLanguage("romana");
        this.model.setOperation("language");
    }
    private void english(){
        this.model.setLanguage("engleza");
        this.model.setOperation("language");
    }
    private void france(){
        this.model.setLanguage("franceza");
        this.model.setOperation("language");
    }
    private void german(){
        this.model.setLanguage("germana");
        this.model.setOperation("language");
    }


    @Override
    public void update() {

    }
}
