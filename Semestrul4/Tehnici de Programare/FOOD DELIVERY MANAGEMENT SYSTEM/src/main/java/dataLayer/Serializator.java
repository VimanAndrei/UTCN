package dataLayer;

import java.io.*;

public class Serializator {

    public void serialize(String fileName, Object obj)  {
        FileOutputStream file = null;
        ObjectOutputStream out = null;
        try {
            file = new FileOutputStream(fileName);
            out = new ObjectOutputStream(file);
            out.writeObject(obj);
            out.flush();
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object deserialize(String fileName)  {
        Object obj = null;
        FileInputStream file = null;
        ObjectInputStream in = null;
        try {
            file = new FileInputStream(fileName);
            in = new ObjectInputStream(file);
            obj = in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
