package Communication;

import Notification.Notification;
import Service.ParfumeDao;
import Service.UserDao;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private UserDao userDao;
    private ParfumeDao parfumeDao;
    private Notification notify;

    public ClientHandler(Socket socket) {
        try {
            this.notify = new Notification();
            this.userDao = new UserDao();
            this.parfumeDao = new ParfumeDao();
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }

    private void brodcastMessage(String message) {

        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeAll(socket, bufferedReader, bufferedWriter);
        }

    }


    public void closeAll(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                String[] splited = messageFromClient.split(" ");

                if (splited[0].equals("startUser")) {
                    String data = userDao.findAllUsers();
                    this.brodcastMessage(data);
                }
                if (splited[0].equals("insertUser")) {
                    userDao.insert(splited[1],splited[2],splited[3]);
                }
                if (splited[0].equals("deleteUser")) {
                    userDao.delete(splited[1]);
                }
                if (splited[0].equals("filterUser")) {
                    String data = userDao.filterUsers(splited[1]);
                    this.brodcastMessage(data);
                }
                if(splited[0].equals("updateUser")) {
                    String text = "" +
                            "Atentie!\n" +
                            "Utilizatorule, " + splited[1] + ", ti-i s-au schimbat credentialele de logare !\n" +
                            "Pentru mai multe informatii contactati administratorul.\n"+
                            "Noile credentiale sunt: Username: "+ splited[2]+" Parola: "+splited[3]+".";
                    this.notify.send(text);
                }




                if (splited[0].equals("startParfume")) {
                    String data = parfumeDao.findAllParfumes();
                    this.brodcastMessage(data);
                }
                if (splited[0].equals("insertParfume")) {
                    parfumeDao.insert(splited);
                }
                if (splited[0].equals("deleteParfume")) {
                    parfumeDao.delete(splited[1],splited[2],splited[3],splited[4]);
                }
                if (splited[0].equals("filterParfume")) {
                    String data = parfumeDao.filterByStoreName(splited[1]);
                    this.brodcastMessage(data);
                }


            } catch (IOException e) {
                closeAll(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

}
