package ViewModel.CommandsEmployee;

import Model.Parfume;
import Model.ParfumeService;
import Model.ParfumeStore;
import ViewModel.ICommand;
import ViewModel.VMEmployee;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterByPriceCommand implements ICommand {
    private VMEmployee vmEmployee;
    private ParfumeService parfumeService;

    public FilterByPriceCommand(VMEmployee vmEmployee, ParfumeService parfumeService) {
        this.vmEmployee = vmEmployee;
        this.parfumeService = parfumeService;
    }

    @Override
    public void Execute() {
        String price1 = vmEmployee.getPrice().get();
        try {
            Double pri1 = Double.parseDouble(price1);
            List<ParfumeStore> parfumeStoreList = parfumeService.getParfumeStoreList();
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
                List<ParfumeStore> parfumes = new ArrayList<>();
                for (ParfumeStore ps : parfumeToView) {
                    for (Parfume p : ps.getParfumes()) {
                        ParfumeStore parfumeStore = new ParfumeStore(ps.getStoreName());
                        parfumeStore.addParfumeToStore(p);
                        parfumes.add(parfumeStore);
                    }
                }

                Object[][] data = new Object[parfumes.size()][8];

                for (int i = 0; i < parfumes.size(); i++) {
                    data[i][0] = parfumes.get(i).getStoreName();
                    data[i][1] = parfumes.get(i).getParfumes().get(0).getParfumeName();
                    data[i][2] = parfumes.get(i).getParfumes().get(0).getParfumeInfo().getManufacturerName();
                    data[i][3] = parfumes.get(i).getParfumes().get(0).getParfumeInfo().getNumberOfCopies();
                    data[i][4] = parfumes.get(i).getParfumes().get(0).getParfumeInfo().getBarCode();
                    data[i][5] = parfumes.get(i).getParfumes().get(0).getParfumeInfo().getPrice();
                    data[i][6] = parfumes.get(i).getParfumes().get(0).getParfumeInfo().getParfumeAmount();
                    data[i][7] = parfumes.get(i).getParfumes().get(0).getParfumeInfo().getNumberOfSoldCopies();


                }

                vmEmployee.setData(data);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Adaugati un numar real in campul Pret!");
        }

    }
}
