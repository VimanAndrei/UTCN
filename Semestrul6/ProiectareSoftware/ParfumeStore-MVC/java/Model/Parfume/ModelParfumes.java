package Model.Parfume;

import Model.AbstractObserver;

import java.util.ArrayList;

public class ModelParfumes extends AbstractObserver {
    private Language language;
    private ParfumeService service;
    private String operation;

    public ModelParfumes() {
        this.observerList = new ArrayList<>();
        this.service = new ParfumeService();
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
