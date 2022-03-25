package model;

import java.io.Serializable;
import java.util.Objects;

public abstract class MenuItem implements Serializable {
    private String title;
    public MenuItem(String title){
        this.title=title;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return this.title.equals(menuItem.title);
    }
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
    public abstract double computePrice();


}
