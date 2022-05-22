package Model.Parfume.Writer;

import Model.Parfume.Parfume;
import Model.Parfume.ParfumeStore;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlWriter implements IWriter{
    String fileName="src/main/java/parfumes.xml";
    private Document getDocumentElements() {
        Document doc = null;
        try {

            File file = new File(fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(file);

        } catch (Exception e) {
            e.printStackTrace();
            doc = null;
        }
        return doc;
    }
    private void saveXmlContent(Document d) {
        String path = "src/main/java/parfumes.xml";


        try {
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource ds = new DOMSource(d);
            StreamResult sr = new StreamResult(path);
            tf.transform(ds, sr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addParfumeToDocument(List<ParfumeStore> parfumeStore){
        Document document = getDocumentElements();
        Element parfumes = document.getDocumentElement();
        //Element parfume = document.createElement("parfume");

        List <ParfumeStore> parfumesList = new ArrayList<>();
        for (ParfumeStore ps : parfumeStore) {
            for (Parfume p : ps.getParfumes()) {

                ParfumeStore parfumeStore1 = new ParfumeStore(ps.getStoreName());
                parfumeStore1.addParfumeToStore(p);
                parfumesList.add(parfumeStore1);
            }
        }

        for (ParfumeStore ps:parfumesList) {
            Element parfume = document.createElement("parfume");
            Element storName = document.createElement("storName");
            storName.appendChild(document.createTextNode(ps.getStoreName()));
            parfume.appendChild(storName);

            List<Parfume> parf = ps.getParfumes();
            for (Parfume p : parf) {
                Element parfumeName = document.createElement("parfumeName");
                parfumeName.appendChild(document.createTextNode(p.getParfumeName()));
                parfume.appendChild(parfumeName);

                Element manufacturerName = document.createElement("manufacturerName");
                manufacturerName.appendChild(document.createTextNode(p.getParfumeInfo().getManufacturerName()));
                parfume.appendChild(manufacturerName);

                Element numberOfCopies = document.createElement("numberOfCopies");
                numberOfCopies.appendChild(document.createTextNode(p.getParfumeInfo().getNumberOfCopies().toString()));
                parfume.appendChild(numberOfCopies);

                Element barCode = document.createElement("barCode");
                barCode.appendChild(document.createTextNode(p.getParfumeInfo().getBarCode().toString()));
                parfume.appendChild(barCode);

                Element price = document.createElement("price");
                price.appendChild(document.createTextNode(p.getParfumeInfo().getPrice().toString()));
                parfume.appendChild(price);

                Element parfumeAmount = document.createElement("parfumeAmount");
                parfumeAmount.appendChild(document.createTextNode(p.getParfumeInfo().getParfumeAmount().toString()));
                parfume.appendChild(parfumeAmount);

                Element numberOfSoldCopies = document.createElement("numberOfSoldCopies");
                numberOfSoldCopies.appendChild(document.createTextNode(p.getParfumeInfo().getNumberOfSoldCopies().toString()));
                parfume.appendChild(numberOfSoldCopies);

            }
            parfumes.appendChild(parfume);
        }


        saveXmlContent(document);
    }

    @Override
    public void saveData(List<ParfumeStore> parfumeStoreList) {
        Document d = getDocumentElements();
        NodeList nl = d.getElementsByTagName("parfume");
        for (int i = 0; i < nl.getLength(); i++) {
            Element eparfume = (Element) nl.item(i);
            eparfume.getParentNode().removeChild(eparfume);
        }
        saveXmlContent(d);

        addParfumeToDocument(parfumeStoreList);
    }
}
