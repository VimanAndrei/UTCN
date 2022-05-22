package ViewModel.CommandsEmployee;

import Model.ParfumeService;
import ViewModel.ICommand;
import ViewModel.VMEmployee;

public class SaveAsOtherFilesCommand implements ICommand {
    private VMEmployee vmEmployee;
    private ParfumeService parfumeService;
    public SaveAsOtherFilesCommand(VMEmployee vmEmployee, ParfumeService parfumeService) {
        this.vmEmployee = vmEmployee;
        this.parfumeService = parfumeService;
    }

    @Override
    public void Execute() {
        parfumeService.saveAsCSV(parfumeService.getParfumeStoreList());
        parfumeService.saveAsJSON(parfumeService.getParfumeStoreList());
        parfumeService.saveAsXML(parfumeService.getParfumeStoreList());
    }
}
