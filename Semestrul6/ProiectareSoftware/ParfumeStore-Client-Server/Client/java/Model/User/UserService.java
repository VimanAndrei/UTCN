package Model.User;

import Model.Client;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserService{
    private List<User> users;
    private Client client;

    public UserService(Client client) {
        this.client = client;
        this.users = new ArrayList<>();
        client.sendMessage("startUser ");
        this.users = constructUsers(client.listenForMessage());
    }

    private List<User> constructUsers(String message){
        List<User> list = new ArrayList<>();
        String[] splited = message.split(" ");

        for (int i = 0; i < splited.length; i+=3) {
            String username = splited[i];
            String password = splited[i+1];
            String role = splited[i+2];
            User user = new UserBuilder().setUsername(username).setPassword(password).setRole(role).build();
            list.add(user);
        }

        return list;
    }

    public User getUserByUsername(String usename){
        for (User u:users) {
            if (u.getUsername().equals(usename)){
                return u;
            }
        }
        return null;
    }

    public boolean insertUser(User user){
        for (User u:users) {
            if(u.getUsername().equals(user.getUsername())){
                return false;
            }
        }
        this.client.sendMessage("insertUser "+user.getUsername()+" "+user.getPassword()+" "+user.getRole()+" ");
        this.users.add(user);
        return true;
    }

    public void deleteUser(String username){
        client.sendMessage("deleteUser "+username+" ");
        client.sendMessage("startUser ");
        this.users = constructUsers(client.listenForMessage());
    }

    public void updateUser(String username, User newUser){
        int l1 = users.size();
        this.deleteUser(username);
        int l2 = users.size();
        if(l1!=l2) {
            this.insertUser(newUser);
        }
        client.sendMessage("updateUser "+username+" "+newUser.getUsername()+" "+newUser.getPassword()+" ");
    }

    public List<User> filterUsers(String role) {
        client.sendMessage("filterUser "+role+" ");
        List<User> list = constructUsers(client.listenForMessage());
        return list;
    }
}
