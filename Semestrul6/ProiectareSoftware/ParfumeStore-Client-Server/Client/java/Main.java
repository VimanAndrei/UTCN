import Model.Client;
import Model.User.ModelUsers;
import View.UserLogin;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        client.connectClient();
        ModelUsers modelUsers = new ModelUsers(client);
        new UserLogin(modelUsers);

    }
}
