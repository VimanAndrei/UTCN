package Communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private ServerSocket serverSocket;

    public Server() {
        this.port = 2222;
    }

    public Server(int port){
        this.port = port;
    }

    public void startServer(){
        try {
            this.serverSocket = new ServerSocket(port);

            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            this.closeServer();
        }
    }

    public void closeServer(){
        try {
            if(serverSocket!=null){
                serverSocket.close();
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }

}
