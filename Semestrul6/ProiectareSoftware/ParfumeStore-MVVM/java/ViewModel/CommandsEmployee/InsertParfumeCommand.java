package ViewModel.CommandsEmployee;

import Model.*;
import ViewModel.ICommand;
import ViewModel.VMEmployee;

import javax.swing.*;

public class InsertParfumeCommand implements ICommand {
    private VMEmployee vmEmployee;
    private ParfumeService parfumeService;

    public InsertParfumeCommand(VMEmployee vmEmployee, ParfumeService parfumeService) {
        this.vmEmployee = vmEmployee;
        this.parfumeService = parfumeService;
    }

    @Override
    public void Execute() {
        if(vmEmployee.getStoreName().get().isEmpty() || vmEmployee.getParfumeName().get().isEmpty() ||
                vmEmployee.getManufacturerName().get().isEmpty() || vmEmployee.getNumberOfCopies().get().isEmpty() ||
                vmEmployee.getBarCode().get().isEmpty() || vmEmployee.getPrice().get().isEmpty()||
                vmEmployee.getParfumeAmount().get().isEmpty() || vmEmployee.getNumberOfSoldCopies().get().isEmpty()){
            JOptionPane.showMessageDialog(null, "Unul dintre campuri nu este completat!");
        }else{

            try {
                String storeName = vmEmployee.getStoreName().get();
                String parfumeName = vmEmployee.getParfumeName().get();
                String manufacturerName =  vmEmployee.getManufacturerName().get();
                Integer numberOfCopies = Integer.parseInt(vmEmployee.getNumberOfCopies().get());
                Integer barCode = Integer.parseInt(vmEmployee.getBarCode().get());
                Double price = Double.parseDouble(vmEmployee.getPrice().get());
                Integer parfumeAmount = Integer.parseInt(vmEmployee.getParfumeAmount().get());
                Integer numberOfSoldCopies = Integer.parseInt(vmEmployee.getNumberOfSoldCopies().get());
                ParfumeInfo parfumeInfo = new ParfumeInfoBuilder().setManufacturerName(manufacturerName).setNumberOfCopies(numberOfCopies).setBarCode(barCode).setPrice(price).setParfumeAmount(parfumeAmount).setNumberOfSoldCopies(numberOfSoldCopies).build();
                Parfume parfume = new ParfumeBuilder().setParfumeName(parfumeName).setParfumeInfo(parfumeInfo).build();
                ParfumeStore parfumeStore = new ParfumeStore(storeName);
                parfumeStore.addParfumeToStore(parfume);
                if(!parfumeService.insertParfume(parfumeStore)){
                    JOptionPane.showMessageDialog(null, "Nu poate fi inserat acest parfum!");
                }
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Adaugati un numar !");
            }


        }
    }
}
