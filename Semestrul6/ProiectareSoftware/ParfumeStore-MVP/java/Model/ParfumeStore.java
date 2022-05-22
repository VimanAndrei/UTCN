package Model;

import java.util.ArrayList;
import java.util.List;

public class ParfumeStore {
    private String storeName;
    private List<Parfume> parfumes;

    public ParfumeStore() {
    }

    public ParfumeStore(String storeName, List<Parfume> parfumes) {
        this.storeName = storeName;
        this.parfumes = parfumes;
    }

    public ParfumeStore(String storeName) {
        this.storeName = storeName;
        this.parfumes = new ArrayList<>();
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<Parfume> getParfumes() {
        return parfumes;
    }

    public void setParfumes(List<Parfume> parfumes) {
        this.parfumes = parfumes;
    }

    public void addParfumeToStore(Parfume parfume){
        this.parfumes.add(parfume);
    }

    public void removeParfumeFromStore(Parfume parfume){
        this.parfumes.remove(parfume);
    }

    @Override
    public String toString() {

        String result ="";

        result = "ParfumeStore{" +
                "storeName='" + storeName + ",\n" +
                "parfumes="+"\n";
        for(Parfume p: parfumes){
            result+="   ";
            result+=p;
            result+="\n";
        }
        result+="}\n";

        return result;
    }
}
