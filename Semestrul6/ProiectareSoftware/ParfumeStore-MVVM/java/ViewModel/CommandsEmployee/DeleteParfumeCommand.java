package ViewModel.CommandsEmployee;

import Model.ParfumeService;
import ViewModel.ICommand;
import ViewModel.VMEmployee;

import javax.swing.*;

public class DeleteParfumeCommand implements ICommand {
    private ParfumeService parfumeService;
    private VMEmployee vmEmployee;

    public DeleteParfumeCommand(VMEmployee vmEmployee,ParfumeService parfumeService) {
        this.parfumeService = parfumeService;
        this.vmEmployee = vmEmployee;
    }

    @Override
    public void Execute() {
        if (vmEmployee.getSelectedStoreName() == null || vmEmployee.getSelectedParfumeName() == null ||
            vmEmployee.getSelectedManufacturerName()==null || vmEmployee.getSelectedParfumeAmount() == null){
            JOptionPane.showMessageDialog(null, "Nu ati selectat nici o linie din tabela!");
        }else{
            parfumeService.deleteParfume(vmEmployee.getSelectedStoreName(),vmEmployee.getSelectedParfumeName(),vmEmployee.getSelectedManufacturerName(),vmEmployee.getSelectedParfumeAmount());

        }
    }
}
