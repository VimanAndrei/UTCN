package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;

import org.w3c.dom.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public abstract class UserServiceInterface {

    public static Document getDocumentElements() {
        Document doc = null;
        try {

            File file = new File("src/main/java/users.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(file);

        } catch (Exception e) {
            e.printStackTrace();
            doc = null;
        }
        return doc;
    }

    public static void saveXmlContent(Document d) {
        String path = "src/main/java/users.xml";


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


    public static List<User> readXmlFile() {
        Document doc = getDocumentElements();
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("user");
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String username = eElement.getElementsByTagName("username").item(0).getTextContent();
                String password = eElement.getElementsByTagName("password").item(0).getTextContent();
                String role = eElement.getElementsByTagName("role").item(0).getTextContent();
                User u = new User(username, password, role);
                users.add(u);
            }
        }
        return users;
    }

    public static void addUserToDocument(User user) {
        Document document = getDocumentElements();
        Element users = document.getDocumentElement();
        Element u = document.createElement("user");

        Element username = document.createElement("username");
        username.appendChild(document.createTextNode(user.getUserName()));
        u.appendChild(username);

        Element password = document.createElement("password");
        password.appendChild(document.createTextNode(user.getPassword()));
        u.appendChild(password);

        Element role = document.createElement("role");
        role.appendChild(document.createTextNode(user.getRole()));
        u.appendChild(role);

        users.appendChild(u);
        saveXmlContent(document);


    }

    public static void deleteUserFromDocument(User u) {
        Document d = getDocumentElements();
        NodeList nl = d.getElementsByTagName("user");
        for (int i = 0; i < nl.getLength(); i++) {
            Element estud = (Element) nl.item(i);
            if (estud.getElementsByTagName("username").item(0).getTextContent().equals(u.getUserName())) {
                estud.getParentNode().removeChild(estud);
            }
        }

        saveXmlContent(d);
    }


}
