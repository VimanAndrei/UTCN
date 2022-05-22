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

public class FilterByStoreNameCommand implements ICommand {
    private VMEmployee vmEmployee;
    private ParfumeService parfumeService;

    public FilterByStoreNameCommand(VMEmployee vmEmployee, ParfumeService parfumeService) {
        this.vmEmployee = vmEmployee;
        this.parfumeService = parfumeService;
    }

    @Override
    public void Execute() {
        String storeName = vmEmployee.getStoreName().get();
        if (storeName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adaugati un nume in campul Nume magazin pentru a realiza afisarea parfmurilor dintr-un magazin!");
        }else{
            List<ParfumeStore> toList = new ArrayList<>();
            toList = parfumeService.getParfumeStoreList().stream().filter(parfumeStore -> parfumeStore.getStoreName().equals(storeName)).collect(Collectors.toList());

            if (toList.size() == 0) {
                JOptionPane.showMessageDialog(null, "Nu s-a gasit nimic!");
            } else {
                List<ParfumeStore> parfumes = new ArrayList<>();
                for (ParfumeStore ps : toList) {
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
        }
    }
}
