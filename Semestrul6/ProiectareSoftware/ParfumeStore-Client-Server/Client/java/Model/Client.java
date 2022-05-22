package Model;

import java.io.*;
import java.net.Socket;

public class Client {
    private int port;
    private String ipAddress;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client() {
        this.port = 2222;
        this.ipAddress = "127.0.0.2";
    }

    public Client(int port, String ipAddress) {
        this.port = port;
        this.ipAddress = ipAddress;
    }

    public void connectClient() {
        try {
            this.socket = new Socket(ipAddress, port);
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String listenForMessage() {
        String message=null;
        try {
            message = bufferedReader.readLine();
        } catch (IOException e) {
            closeAll(socket, bufferedReader, bufferedWriter);
        }
        return message;
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
}
