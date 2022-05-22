package Model.Parfume;

import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Language {
    private String language;

    public Language() {
        this.language = "romana";
    }
    public Language(String language) {
        this.language = language;
    }

    public List<String> getLanguageComponent(){
        List<String> parfumesLanguageList = new ArrayList<>();
        try {

            File file = new File("src/main/java/languagesP.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName(language);


            Node node = nodeList.item(0);
            Element eElement = (Element) node;

            parfumesLanguageList.add(eElement.getElementsByTagName("nume_producator").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("produse_ramase").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("cod_de_bare").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("pret").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("dimensiunea").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("produse_vandute").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("nume_parfum").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("nume_magazin").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("actualizare").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("salvare").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("stergere").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("afisare_parfmuri").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("afisare_parfmuri_magazin").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("salvare_formate").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("filtrare_producator").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("filtrare_disponibilitate").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("cautare_parfum").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("filtrare_pret").item(0).getTextContent());
            parfumesLanguageList.add(eElement.getElementsByTagName("statistici").item(0).getTextContent());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return parfumesLanguageList;
    }


}
