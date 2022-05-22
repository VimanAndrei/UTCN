package ViewModel.CommandsEmployee;

import Model.Parfume;
import Model.ParfumeService;
import Model.ParfumeStore;
import ViewModel.ICommand;
import ViewModel.VMEmployee;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SearchByParfumeNameCommand implements ICommand {
    private VMEmployee vmEmployee;
    private ParfumeService parfumeService;

    public SearchByParfumeNameCommand(VMEmployee vmEmployee, ParfumeService parfumeService) {
        this.vmEmployee = vmEmployee;
        this.parfumeService = parfumeService;
    }

    @Override
    public void Execute() {
        String parfumeName = vmEmployee.getParfumeName().get();
        if (parfumeName==null) {
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
                JOptionPane.showMessageDialog(null, "Nu s-a gasit nimic!");
            } else {
                List <ParfumeStore> parfumes = new ArrayList<>();
                for (ParfumeStore ps : parfumeToView) {
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

                vmEmployee.setData(data);
            }
        }
    }
}
