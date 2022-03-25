package bll;

import model.Clients;
import model.Orders;
import model.Products;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

public class Bill {
    private ClientBLL cb;
    private OrderBLL ob;
    private ProductsBLL pb;
    int nrOrdine;

    public Bill() {
        this.cb = new ClientBLL();
        this.ob = new OrderBLL();
        this.pb = new ProductsBLL();
        this.nrOrdine = 0;
    }

    public void chitanta() {

        List<Clients> aa = new ArrayList<Clients>();
        List<Orders> bb = new ArrayList<Orders>();
        List<Products> cc = new ArrayList<Products>();

        aa = cb.selectAll();
        cc = pb.selectAll();
        bb = ob.selectAll();
        try {
            for (Clients b : aa) {
                nrOrdine++;
                Document document = new Document();

                PdfWriter.getInstance(document, new FileOutputStream("Bon" + nrOrdine + ".pdf"));
                document.open();

                String afis = new String();
                afis += b.getName() + "\n";
                double totalplata = 0;
                for (Orders o : bb) {
                    if (b.getId() == o.getClientId()) {
                        for (Products d : cc) {
                            if (o.getProductId() == d.getId()) {
                                double totalp = o.getQuantity() * d.getPrice();
                                afis = afis + d.getProductName() + " " + d.getPrice() + " lei/buc " + o.getQuantity() + " buc =" + totalp + " lei " + "\n";
                                totalplata += totalp;
                            }
                        }
                    }
                }
                afis = afis + "Total plata= " + totalplata + "\n";
                Paragraph aaa = new Paragraph(afis);
                document.add(aaa);
                document.close();

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
