package businessLayer;

import model.MenuItem;

import java.io.Serializable;
import java.util.Objects;

public class BaseProduct extends MenuItem implements Serializable {
    private double rating,price;
    private int protein,fat,sodium,calories;

    public BaseProduct(String title,double rating,int calories,int protein,int fat,int sodium, double price) {
        super(title);
        this.rating=rating;
        this.price=price;
        this.protein=protein;
        this.fat=fat;
        this.calories=calories;
        this.sodium=sodium;
    }

    @Override
    public double computePrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseProduct that = (BaseProduct) o;
        return this.getTitle().equals(that.getTitle())
                /*&& Double.compare(that.rating, rating) == 0 && Double.compare(that.price, price) == 0
                && protein == that.protein && fat == that.fat && sodium == that.sodium*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return getTitle()+
                ", rating=" + rating +
                ", price=" + price +
                ", protein=" + protein +
                ", fat=" + fat +
                ", sodium=" + sodium +
                ", calories=" + calories ;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getSodium() {
        return sodium;
    }

    public int getCalories() {
        return calories;
    }
}
