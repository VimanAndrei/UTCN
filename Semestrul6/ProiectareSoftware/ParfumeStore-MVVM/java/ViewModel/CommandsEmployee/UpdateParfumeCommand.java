package ViewModel.CommandsEmployee;

import Model.*;
import ViewModel.ICommand;
import ViewModel.VMEmployee;

import javax.swing.*;

public class UpdateParfumeCommand implements ICommand {
    private ParfumeService parfumeService;
    private VMEmployee vmEmployee;

    public UpdateParfumeCommand(VMEmployee vmEmployee, ParfumeService parfumeService) {
        this.parfumeService = parfumeService;
        this.vmEmployee = vmEmployee;
    }

    @Override
    public void Execute() {
        String storeNameOld = vmEmployee.getSelectedStoreName();
        String parfumeNameOld = vmEmployee.getSelectedParfumeName();
        String manufacturerNameOld = vmEmployee.getSelectedManufacturerName();

        String parfumeAmountOld = vmEmployee.getSelectedParfumeAmount();

        if(storeNameOld == null||parfumeNameOld== null||manufacturerNameOld== null||parfumeAmountOld== null){
            JOptionPane.showMessageDialog(null, "Nu ati selectat nici o linie din tabela!");
        }else{

                ParfumeStore parfumeStoreOld = parfumeService.getParfumeStoreByStrings(storeNameOld, parfumeNameOld, manufacturerNameOld,parfumeAmountOld);
                if (parfumeStoreOld == null) {
                    JOptionPane.showMessageDialog(null, "Parfum negasit, nu poate avea loc actualizarea!");
                } else {
                    String storeNameNew  = vmEmployee.getStoreName().get();
                    String parfumeNameNew  = vmEmployee.getParfumeName().get();
                    String manufacturerNameNew  = vmEmployee.getManufacturerName().get();
                    String numberOfCopiesNew  = vmEmployee.getNumberOfCopies().get();
                    String barCodeNew  = vmEmployee.getBarCode().get();
                    String priceNew  = vmEmployee.getPrice().get();
                    String parfumeAmountNew  = vmEmployee.getParfumeAmount().get();
                    String numberOfSoldCopiesNew  = vmEmployee.getNumberOfSoldCopies().get();

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

                        parfumeService.updateParfume(parfumeStoreOld, parfumeStoreNew);

                    }else{
                        JOptionPane.showMessageDialog(null, "Nu poate avea loc actualizarea!");
                    }

                }



        }


    }
}
