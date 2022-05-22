package Model.Parfume;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ParfumeStore {
    private String storeName;
    private List<Parfume> parfumes;

    public ParfumeStore(String storeName) {
        this.storeName = storeName;
        this.parfumes = new ArrayList<>();
    }

    public void addParfumeToStore(Parfume parfume) {
        this.parfumes.add(parfume);

    }
}
