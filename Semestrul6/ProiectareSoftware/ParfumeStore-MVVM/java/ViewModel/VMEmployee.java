package ViewModel;

import Model.ParfumeService;
import ViewModel.CommandsAdministrator.DeleteCommand;
import ViewModel.CommandsEmployee.*;
import lombok.Getter;
import lombok.Setter;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;

@Getter
@Setter
public class VMEmployee {

    private Property<String> storeName = PropertyFactory.createProperty("storeName", this, String.class);
    private Property<String> parfumeName = PropertyFactory.createProperty("parfumeName", this, String.class);
    private Property<String> manufacturerName = PropertyFactory.createProperty("manufacturerName", this, String.class);
    private Property<String> numberOfCopies = PropertyFactory.createProperty("numberOfCopies", this, String.class);
    private Property<String> barCode = PropertyFactory.createProperty("barCode", this, String.class);
    private Property<String> price = PropertyFactory.createProperty("price", this, String.class);
    private Property<String> parfumeAmount  = PropertyFactory.createProperty("parfumeAmount", this, String.class);
    private Property<String> numberOfSoldCopies = PropertyFactory.createProperty("numberOfSoldCopies", this, String.class);

    private String selectedStoreName ;
    private String selectedParfumeName;
    private String selectedManufacturerName;
    private String selectedNumberOfCopies ;
    private String selectedBarCode;
    private String selectedPrice;
    private String selectedParfumeAmount;
    private String selectedNumberOfSoldCopies;

    private ParfumeService parfumeService;
    private Object[][] data = null;

    public ICommand listAllCommand;
    public ICommand insertCommand;
    public ICommand deleteCommand;
    public ICommand updateCommand;
    public ICommand searchByParfumeNameCommand;
    public ICommand filterByNumOfCopies;
    public ICommand filterByManufacturerName;
    public ICommand filterByPrice;
    public ICommand filterByStoreNameCommand;
    public ICommand saveAsOtherFiles;
    public ICommand chartCommand;

    public VMEmployee() {
        parfumeService = new ParfumeService();
        listAllCommand = new ListAllParfumeCommand(this,parfumeService);
        insertCommand = new InsertParfumeCommand(this,parfumeService);
        deleteCommand = new DeleteParfumeCommand(this,parfumeService);
        updateCommand = new UpdateParfumeCommand(this,parfumeService);
        searchByParfumeNameCommand = new SearchByParfumeNameCommand(this,parfumeService);
        filterByNumOfCopies = new FilterByNumOfCopiesCommand(this,parfumeService);
        filterByManufacturerName = new FilterByManufacturerNameCommand(this,parfumeService);
        filterByPrice = new FilterByPriceCommand(this,parfumeService);
        filterByStoreNameCommand = new FilterByStoreNameCommand(this,parfumeService);
        saveAsOtherFiles = new SaveAsOtherFilesCommand(this,parfumeService);
        chartCommand = new ChartCommand(this,parfumeService);

    }


}
