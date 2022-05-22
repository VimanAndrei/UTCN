package Model.Parfume;

import Model.Writer.ParfumeCsvWriter;
import Model.Writer.ParfumeJsonWriter;
import Model.Writer.ParfumeXmlWriter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ParfumeService extends ParfumeDAO implements ParfumeCsvWriter, ParfumeXmlWriter, ParfumeJsonWriter {
    private List<ParfumeStore> parfumeStoreList;

    public ParfumeService() {
        this.parfumeStoreList = new ArrayList<>();
        this.parfumeStoreList = this.findAllParfumes();
    }

    public boolean insertParfume(ParfumeStore parfumeStore){
        Parfume parfume = parfumeStore.getParfumes().get(0);
        for (ParfumeStore ps:parfumeStoreList) {

            if (ps.getStoreName().equals(parfumeStore.getStoreName())){

                for (Parfume p:ps.getParfumes()) {
                    if (p.getParfumeName().equals(parfume.getParfumeName()) && p.getParfumeInfo().getManufacturerName().equals(parfume.getParfumeInfo().getManufacturerName())){
                        return false;
                    }
                }
                this.insert(parfumeStore);
                parfumeStoreList = this.findAllParfumes();
                return true;
            }
        }
        this.insert(parfumeStore);
        parfumeStoreList = this.findAllParfumes();
        return true;
    }

    public void deleteParfume(String selectedStoreName, String selectedParfumeName, String selectedManufacturerName, String selectedParfumeAmount) {
        this.delete(selectedStoreName,selectedParfumeName,selectedManufacturerName,selectedParfumeAmount);
        this.parfumeStoreList = this.findAllParfumes();
    }

    public ParfumeStore getParfumeStoreByStrings(String storName,String parfumeName, String manufacturerName,String parfumeAmount){
        ParfumeStore parfumeStore = null;
        for (ParfumeStore ps:parfumeStoreList) {
            if (ps.getStoreName().equals(storName)){
                List <Parfume> parfumes = ps.getParfumes();
                for (Parfume p:parfumes) {
                    if(p.getParfumeName().equals(parfumeName) && p.getParfumeInfo().getManufacturerName().equals(manufacturerName)
                            &&p.getParfumeInfo().getParfumeAmount().equals(Integer.parseInt(parfumeAmount))){
                        parfumeStore = new ParfumeStore(ps.getStoreName());
                        parfumeStore.addParfumeToStore(p);
                    }
                }
            }
        }

        return parfumeStore;
    }

    public void updateParfume(ParfumeStore parfumeStoreOld, ParfumeStore parfumeStoreNew) {
        int l1 = parfumeStoreList.size();
        this.deleteParfume(parfumeStoreOld.getStoreName(),parfumeStoreOld.getParfumes().get(0).getParfumeName(),parfumeStoreOld.getParfumes().get(0).getParfumeInfo().getManufacturerName(),parfumeStoreOld.getParfumes().get(0).getParfumeInfo().getParfumeAmount().toString());
        int l2 = parfumeStoreList.size();
        if(l1!=l2) {
            this.insert(parfumeStoreNew);
            this.parfumeStoreList = findAllParfumes();
        }
    }
}
