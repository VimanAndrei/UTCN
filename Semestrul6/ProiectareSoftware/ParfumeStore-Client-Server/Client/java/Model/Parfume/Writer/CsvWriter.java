package Model.Parfume.Writer;

import Model.Parfume.Parfume;
import Model.Parfume.ParfumeStore;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CsvWriter implements IWriter{
    private final String fileName="src/main/java/parfumes.csv";
    @Override
    public void saveData(List<ParfumeStore> parfumeStoreList) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            StringBuilder sb = new StringBuilder();
            sb.append("storName");
            sb.append(',');
            sb.append("parfumeName");
            sb.append(',');
            sb.append("manufacturerName");
            sb.append(',');
            sb.append("numberOfCopies");
            sb.append(',');
            sb.append("barCode");
            sb.append(',');
            sb.append("price");
            sb.append(',');
            sb.append("parfumeAmount");
            sb.append(',');
            sb.append("numberOfSoldCopies");
            sb.append('\n');

            for (ParfumeStore ps : parfumeStoreList) {
                for (Parfume p : ps.getParfumes()) {
                    sb.append(ps.getStoreName());
                    sb.append(',');
                    sb.append(p.getParfumeName());
                    sb.append(',');
                    sb.append(p.getParfumeInfo().getManufacturerName());
                    sb.append(',');
                    sb.append(p.getParfumeInfo().getNumberOfCopies().toString());
                    sb.append(',');
                    sb.append(p.getParfumeInfo().getBarCode().toString());
                    sb.append(',');
                    sb.append(p.getParfumeInfo().getPrice().toString());
                    sb.append(',');
                    sb.append(p.getParfumeInfo().getParfumeAmount().toString());
                    sb.append(',');
                    sb.append(p.getParfumeInfo().getNumberOfSoldCopies().toString());
                    sb.append('\n');
                }
            }
            writer.write(sb.toString());
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
