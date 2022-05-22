package Model.User;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserService extends UserDAO {
    private List<User> users;
    public UserService() {
        this.users = new ArrayList<>();
        this.users = this.findAllUsers();
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
        this.insert(user);
        this.users.add(user);
        return true;
    }

    public void deleteUser(String username){
        this.delete(username);
        this.users=this.findAllUsers();
    }

    public void updateUser(String username, User newUser){
        int l1 = users.size();
        this.deleteUser(username);
        int l2 = users.size();
        if(l1!=l2) {
            this.insert(newUser);
            this.users = findAllUsers();
        }
    }

}
