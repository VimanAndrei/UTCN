package businessLayer;

import model.MenuItem;

import java.io.Serializable;
import java.util.List;

public class CompositeProduct extends MenuItem implements Serializable {
    private List<BaseProduct> products;

    public CompositeProduct(String title, List<BaseProduct> products) {
        super(title);
        this.products=products;
    }


    @Override
    public double computePrice() {
        double sum = 0;
        for(MenuItem product : products){
            sum=sum+product.computePrice();
        }
        return sum;
    }

    public int computeFat(){
        int sum = 0;
        for(BaseProduct product : products){
            sum=sum+product.getFat();
        }
        return sum;
    }
    public int computeSodium(){
        int sum = 0;
        for(BaseProduct product : products){
            sum=sum+product.getSodium();
        }
        return sum;
    }
    public int computeCalories(){
        int sum = 0;
        for(BaseProduct product : products){
            sum=sum+product.getCalories();
        }
        return sum;
    }
    public int computeProtein(){
        int sum = 0;
        for(BaseProduct product : products){
            sum=sum+product.getProtein();
        }
        return sum;
    }

}
