package Model.User;
import Model.AbstractObserver;
import Model.Client;

import java.util.ArrayList;

public class ModelUsers extends AbstractObserver {
    private Language language;
    private UserService service;
    private String operation;

    public ModelUsers(Client client){

        this.observerList = new ArrayList<>();
        this.service = new UserService(client);
        this.language = new Language();
        this.operation = "";
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language.setLanguage(language);
    }

    public UserService getService() {
        return service;
    }

    public void setService(UserService service) {
        this.service = service;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
        this.notifyObservers();
    }
}
