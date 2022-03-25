package Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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

public abstract class ParfumeServiceInterface {

    public static Document getDocumentElements() {
        Document doc = null;
        try {

            File file = new File("src/main/java/parfumes.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(file);

        } catch (Exception e) {
            e.printStackTrace();
            doc = null;
        }
        return doc;
    }

    public static List<ParfumeStore> readXmlFile(){
        Document doc = getDocumentElements();
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("parfume");
        List<ParfumeStore> parfumeStoreList = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String storeName = eElement.getElementsByTagName("storName").item(0).getTextContent();
                String parfumeName = eElement.getElementsByTagName("parfumeName").item(0).getTextContent();
                String manufacturerName = eElement.getElementsByTagName("manufacturerName").item(0).getTextContent();
                String numberOfCopies = eElement.getElementsByTagName("numberOfCopies").item(0).getTextContent();
                String barCode = eElement.getElementsByTagName("barCode").item(0).getTextContent();
                String price = eElement.getElementsByTagName("price").item(0).getTextContent();
                String parfumeAmount  = eElement.getElementsByTagName("parfumeAmount").item(0).getTextContent();
                String numberOfSoldCopies = eElement.getElementsByTagName("numberOfSoldCopies").item(0).getTextContent();

                ParfumeInfo parfumeInfo = new ParfumeInfo(manufacturerName,Integer.parseInt(numberOfCopies),Integer.parseInt(barCode),Double.parseDouble(price),Integer.parseInt(parfumeAmount),Integer.parseInt(numberOfSoldCopies));
                Parfume parfume = new Parfume(parfumeName,parfumeInfo);
                boolean gasit = false;
                int index = -1;
                for (ParfumeStore ps:parfumeStoreList) {
                    if(ps.getStoreName().equals(storeName)){
                        gasit=true;
                        index = parfumeStoreList.indexOf(ps);
                    }
                }
                if(!gasit){
                ParfumeStore parfumeStore = new ParfumeStore(storeName);
                parfumeStore.addParfumeToStore(parfume);
                parfumeStoreList.add(parfumeStore);
                }else{
                    parfumeStoreList.get(index).addParfumeToStore(parfume);
                }
            }
        }

        return parfumeStoreList;
    }

    public static void saveXmlContent(Document d) {
        String path = "src/main/java/parfumes.xml";


        try {
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "yes");
            DOMSource ds = new DOMSource(d);
            StreamResult sr = new StreamResult(path);
            tf.transform(ds, sr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addParfumeToDocument(ParfumeStore parfumeStore){
        Document document = getDocumentElements();
        Element parfumes = document.getDocumentElement();
        Element parfume = document.createElement("parfume");

        Element storName = document.createElement("storName");
        storName.appendChild(document.createTextNode(parfumeStore.getStoreName()));
        parfume.appendChild(storName);

        List <Parfume> parf = parfumeStore.getParfumes();
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
        saveXmlContent(document);
    }

    public  static void deleteParfumeFromDocument(ParfumeStore parfumeStore){
        Document d = getDocumentElements();
        NodeList nl = d.getElementsByTagName("parfume");
        for (int i = 0; i < nl.getLength(); i++) {
            Element eparfume = (Element) nl.item(i);
            for (Parfume p:parfumeStore.getParfumes()) {
                if(eparfume.getElementsByTagName("storName").item(0).getTextContent().equals(parfumeStore.getStoreName()) &&
                   eparfume.getElementsByTagName("parfumeName").item(0).getTextContent().equals(p.getParfumeName()) &&
                   eparfume.getElementsByTagName("manufacturerName").item(0).getTextContent().equals(p.getParfumeInfo().getManufacturerName()) ){
                    eparfume.getParentNode().removeChild(eparfume);
                }
            }
        }
        saveXmlContent(d);
    }


}
