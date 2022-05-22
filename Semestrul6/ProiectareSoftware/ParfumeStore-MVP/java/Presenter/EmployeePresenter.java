package Presenter;

import Model.Parfume;
import Model.ParfumeInfo;
import Model.ParfumeService;
import Model.ParfumeStore;
import View.EmployeeView;
import View.IEmployeeView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeePresenter {
    IEmployeeView view;
    ParfumeService parfumeService;

    public EmployeePresenter(IEmployeeView view) {
        this.view = view;
        parfumeService = new ParfumeService();
    }


    public void listParfumes() {
        view.afisParfumes(parfumeService.toString());
    }

    public void addParfume() {
        String storeName = view.getStoreName();
        String parfumeName = view.getParfumeName();
        String manufacturerName = view.getManufacturerName();
        String numberOfCopies = view.getNumOfCopies();
        String barCode = view.getBarCode();
        String price = view.getPrice();
        String parfumeAmount = view.getParfumeAmount();
        String numberOfSoldCopies = view.getNumOfSoldCopies();

        Integer nofc = null, barC = null, parfAmo = null, nofsc = null;
        Double pri = null;
        try {
            nofc = Integer.parseInt(numberOfCopies);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Adaugati un numar intreg in campul Numar produse ramase!");
        }

        try {
            barC = Integer.parseInt(barCode);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Adaugati un numar intreg in campul Cod de bare!");
        }

        try {
            pri = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Adaugati un numar real in campul Pret!");
        }

        try {
            parfAmo = Integer.parseInt(parfumeAmount);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Adaugati un numar intreg in campul Dimensiunea in ml!");
        }

        try {
            nofsc = Integer.parseInt(numberOfSoldCopies);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Adaugati un numar intreg in campul Numar produse vandute!");
        }

        if (nofc != null && barC != null && pri != null && parfAmo != null && nofsc != null) {
            if (storeName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume magazin!");
            } else {
                if (parfumeName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume Parfum!");
                } else {
                    if (manufacturerName.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume producator!");
                    } else {
                        ParfumeInfo parfumeInfo = new ParfumeInfo(manufacturerName, nofc, barC, pri, parfAmo, nofsc);
                        Parfume parfume = new Parfume(parfumeName, parfumeInfo);
                        ParfumeStore parfumeStore = new ParfumeStore(storeName);
                        parfumeStore.addParfumeToStore(parfume);

                        if (!parfumeService.addNewParfume(parfumeStore)) {
                            JOptionPane.showMessageDialog(null, "Nu a putut fi adaugat acest parfum deoarece exista deja!");
                        }
                    }
                }
            }
        }
    }

    public void deleteParfume() {
        String storeName = view.getStoreName();
        String parfumeName = view.getParfumeName();
        String manufacturerName = view.getManufacturerName();

        if (storeName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume magazin pentru a realiza stergerea!");
        } else {
            if (parfumeName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume Parfum pentru a realiza stergerea!");
            } else {
                if (manufacturerName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume producator pentru a realiza stergerea!");
                } else {
                    ParfumeStore parfumeStore = parfumeService.getParfumeStoreByStrings(storeName, parfumeName, manufacturerName);
                    if (parfumeStore == null) {
                        JOptionPane.showMessageDialog(null, "Parfum negasit, nu poate avea loc stergerea!");
                    } else {
                        parfumeService.deleteParfume(parfumeStore);
                    }
                }
            }
        }
    }

    public void updateParfume() {
        String storeName = view.getStoreName();
        String parfumeName = view.getParfumeName();
        String manufacturerName = view.getManufacturerName();

        if (storeName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume magazin pentru a realiza actualizarea!");
        } else {
            if (parfumeName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume Parfum pentru a realiza actualizarea!");
            } else {
                if (manufacturerName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume producator pentru a realiza actualizarea!");
                } else {
                    ParfumeStore parfumeStoreOld = parfumeService.getParfumeStoreByStrings(storeName, parfumeName, manufacturerName);
                    if (parfumeStoreOld == null) {
                        JOptionPane.showMessageDialog(null, "Parfum negasit, nu poate avea loc actualizarea!");
                    } else {

                        String storeNameNew = view.getUpdStoreName();
                        String parfumeNameNew = view.getUpdParfumeName();
                        String manufacturerNameNew = view.getUpdManufacturerName();
                        String numberOfCopiesNew = view.getUpdNumOfCopies();
                        String barCodeNew = view.getUpdBarCode();
                        String priceNew = view.getUpdPrice();
                        String parfumeAmountNew = view.getUpdParfumeAmount();
                        String numberOfSoldCopiesNew = view.getUpdNumOfSoldCopies();

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
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Adaugati un numar intreg in campul Numar produse ramase!");
                        }

                        try {
                            barC = Integer.parseInt(barCodeNew);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Adaugati un numar intreg in campul Cod de bare!");
                        }

                        try {
                            pri = Double.parseDouble(priceNew);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Adaugati un numar real in campul Pret!");
                        }

                        try {
                            parfAmo = Integer.parseInt(parfumeAmountNew);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Adaugati un numar intreg in campul Dimensiunea in ml!");
                        }

                        try {
                            nofsc = Integer.parseInt(numberOfSoldCopiesNew);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Adaugati un numar intreg in campul Numar produse vandute!");
                        }

                        if (nofc != null && barC != null && pri != null && parfAmo != null && nofsc != null) {

                            ParfumeInfo parfumeInfo = new ParfumeInfo(manufacturerNameNew, nofc, barC, pri, parfAmo, nofsc);
                            Parfume parfume = new Parfume(parfumeNameNew, parfumeInfo);
                            ParfumeStore parfumeStoreNew = new ParfumeStore(storeNameNew);
                            parfumeStoreNew.addParfumeToStore(parfume);

                            parfumeService.updateParfume(parfumeStoreOld, parfumeStoreNew);

                        }
                    }
                }
            }
        }
    }

    public void searchByParfumeName() {
        String parfumeName = view.getParfumeName();
        if (parfumeName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume Parfum pentru a realiza cautare dupa nume!");
        } else {
            List<ParfumeStore> parfumeStoreList = parfumeService.getParfumeStoreList();

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
                view.afisParfumes("Nu s-a gasit nimic!");
            } else {
                view.afisParfumes(parfumeToView.toString());
            }
        }
    }

    public void filterByNumOfCopies() {

        List<ParfumeStore> parfumeStoreList = parfumeService.getParfumeStoreList();
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
            view.afisParfumes("Nu s-a gasit nimic!");
        } else {
            view.afisParfumes(parfumeToView.toString());
        }

    }

    public void filterByManufacturerName() {
        String manufacturerName = view.getManufacturerName();
        if(manufacturerName.isEmpty()){
            JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume producator pentru a realiza filtrarea!");
        }else{

            List<ParfumeStore> parfumeStoreList = parfumeService.getParfumeStoreList();
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
                view.afisParfumes("Nu s-a gasit nimic!");
            } else {
                view.afisParfumes(parfumeToView.toString());
            }

        }
    }

    public void filterByPrice() {
        String price1 = view.getPrice();
        String price2 = view.getUpdPrice();
        Double pri1=null,pri2=null;
        try {
            pri1 = Double.parseDouble(price1);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Adaugati un numar real in primul camp Pret!");
        }
        try {
            pri2 = Double.parseDouble(price2);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Adaugati un numar real in al doilea camp Pret!");
        }
        if (pri1 != null && pri2 != null){
            if(pri1 > pri2){
                Double aux = pri1;
                pri1 = pri2;
                pri2 = aux;
            }

            List<ParfumeStore> parfumeStoreList = parfumeService.getParfumeStoreList();
            List<Parfume> deAfisat;
            List<ParfumeStore> parfumeToView = new ArrayList<>();
            for (ParfumeStore ps : parfumeStoreList) {
                Double finalPri = pri1;
                Double finalPri1 = pri2;
                deAfisat = ps.getParfumes().stream().filter((p) -> p.getParfumeInfo().getPrice()>=finalPri && p.getParfumeInfo().getPrice()<=finalPri1).collect(Collectors.toList());
                if (deAfisat.size() > 0){
                    ParfumeStore parf = new ParfumeStore(ps.getStoreName());
                    parf.setParfumes(deAfisat);
                    parfumeToView.add(parf);
                }
            }

            if (parfumeToView.size() == 0) {
                view.afisParfumes("Nu s-a gasit nimic!");
            } else {
                view.afisParfumes(parfumeToView.toString());
            }
        }
    }

    public void saveAsOtherFiles() {
        parfumeService.saveAsCSV(parfumeService.getParfumeStoreList());
        parfumeService.saveAsJSON(parfumeService.getParfumeStoreList());
    }

    public void filterByStore() {
        String storeName = view.getStoreName();
        if (storeName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume magazin pentru a realiza afisarea parfmurilor dintr-un magazin!");
        }else{
            List<ParfumeStore> toList = new ArrayList<>();
            toList = parfumeService.getParfumeStoreList().stream().filter(parfumeStore -> parfumeStore.getStoreName().equals(storeName)).collect(Collectors.toList());

            if (toList.size() == 0) {
                view.afisParfumes("Nu s-a gasit nimic!");
            } else {
                view.afisParfumes(toList.toString());
            }
        }

    }
}
