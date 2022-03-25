package Model;


import java.util.ArrayList;
import java.util.List;

public class UserService extends UserServiceInterface{
    private List<User> users;


    public UserService() {
        this.users = new ArrayList<>();
        this.users = this.readXmlFile();
    }

    public boolean existUser(User user){
        boolean exist = false;
        for (User u:users) {
            if (u.getUserName().equals(user.getUserName())){
                exist=true;
            }
        }
        return exist;
    }

    public User getUserByUsername(String usename){
        for (User u:users) {
            if (u.getUserName().equals(usename)){
                return u;
            }
        }
        return null;
    }

    public boolean addUser(User user){

        if(this.existUser(user)){
            return false;
        }else {
            this.users.add(user);
            this.addUserToDocument(user);
            return true;
        }
    }

    public boolean removeUser(User user){
        boolean exist = false;
        int index=-1;
        for (User u:users) {
            if (u.getUserName().equals(user.getUserName())){
                exist=true;
                index = users.indexOf(u);
            }
        }
        if(exist){
            this.users.remove(index);
            this.deleteUserFromDocument(user);
            return true;
        }else {
            return false;
        }
    }

    public boolean updateUser(User oldUser, User newUser){
        if(this.removeUser(oldUser)){
            this.addUser(newUser);
            return true;
        }else{
            return false;
        }
    }


    @Override
    public String toString() {
        String rez = "";
        for (User u: users ) {
            rez += u.toString();
        }
        return rez;
    }
}
