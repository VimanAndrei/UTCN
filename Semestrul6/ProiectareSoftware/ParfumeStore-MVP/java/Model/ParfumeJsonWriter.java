package Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public interface ParfumeJsonWriter {
    String fileName = "src/main/java/parfumes.json";

    @SuppressWarnings("unchecked")
    default void saveAsJSON(List<ParfumeStore> parfumeStoreList) {
        try {
            FileWriter file = new FileWriter(fileName);
            JSONObject obj ;

            for (ParfumeStore ps : parfumeStoreList) {
                obj = new JSONObject();
                obj.put("StorName", ps.getStoreName());
                for (Parfume p : ps.getParfumes()) {
                    JSONArray company = new JSONArray();
                    company.add("ParfumeName: " + p.getParfumeName());
                    company.add("ManufacturerName: " + p.getParfumeInfo().getManufacturerName());
                    company.add("NumberOfCopies: " + p.getParfumeInfo().getNumberOfCopies());
                    company.add("BarCode: " + p.getParfumeInfo().getBarCode());
                    company.add("Price: " + p.getParfumeInfo().getPrice());
                    company.add("ParfumeAmount: " + p.getParfumeInfo().getParfumeAmount());
                    company.add("NumberOfSoldCopies: " + p.getParfumeInfo().getNumberOfSoldCopies());
                    obj.put("ParfumeSpecs: ", company);
                    file.write(obj.toJSONString());
                }
            }

            file.flush();
            file.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
