import Model.User.ModelUsers;
import View.UserLogin;

public class Main {
    public static void main(String[] args){
        ModelUsers ms = new ModelUsers();
        new UserLogin(ms);
    }
}
