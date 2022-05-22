package Model.Parfume;

import Model.Client;
import Model.Parfume.Writer.CsvWriter;
import Model.Parfume.Writer.IWriter;
import Model.Parfume.Writer.JsonWriter;
import Model.Parfume.Writer.XmlWriter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ParfumeService implements IWriter {
    private List<ParfumeStore> parfumeStoreList;
    private Client client;

    public ParfumeService(Client client) {
        this.client = client;
        this.parfumeStoreList = new ArrayList<>();
        this.client.sendMessage("startParfume ");
        this.parfumeStoreList = this.constructParfumes(this.client.listenForMessage());
    }

    private List<ParfumeStore> constructParfumes(String message) {
        List<ParfumeStore> list = new ArrayList<>();
        String[] splited = message.split(" ");
        for (int i = 0; i < splited.length; i += 8) {
            String storeName = splited[i];
            String parfumeName = splited[i + 1];
            String manufacturerName = splited[i + 2];
            String numberOfCopies = splited[i + 3];
            String barCode = splited[i + 4];
            String price = splited[i + 5];
            String parfumeAmount = splited[i + 6];
            String numberOfSoldCopies = splited[i + 7];

            ParfumeInfo parfumeInfo = new ParfumeInfoBuilder().setManufacturerName(manufacturerName).setNumberOfCopies(Integer.parseInt(numberOfCopies)).setBarCode(Integer.parseInt(barCode)).setPrice(Double.parseDouble(price)).setParfumeAmount(Integer.parseInt(parfumeAmount)).setNumberOfSoldCopies(Integer.parseInt(numberOfSoldCopies)).build();
            Parfume parfume = new ParfumeBuilder().setParfumeName(parfumeName).setParfumeInfo(parfumeInfo).build();

            boolean gasit = false;
            int index = -1;
            for (ParfumeStore ps : list) {
                if (ps.getStoreName().equals(storeName)) {
                    gasit = true;
                    index = list.indexOf(ps);
                }
            }
            if (!gasit) {
                ParfumeStore parfumeStore = new ParfumeStore(storeName);
                parfumeStore.addParfumeToStore(parfume);
                list.add(parfumeStore);
            } else {
                list.get(index).addParfumeToStore(parfume);
            }
        }
        return list;
    }

    private void insertHelper(ParfumeStore parfumeStore) {
        this.client.sendMessage("insertParfume " + parfumeStore.getStoreName() + " " +
                parfumeStore.getParfumes().get(0).getParfumeName() + " " +
                parfumeStore.getParfumes().get(0).getParfumeInfo().getManufacturerName() + " " +
                parfumeStore.getParfumes().get(0).getParfumeInfo().getNumberOfCopies() + " " +
                parfumeStore.getParfumes().get(0).getParfumeInfo().getBarCode() + " " +
                parfumeStore.getParfumes().get(0).getParfumeInfo().getPrice() + " " +
                parfumeStore.getParfumes().get(0).getParfumeInfo().getParfumeAmount() + " " +
                parfumeStore.getParfumes().get(0).getParfumeInfo().getNumberOfSoldCopies());
        this.client.sendMessage("startParfume ");
        this.parfumeStoreList = this.constructParfumes(this.client.listenForMessage());
    }

    public boolean insertParfume(ParfumeStore parfumeStore) {
        Parfume parfume = parfumeStore.getParfumes().get(0);
        for (ParfumeStore ps : parfumeStoreList) {

            if (ps.getStoreName().equals(parfumeStore.getStoreName())) {

                for (Parfume p : ps.getParfumes()) {
                    if (p.getParfumeName().equals(parfume.getParfumeName()) && p.getParfumeInfo().getManufacturerName().equals(parfume.getParfumeInfo().getManufacturerName())) {
                        return false;
                    }
                }
                this.insertHelper(parfumeStore);
                return true;
            }
        }
        this.insertHelper(parfumeStore);
        return true;
    }

    public void deleteParfume(String selectedStoreName, String selectedParfumeName, String selectedManufacturerName, String selectedParfumeAmount) {
        this.client.sendMessage("deleteParfume " + selectedStoreName + " " + selectedParfumeName + " " +
                selectedManufacturerName + " " + selectedParfumeAmount);

        this.client.sendMessage("startParfume ");
        this.parfumeStoreList = this.constructParfumes(this.client.listenForMessage());

    }

    public ParfumeStore getParfumeStoreByStrings(String storName, String parfumeName, String manufacturerName, String parfumeAmount) {
        ParfumeStore parfumeStore = null;
        for (ParfumeStore ps : parfumeStoreList) {
            if (ps.getStoreName().equals(storName)) {
                List<Parfume> parfumes = ps.getParfumes();
                for (Parfume p : parfumes) {
                    if (p.getParfumeName().equals(parfumeName) && p.getParfumeInfo().getManufacturerName().equals(manufacturerName)
                            && p.getParfumeInfo().getParfumeAmount().equals(Integer.parseInt(parfumeAmount))) {
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
        this.deleteParfume(parfumeStoreOld.getStoreName(), parfumeStoreOld.getParfumes().get(0).getParfumeName(), parfumeStoreOld.getParfumes().get(0).getParfumeInfo().getManufacturerName(), parfumeStoreOld.getParfumes().get(0).getParfumeInfo().getParfumeAmount().toString());
        int l2 = parfumeStoreList.size();
        if (l1 != l2) {
            this.insertParfume(parfumeStoreNew);
        }
    }

    public List<ParfumeStore> filterByStoreName(String storeName) {
        this.client.sendMessage("filterParfume " + storeName + " ");
        return this.constructParfumes(this.client.listenForMessage());
    }

    @Override
    public void saveData(List<ParfumeStore> parfumeStoreList) {
        IWriter csvWriter = new CsvWriter();
        IWriter xmlWriter = new XmlWriter();
        IWriter jsonWriter = new JsonWriter();

        csvWriter.saveData(parfumeStoreList);
        xmlWriter.saveData(parfumeStoreList);
        jsonWriter.saveData(parfumeStoreList);
    }
}
