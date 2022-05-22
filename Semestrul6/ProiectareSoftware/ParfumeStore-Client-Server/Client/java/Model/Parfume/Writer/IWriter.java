package Model.Parfume.Writer;

import Model.Parfume.ParfumeStore;

import java.util.List;

public interface IWriter {
    void saveData(List<ParfumeStore> parfumeStoreList);
}
