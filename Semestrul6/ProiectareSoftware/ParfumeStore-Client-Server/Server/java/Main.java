import Communication.Server;

public class Main {
    public static void main(String[] args) {
        Server s = new Server(2222);
        s.startServer();
    }

}
