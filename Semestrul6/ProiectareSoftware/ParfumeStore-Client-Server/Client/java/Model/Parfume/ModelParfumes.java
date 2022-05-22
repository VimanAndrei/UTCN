package Model.Parfume;

import Model.AbstractObserver;
import Model.Client;

import java.util.ArrayList;

public class ModelParfumes extends AbstractObserver {
    private Language language;
    private ParfumeService service;
    private String operation;

    public ModelParfumes(Client client) {
        this.observerList = new ArrayList<>();
        this.service = new ParfumeService(client);
        this.language = new Language();
        this.operation = "";
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language.setLanguage(language);
    }

    public ParfumeService getService() {
        return service;
    }

    public void setService(ParfumeService service) {
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
