package Model;

import java.util.List;

public class ParfumeService extends ParfumeServiceInterface implements ParfumeCsvWriter,ParfumeJsonWriter {
    private List<ParfumeStore> parfumeStoreList;

    public ParfumeService() {
        this.parfumeStoreList = readXmlFile();
    }

    public List<ParfumeStore> getParfumeStoreList() {
        return parfumeStoreList;
    }

    public boolean addNewParfume(ParfumeStore parfumeStore){
        Parfume parfume = parfumeStore.getParfumes().get(0);
        for (ParfumeStore ps:parfumeStoreList) {

            if (ps.getStoreName().equals(parfumeStore.getStoreName())){

                for (Parfume p:ps.getParfumes()) {
                    if (p.getParfumeName().equals(parfume.getParfumeName()) && p.getParfumeInfo().getManufacturerName().equals(parfume.getParfumeInfo().getManufacturerName())){
                        return false;
                    }
                }
                addParfumeToDocument(parfumeStore);
                parfumeStoreList = readXmlFile();
                return true;
            }
        }
        addParfumeToDocument(parfumeStore);
        parfumeStoreList = readXmlFile();
        return true;
    }

    public ParfumeStore getParfumeStoreByStrings(String storName,String parfumeName, String manufacturerName){
        ParfumeStore parfumeStore = null;
        for (ParfumeStore ps:parfumeStoreList) {
            if (ps.getStoreName().equals(storName)){
                List <Parfume> parfumes = ps.getParfumes();
                for (Parfume p:parfumes) {
                    if(p.getParfumeName().equals(parfumeName) && p.getParfumeInfo().getManufacturerName().equals(manufacturerName)){
                        parfumeStore = new ParfumeStore(ps.getStoreName());
                        parfumeStore.addParfumeToStore(p);
                    }
                }
            }
        }

        return parfumeStore;
    }

    public void deleteParfume(ParfumeStore parfumeStore) {
        deleteParfumeFromDocument(parfumeStore);
        parfumeStoreList = readXmlFile();
    }

    public void updateParfume(ParfumeStore oldParfume, ParfumeStore newParfume){
        deleteParfume(oldParfume);
        addNewParfume(newParfume);
    }

    @Override
    public String toString() {
        String result ="";

        for (ParfumeStore ps:parfumeStoreList) {
            result+=ps+"\n";
        }
        return result;
    }

}
