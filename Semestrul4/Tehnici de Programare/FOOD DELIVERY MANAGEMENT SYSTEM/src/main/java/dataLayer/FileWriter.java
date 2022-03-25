package dataLayer;

import businessLayer.BaseProduct;
import businessLayer.Order;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import model.LoggingAccount;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileWriter {
    private String fileName = "products.csv";
    private String format = "[,]";

    public HashSet<BaseProduct> readDataFromFile(){
            HashSet<BaseProduct> products = new HashSet<>();
            Pattern p1 = Pattern.compile(format);
            try(Stream<String>lines = Files.lines(Path.of(fileName))) {
             List<BaseProduct> product = lines.skip(1).map(line -> {
                    String[] arr = p1.split(line);
                    return new BaseProduct(
                          arr[0],
                          Double.parseDouble(arr[1]),
                          Integer.parseInt(arr[2]),
                          Integer.parseInt(arr[3]),
                          Integer.parseInt(arr[4]),
                          Integer.parseInt(arr[5]),
                          Double.parseDouble(arr[6]));
                }).collect(Collectors.toList());
             for(BaseProduct bp : product) {
                 products.add(bp);
             }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return products;
    }

    public void createBill(Order o, List<BaseProduct> lista, LoggingAccount client,double price){
        try {

                Document document = new Document();

                PdfWriter.getInstance(document, new FileOutputStream("Bon" + o.getOrderId() + ".pdf"));
                document.open();

                String afis = new String();
                afis = afis +"Bonul pentru clientul cu numele:"+ client.getUserName() + "\n";
                afis = afis + "Are urmatoarele produse comandate:\n";
                for(BaseProduct b  : lista){
                    afis= afis+"Produsul: "+ b.getTitle()+ " cu pretul: "+b.getPrice()+"\n";
                }

                afis = afis + "Cu totalul de plata: "+ price + "\n";
                afis = afis + "Data: "+ o.getOrderDate() + "\n";

            Paragraph aaa = new Paragraph(afis);
            document.add(aaa);
            document.close();

        } catch (DocumentException documentException) {
            documentException.printStackTrace();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

    }

}
