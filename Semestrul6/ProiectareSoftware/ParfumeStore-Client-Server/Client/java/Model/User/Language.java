package Model.User;

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
        List<String> usersLanguageList = new ArrayList<>();
        try {

            File file = new File("src/main/java/languagesUsers.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName(language);


            Node node = nodeList.item(0);
            Element eElement = (Element) node;

            usersLanguageList.add(eElement.getElementsByTagName("nume_utilizator").item(0).getTextContent());
            usersLanguageList.add(eElement.getElementsByTagName("parola").item(0).getTextContent());
            usersLanguageList.add(eElement.getElementsByTagName("rolul").item(0).getTextContent());
            usersLanguageList.add(eElement.getElementsByTagName("salvare").item(0).getTextContent());
            usersLanguageList.add(eElement.getElementsByTagName("stergere").item(0).getTextContent());
            usersLanguageList.add(eElement.getElementsByTagName("actualizare").item(0).getTextContent());
            usersLanguageList.add(eElement.getElementsByTagName("afisare_utilizatori").item(0).getTextContent());
            usersLanguageList.add(eElement.getElementsByTagName("filtrere").item(0).getTextContent());
            usersLanguageList.add(eElement.getElementsByTagName("administrator").item(0).getTextContent());
            usersLanguageList.add(eElement.getElementsByTagName("angajat").item(0).getTextContent());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersLanguageList;
    }


}
