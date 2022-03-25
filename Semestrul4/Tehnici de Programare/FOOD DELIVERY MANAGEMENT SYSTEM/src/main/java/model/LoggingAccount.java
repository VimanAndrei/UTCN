package model;

import java.io.Serializable;

public class LoggingAccount implements Serializable {
    private String userName;
    private String password;
    public LoggingAccount(String userName,String password){
        this.userName=userName;
        this.password=password;

    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }

}
